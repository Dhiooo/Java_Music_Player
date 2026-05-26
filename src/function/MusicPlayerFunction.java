package function;

import com.mpatric.mp3agic.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MusicPlayerFunction {
  // ========================================== <Variables>
  // ==========================================

  private final String DEFAULT_ALBUM_IMAGE = "src/resources/assets/Image/Default Album Image.jpg";

  // Listener var
  private AudioPlayerListener listener;
  private String songFileName = "";

  // Audio var
  private Mp3File mp3file;
  private AdvancedPlayer audioPlayer;
  private Thread audioThread;
  private BufferedInputStream bufferedStream;
  private int audioPausedTime = 0, tempAudioPaused = 0;
  private boolean isPaused = false;

  // Song Storage and Playlist var
  private List<Path> songFiles;
  private int currentIndex = -1;

  // Song Metadata
  private ImageIcon songLogoImage;
  private String songTitle;

  // timer
  private Timer audioTimeStamp;

  // ========================================== <Constructor>
  // ==========================================

  public MusicPlayerFunction(AudioPlayerListener listener) {
    this.listener = listener;

    songLogoImage = new ImageIcon(DEFAULT_ALBUM_IMAGE);
    songTitle = "Unknown";
  }

  // ========================================== <Main Feature>
  // ==========================================

  // FUNCTION to Play the song
  private void playAudio() {
    try {
      bufferedStream = new BufferedInputStream(new FileInputStream(songFileName));
      audioPlayer = new AdvancedPlayer(bufferedStream);

      audioThread = new Thread(() -> {
        try {
          audioPlayer.play(audioPausedTime, Integer.MAX_VALUE);
        } catch (JavaLayerException e) {
          if (listener != null) {
            listener.onError("There seems to be problem with the JLayer Function", "JLayer Problem");
          }
        }
      });
      audioThread.start();

    } catch (FileNotFoundException | JavaLayerException e) {
      if (listener != null) {
        listener.onError("Please Refer to the correct file", "File Not Found");
      }
    }

    updateMp3file();
    audioStreamTimer();
  }

  // FUNCTION to Skip the current song to play the next song
  public void playNextSong() {
    if (songFiles == null || songFiles.isEmpty()) {
      System.out.println("No songs found in the directory.");
      return;
    }

    // Get The next mp3 file index on song directory
    currentIndex = (currentIndex + 1) % songFiles.size();
    songFileName = songFiles.get(currentIndex).toString();

    playAudio();
  }

  // FUNCTION to Skip the current song to play the previous song
  public void playPreviousSong() {
    if (songFiles == null || songFiles.isEmpty()) {
      System.out.println("No songs found in the directory.");
      return;
    }

    // Get The previous mp3 file index on song directory
    currentIndex = (currentIndex - 1 + songFiles.size()) % songFiles.size();
    songFileName = songFiles.get(currentIndex).toString();

    playAudio();
  }

  // FUNCTION to Stop the current audio
  public void stopAudio() {
    if (audioPlayer != null) {
      try {
        bufferedStream.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      audioPlayer.close();
      audioPlayer = null;
      audioTimeStamp.stop();
      isPaused = false;
      audioPausedTime = 0;
      tempAudioPaused = 0;

      audioThread.interrupt(); // Stop the thread
      audioThread = null;
    }
  }

  // FUNCTION to Pause the current audio
  public void pauseAudio() {
    if (audioPlayer != null) {
      audioPausedTime = convertMilisToFrame(tempAudioPaused);
      audioTimeStamp.stop();
      audioPlayer.close();
      isPaused = true;
    }
  }

  // FUNCTION to Resume the current audio
  public void resumeAudio() {
    if (isPaused) {
      isPaused = false;

      playAudio();
    }
  }

  // ========================================== <Function that help main feature>
  // ==========================================

  // CONVERT Milisecond to frame
  public int convertMilisToFrame(int currentTime) {
    return (currentTime * 38) / 1000;
  }

  // CONVERT frame to milisecond
  public int convertFrameToMilis(int currentTime) {
    return (currentTime / 38) * 1000;
  }

  // UPDATE the song metadata
  public void updateSongMetadata() {
    try {
      // Initialized the JAudioTagger object to get the song file
      AudioFile songMetaData = AudioFileIO.read(new File(songFileName));

      // Get all the song metadata from the current song
      Tag tag = songMetaData.getTag();

      if (tag != null) {
        // Get the album image (Metadata)
        List<Artwork> albumSongList = tag.getArtworkList();
        if (!albumSongList.isEmpty()) {
          System.out.println("ALBUM IMAGE IS NOT EMPTY");
          Artwork albumSong = albumSongList.getFirst();
          byte[] albumSongByte = albumSong.getBinaryData();
          songLogoImage = new ImageIcon(albumSongByte);
        } else {
          System.out.println("ALBUM IMAGE IS EMPTY");
          songLogoImage = new ImageIcon(DEFAULT_ALBUM_IMAGE);
        }

        // Get the song title (metadata)
        songTitle = tag.getFirst(FieldKey.TITLE);
      } else {
        songLogoImage = new ImageIcon(DEFAULT_ALBUM_IMAGE);
        songTitle = "Unknown";
      }
    } catch (Exception e) {
      System.out.println("Something wrong when getting the song Metadata");
      throw new RuntimeException(e);
    }
  }

  // Timer to get the song TimeStamp (ONLY FOR THIS CLASS)
  private void audioStreamTimer() {
    if (!isPaused) {
      // timer every 1 second to get the audio timestamp
      audioTimeStamp = new Timer(1000, e -> {
        tempAudioPaused += 1000;

        // Update the JSlider using Milisecond
        if (listener != null) {
          listener.onPlaybackTimeUpdated(tempAudioPaused);
        }

        System.out.println("Audio (in Second)               : " + (tempAudioPaused / 1000));
        System.out.println("Total Audio Length (in Second)  : " + (int) mp3file.getLengthInSeconds());

        // If The Song is Done Move to the Next Song (AUTOMATIC SONG PLAYLIST)
        if ((tempAudioPaused / 1000) >= (int) mp3file.getLengthInSeconds()) {
          stopAudio();
          playNextSong();

          // Update song metadata
          updateSongMetadata();

          // Notify UI that song changed
          if (listener != null) {
            listener.onSongChanged();
          }
        }
      });
      audioTimeStamp.start();
    }
  }

  // GET the condition of isAudioPaused
  public boolean getIsAudioPaused() {
    return isPaused;
  }

  // UPDATE the Mp3file if the song is changed
  private void updateMp3file() {
    try {
      mp3file = new Mp3File(songFileName);
    } catch (IOException | UnsupportedTagException | InvalidDataException e) {
      throw new RuntimeException(e);
    }
  }

  // ========================================== <Setter and Getter>
  // ==========================================

  public void setSongFileName(String folderDir) {
    this.songFileName = folderDir;

    try (Stream<Path> paths = Files.list(Paths.get(songFileName))) {
      songFiles = paths
          .filter(Files::isRegularFile)
          .filter(path -> path.toString().toLowerCase().endsWith(".mp3"))
          .collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setTempAudioPaused(int miliSecond) {
    this.tempAudioPaused = miliSecond;
  }

  // GET The Audio TimeStamp that is currently Playing As String
  public String getCurTimeStampAsString() {
    // CONVERT Audio Current Timestamp as String
    int second = (tempAudioPaused / 1000) % 60;
    int minute = (tempAudioPaused / 1000) / 60;
    int hour = minute / 60;

    return String.format("%01d:%02d:%02d", hour, minute, second);
  }

  // GET The Total Audio Length that is Playing As String
  public String getAudioLengthAsString() {
    // GET the mp3 audio length and convert it to hour, minute, and second (example:
    // 01:20:30, 02:55, etc)
    long totalAudioLength = (int) mp3file.getLengthInSeconds();
    long second = totalAudioLength % 60;
    long minute = totalAudioLength / 60;
    long hour = minute / 60;

    return String.format("%01d:%02d:%02d", hour, minute, second);
  }

  public String getSongFileName() {
    return songFileName;
  }

  public ImageIcon getSongLogoImage() {
    return songLogoImage;
  }

  public String getSongTitle() {
    return songTitle;
  }

  public Mp3File getMp3file() {
    return mp3file;
  }
}

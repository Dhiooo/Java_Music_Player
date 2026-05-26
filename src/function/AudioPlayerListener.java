package function;

public interface AudioPlayerListener {
    void onPlaybackTimeUpdated(int currentTimeMilis);
    void onSongFinished();
    void onSongChanged();
    void onError(String message, String title);
}

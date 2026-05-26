package gui;

import java.io.File;

// Definisi Interface secara lokal agar tidak bentrok
interface AudioFormatValidatorTest {
    boolean isSupported(File file);
}

// Stub Double yang mengembalikan nilai tiruan secara instan
class StubAudioValidator implements AudioFormatValidatorTest {
    private boolean returnValue;

    public StubAudioValidator(boolean returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public boolean isSupported(File file) {
        return this.returnValue;
    }
}

public class MainGUIPanelTest {

    // Simulasi Logika isValidSongFolder dari MainGUIPanel secara mandiri
    public static boolean simulasiIsValidSongFolder(String getAbsoluteSongPath, AudioFormatValidatorTest audioValidator) {
        File songFolder = new File(getAbsoluteSongPath);

        if (songFolder.exists() && songFolder.isDirectory()) {
            File[] songList = songFolder.listFiles();

            if (songList != null) {
                for (File file : songList) {
                    // Menggunakan Stub Double di sini
                    if (audioValidator.isSupported(file)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   MENJALANKAN UNIT TEST DENGAN STUB DOUBLE      ");
        System.out.println("=================================================");

        String pathDummy = "."; 

        // ---------------------------------------------------------
        // TEST CASE 1: Skenario Positif (File Didukung)
        // ---------------------------------------------------------
        AudioFormatValidatorTest stubTrue = new StubAudioValidator(true);
        boolean hasilTest1 = simulasiIsValidSongFolder(pathDummy, stubTrue);

        System.out.print("TEST 1 (Skenario File Didukung): ");
        if (hasilTest1 == true) {
            System.out.println("[ PASSED ]");
        } else {
            System.out.println("[ FAILED ]");
        }

        // ---------------------------------------------------------
        // TEST CASE 2: Skenario Negatif (File TIDAK Didukung)
        // ---------------------------------------------------------
        AudioFormatValidatorTest stubFalse = new StubAudioValidator(false);
        boolean hasilTest2 = simulasiIsValidSongFolder(pathDummy, stubFalse);

        System.out.print("TEST 2 (Skenario File Tidak Didukung): ");
        if (hasilTest2 == false) {
            System.out.println("[ PASSED ] -> Logika sukses mengembalikan false.");
        } else {
            System.out.println("[ FAILED ]");
        }

        System.out.println("=================================================");
    }
}
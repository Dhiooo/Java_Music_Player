package gui;

import java.io.File;

public class StubAudioValidator implements AudioFormatValidator {
    private boolean returnValue;

    // Konstruktor untuk menentukan hasil kembalian secara instan
    public StubAudioValidator(boolean returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public boolean isSupported(File file) {
        // Mengembalikan nilai tiruan tanpa memeriksa kondisi file asli
        return this.returnValue;
    }
}
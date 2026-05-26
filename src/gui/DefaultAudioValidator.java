package gui;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DefaultAudioValidator implements AudioFormatValidator {
    private final List<String> supportedExtensions;

    public DefaultAudioValidator() {
        this.supportedExtensions = Arrays.asList(".mp3");
    }

    public DefaultAudioValidator(List<String> supportedExtensions) {
        this.supportedExtensions = supportedExtensions;
    }

    @Override
    public boolean isSupported(File file) {
        if (!file.isFile()) {
            return false;
        }
        String name = file.getName().toLowerCase();
        for (String ext : supportedExtensions) {
            if (name.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}

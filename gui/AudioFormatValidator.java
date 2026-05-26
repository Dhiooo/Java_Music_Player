package gui;

import java.io.File;

public interface AudioFormatValidator {
    boolean isSupported(File file);
}

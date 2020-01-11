package io.github.vampirestudios.tdg.levelcreator.fileFilter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileFilterExtension extends FileFilter {

    private String extension, description;

    public FileFilterExtension(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    @Override
    public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith(extension) || file.isDirectory();
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}

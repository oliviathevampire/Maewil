package coffeecatteam.theultimatetile.levelcreator;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class CustomFileFilter extends FileFilter {

    private String extension, description;

    public CustomFileFilter(String extension, String description) {
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

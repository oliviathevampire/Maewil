package coffeecatteam.theultimatetile.levelcreator.fileFilter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileFilterDirectories extends FileFilter {

    public boolean accept(File f) {
        return (f.isDirectory());
    }

    public String getDescription() {
        return ("Directories");
    }
}

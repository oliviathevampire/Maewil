package io.github.vampirestudios.tdg_rewritten.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class FileUtils {

    public static String loadAsString(String path) {
        StringBuilder result = new StringBuilder();
        Class.class.getClassLoader();
        URL url = ClassLoader.getSystemResource(path);
        try(InputStream stream = url.openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error: Couldn't find file");
        }
        return result.toString();
    }

}

package team.abnormals.tut_new.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class Utils {

    public static String loadResource(String fileName) {
        StringBuilder result = new StringBuilder();
        Class.class.getClassLoader();
        URL url = ClassLoader.getSystemResource(fileName);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
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
package team.abnormals.tut_new.engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Utils {

    public static String loadResource(String fileName) {
        String result;
        try (InputStream in = Class.forName(Utils.class.getName()).getResourceAsStream(fileName);
             Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

}
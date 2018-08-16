package coffeecatteam.theultimatetile.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    public static void print(Object msg) {
        System.out.println(msg);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./log.txt", true));
            writer.append("    \n");
            writer.append(String.valueOf(msg));

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

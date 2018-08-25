package coffeecatteam.theultimatetile.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static File logFile;

    public static void init() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        Date date = new Date();

        logFile = new File("./logs/log_" + formatter.format(date) + ".txt");
        logFile.getParentFile().mkdirs();
    }

    public static void print(Object msg) {
        String msgString = String.valueOf(msg);
        System.out.println(msgString);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.append(msgString);
            if (msgString.contains("\\n") || msgString.contains("\n"))
                writer.newLine();
            writer.newLine();

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

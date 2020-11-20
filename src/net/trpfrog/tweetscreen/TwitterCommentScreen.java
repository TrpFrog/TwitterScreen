package net.trpfrog.tweetscreen;

import com.sun.tools.javac.Main;
import net.trpfrog.tweetscreen.manager.ScreenManagerFrame;
import net.trpfrog.tweetscreen.stream.WordStreamReader;
import net.trpfrog.tweetscreen.viewer.ScreenConfigs;
import net.trpfrog.tweetscreen.viewer.TwitterScreen;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TwitterCommentScreen {
    public static final String FS = File.separator;
    private static String jarPath = System.getProperty("java.class.path");
    public static final ScreenConfigs CONFIG = new ScreenConfigs(getFilePath() + "config.properties");

    private static boolean macOS = false;

    public static boolean isMacOS() {
        return macOS;
    }

    private static String getFilePath() {
        System.out.println(System.getProperty("os.name"));
        if (System.getProperty("os.name").equals("Mac OS X")) {
            macOS = true;
            return "." + FS;
        } else {
            return jarPath.substring(0, jarPath.lastIndexOf(File.separator) + 1);
        }
    }

    public static void main(String[] args) {

        var ts = new TwitterScreen(800, 600);

        try {
            var ts = new TwitterScreen(CONFIG);
            WordStreamReader stream = new WordStreamReader(
                    ts.getCommentProvider(),
                    getFilePath() + "FilterWords.txt",
                    CONFIG);
            new ScreenManagerFrame(ts, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

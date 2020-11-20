package net.trpfrog.tweetscreen;

import net.trpfrog.tweetscreen.manager.ScreenManagerFrame;
import net.trpfrog.tweetscreen.stream.WordStreamReader;
import net.trpfrog.tweetscreen.viewer.ScreenConfigs;
import net.trpfrog.tweetscreen.viewer.TwitterScreen;

import java.io.File;

public class TwitterCommentScreen {
    public static final String FS = File.separator;
    private static String jarPath = System.getProperty("java.class.path");
    public static final ScreenConfigs CONFIG = new ScreenConfigs(getCurrentFilePath() + "config.properties");

    private static boolean macOS = false;

    public static boolean isMacOS() {
        return macOS;
    }

    public static String getCurrentFilePath() {
        if (System.getProperty("os.name").equals("Mac OS X")) {
            macOS = true;
            return "." + FS;
        } else {
            return jarPath.substring(0, jarPath.lastIndexOf(File.separator) + 1);
        }
    }

    public static void main(String[] args) {
        try {
            var ts = new TwitterScreen(CONFIG);
            WordStreamReader stream = new WordStreamReader(
                    ts.getCommentProvider(),
                    getCurrentFilePath() + "FilterWords.txt",
                    CONFIG);
            new ScreenManagerFrame(ts, stream);
        } catch (Exception e) {
            new CrashWindow(e);
        }
    }
}

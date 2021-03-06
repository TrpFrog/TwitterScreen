package net.trpfrog.tweetscreen;

import net.trpfrog.tweetscreen.comment.Comment;
import net.trpfrog.tweetscreen.manager.ScreenManagerFrame;
import net.trpfrog.tweetscreen.stream.TwitterConfigs;
import net.trpfrog.tweetscreen.stream.WordStreamReader;
import net.trpfrog.tweetscreen.viewer.ScreenConfigs;
import net.trpfrog.tweetscreen.viewer.CommentFrame;

import java.io.File;

public class TwitterScreen {
    public static final String FS = File.separator;
    private static String jarPath = System.getProperty("java.class.path");
    public static final TwitterConfigs CONFIG = new TwitterConfigs(getCurrentFilePath() + "config.properties");

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
            var ts = new CommentFrame(CONFIG);
            WordStreamReader stream = new WordStreamReader(
                    ts.getCommentProvider(),
                    getCurrentFilePath() + "FilterWords.txt",
                    CONFIG);
            new ScreenManagerFrame(ts, stream, CONFIG);
        } catch (Exception e) {
            new CrashWindow(e);
        }
    }
}

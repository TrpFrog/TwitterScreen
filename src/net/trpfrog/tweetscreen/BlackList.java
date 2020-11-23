package net.trpfrog.tweetscreen;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class BlackList {
    private Set<Long> blacklist = new HashSet<>();
    private static BlackList singleton;
    private Twitter twitter = new TwitterFactory().getInstance();

    public static BlackList getInstance() {
        return singleton;
    }

    static {
        singleton = new BlackList();
    }

    private BlackList() {
        reload();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Updating blacklist...");
            try {
                FileWriter writer = new FileWriter(TwitterScreen.getCurrentFilePath() + "BlackList.txt");
                PrintWriter pw = new PrintWriter(new BufferedWriter(writer));
                for(long id : blacklist) {
                    System.out.println(id);
                    pw.println(id + "");
                }
                pw.close();
                writer.close();
            } catch (IOException e) {
                new CrashWindow(e);
            }
        }));
    }

    public void reload() {
        blacklist.clear();
        try {
            System.out.println("[Blacklist]");
            Files.readAllLines(Paths.get("BlackList.txt"))
                    .stream()
                    .filter(e -> e.matches("^[0-9]+$"))
                    .map(Long::parseLong)
                    .forEach(e -> {
                        blacklist.add(e);
                        System.out.println(e);
                    });
        } catch (IOException e) {
            new CrashWindow(e);
        }
    }

    public void add(long id) {
        blacklist.add(id);
    }

    public void add(String screenName) {
        long id = -1;
        try {
            id = twitter.showUser(screenName).getId();
            add(id);
        } catch (TwitterException e) {
            // 存在しないユーザ名は無視
        }
        System.out.println(screenName + " (" + id + ") has been banned.");
    }

    public void remove(long id) {
        blacklist.remove(id);
    }

    public void remove(String screenName) {
        long id = -1;
        try {
            id = twitter.showUser(screenName).getId();
            remove(id);
        } catch (TwitterException e) {
            // 存在しないユーザ名は無視
        }
        System.out.println(screenName + " (" + id + ") has been unbanned.");
    }

    public boolean isBanned(long id) {
        return blacklist.contains(id);
    }
}

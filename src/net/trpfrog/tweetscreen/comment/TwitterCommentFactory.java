package net.trpfrog.tweetscreen.comment;

import net.trpfrog.tweetscreen.viewer.ScreenConfigs;
import twitter4j.Status;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TwitterCommentFactory {

    private boolean screenNameVisible = false;
    private boolean nameVisible = false;
    private boolean removeLinks = true;
    private boolean removeHashtags = true;

    public TwitterCommentFactory(ScreenConfigs config) {
        screenNameVisible = config.SHOW_SCREEN_NAME;
        nameVisible = config.SHOW_NAME;
        removeLinks = config.REMOVE_LINKS;
        removeHashtags = config.REMOVE_HASHTAGS;
    }

    public TwitterCommentFactory() {}


    public boolean isScreenNameVisible() {
        return screenNameVisible;
    }

    public TwitterCommentFactory setScreenNameVisible(boolean screenNameVisible) {
        this.screenNameVisible = screenNameVisible;
        return this;
    }

    public boolean isNameVisible() {
        return nameVisible;
    }

    public TwitterCommentFactory setNameVisible(boolean nameVisible) {
        this.nameVisible = nameVisible;
        return this;
    }

    public String generate(Status status) {
        String screenName = status.getUser().getScreenName();
        String name       = status.getUser().getName();
        String tweet      =
                Arrays.stream(status.getText().split("[ 　\n]"))
                .filter(e -> !removeLinks || !e.matches("^https://t\\.co/.*"))
                .filter(e -> !removeHashtags || !e.matches("^#.+"))
                .collect(Collectors.joining(" "));
        if(screenNameVisible && nameVisible) return name + "(@" + screenName + "): " + tweet;
        else if(screenNameVisible)           return screenName + ": " + tweet;
        else if(nameVisible)                 return name + ": " + tweet;
        else                                 return tweet;
    }
}

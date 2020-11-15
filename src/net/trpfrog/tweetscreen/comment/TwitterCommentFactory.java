package net.trpfrog.tweetscreen.comment;

import twitter4j.Status;

public class TwitterCommentFactory {

    private boolean screenNameVisible = false;
    private boolean nameVisible = false;

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
        String tweet      = status.getText();
        if(screenNameVisible && nameVisible) return name + "(@" + screenName + "): " + tweet;
        else if(screenNameVisible)           return screenName + ": " + tweet;
        else if(nameVisible)                 return name + ": " + tweet;
        else                                 return tweet;
    }
}

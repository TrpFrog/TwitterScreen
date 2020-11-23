package net.trpfrog.tweetscreen.stream;

import net.trpfrog.tweetscreen.viewer.ScreenConfigs;

public class TwitterConfigs extends ScreenConfigs {

    public final boolean REMOVE_LINKS;
    public final boolean REMOVE_HASHTAGS;
    public final boolean REMOVE_REPLY;

    public final boolean STREAMING;

    public TwitterConfigs(String path) {
        super(path);
        REMOVE_LINKS = getBooleanProperty("remove_links", "true");
        REMOVE_HASHTAGS = getBooleanProperty("remove_hashtags", "true");
        REMOVE_REPLY = getBooleanProperty("remove_reply", "true");
        STREAMING = getBooleanProperty("streaming", "true");
    }

}

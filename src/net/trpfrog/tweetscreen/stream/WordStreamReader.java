package net.trpfrog.tweetscreen.stream;

import net.trpfrog.tweetscreen.BlackList;
import net.trpfrog.tweetscreen.comment.CommentProvider;
import net.trpfrog.tweetscreen.comment.TwitterCommentFactory;
import net.trpfrog.tweetscreen.viewer.ScreenConfigs;
import twitter4j.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WordStreamReader implements StatusListener {
    private TwitterStream twitterStream;
    private final CommentProvider COMMENT_PROVIDER;

    private TwitterCommentFactory commentFactory;

    private TwitterConfigs config;
    private BlackList blacklist = BlackList.getInstance();

    public TwitterCommentFactory getCommentFactory() {
        return commentFactory;
    }

    public WordStreamReader(CommentProvider cp, String filterTextPath, TwitterConfigs config) throws IOException {
        this.config = config;
        commentFactory = new TwitterCommentFactory(config);

        COMMENT_PROVIDER = cp;

        String[] filterWords =
                Files.readAllLines(Paths.get(filterTextPath))
                .stream()
                .map(String::trim)
                .toArray(String[]::new);

        for(String str : filterWords) System.err.println(str);

        FilterQuery filterQuery = new FilterQuery();
        filterQuery.track(filterWords);

        if(config.STREAMING) {
            twitterStream = new TwitterStreamFactory().getInstance();
            twitterStream.addListener(this);

            System.out.println("Stream start!");
            twitterStream.filter(filterQuery);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Closing stream...");
                twitterStream.shutdown();
            }));
        }
    }

    @Override
    public void onStatus(Status status) {
        String comment = status.getText();
        if(blacklist.isBanned(status.getUser().getId())) return;
        if(status.isRetweet()) return;
        if(config.REMOVE_REPLY && status.getInReplyToUserId() > 0) return;

        COMMENT_PROVIDER.addComment(commentFactory.generate(status));
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {

    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {

    }

    @Override
    public void onException(Exception e) {

    }
}

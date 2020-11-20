package net.trpfrog.tweetscreen.comment;

import net.trpfrog.tweetscreen.viewer.ScreenConfigs;
import net.trpfrog.tweetscreen.viewer.TwitterScreen;

import javax.swing.Timer;
import java.util.*;

public class CommentProvider {
    private final TwitterScreen SCREEN;
    private final List<Comment> ACTIVE_COMMENTS = Collections.synchronizedList(new LinkedList<>());
    private final Set<Comment> NEW_COMMENTS = Collections.synchronizedSet(new HashSet<>());
    private final TreeSet<Integer> AVAILABLE_Y = new TreeSet<>();
    private final Timer TIMER = new Timer(10, e -> moveComments());
    private int updateInterval;
    private int commentSpeed;
    private final int FONT_HEIGHT;
    private ScreenConfigs config;

    public CommentProvider(TwitterScreen SCREEN, ScreenConfigs config) {
        this.config = config;
        this.SCREEN = SCREEN;
        this.FONT_HEIGHT = config.FONT_SIZE + 20;
        synchronized(AVAILABLE_Y) {
            for(int i = 0; i + FONT_HEIGHT <= SCREEN.getHeight(); i += FONT_HEIGHT) {
                AVAILABLE_Y.add(i);
            }
        }
        updateInterval = config.DEFAULT_UPDATE_INTERVAL_MS;
        commentSpeed = config.DEFAULT_COMMENT_SPEED;
        TIMER.setDelay(config.DEFAULT_UPDATE_INTERVAL_MS);
    }

    /**
     * スクリーンに新たなコメントを描画します
     * @param comment コメント
     */
    public void addComment(String comment) {
        System.err.println(comment);
        if(!canInsert()) return;

        // X座標としてスクリーンの横幅を指定するのは右端から出現させるため
        Comment newComment = new Comment(comment.trim(), SCREEN.getWidth(), pollOptimalY());

        newComment.setBounds(newComment.getX(), newComment.getY(), newComment.getWidth(), newComment.getHeight());
        ACTIVE_COMMENTS.add(newComment);
        SCREEN.add(newComment);
        NEW_COMMENTS.add(newComment);
    }

    public synchronized boolean canInsert() {
        return !AVAILABLE_Y.isEmpty();
    }

    private synchronized int pollOptimalY() {
        Integer ret = AVAILABLE_Y.pollFirst();
        return ret == null ? - FONT_HEIGHT : ret;
    }

    private void moveComments() {
        Iterator<Comment> it = ACTIVE_COMMENTS.iterator();

        while(it.hasNext()) {
            Comment cmt = it.next();

            int dx = (commentSpeed * updateInterval) / 2500; //2500は速度調整
            cmt.setX(cmt.getX() - dx);
            cmt.setBounds(cmt.getX(), cmt.getY(), cmt.getWidth(), cmt.getHeight());

            boolean canInsertOnThisLine = cmt.getX() + cmt.getWidth() + 10 < SCREEN.getWidth();
            if(canInsertOnThisLine && NEW_COMMENTS.contains(cmt)) {
                synchronized(AVAILABLE_Y) {
                    AVAILABLE_Y.add(cmt.getY());
                }
                NEW_COMMENTS.remove(cmt);
            }

            boolean inViewerBounds = cmt.getX() + cmt.getWidth() >= 0;
            if(!inViewerBounds) {
                it.remove();
            }
        }
    }

    public synchronized void onWindowStatusChanged() {
        AVAILABLE_Y.clear();
        System.out.println(SCREEN.getHeight());
        for(int i = 0; i + FONT_HEIGHT <= SCREEN.getHeight(); i += FONT_HEIGHT) {
            AVAILABLE_Y.add(i);
        }
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
        TIMER.setDelay(updateInterval);
    }

    public int getCommentSpeed() {
        return commentSpeed;
    }

    public void setCommentSpeed(int commentSpeed) {
        this.commentSpeed = commentSpeed;
    }

    public void start() {
        TIMER.start();
    }

    public void stop() {
        TIMER.stop();
    }
}

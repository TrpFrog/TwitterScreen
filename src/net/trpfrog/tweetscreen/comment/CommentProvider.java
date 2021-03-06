package net.trpfrog.tweetscreen.comment;

import net.trpfrog.tweetscreen.viewer.ScreenConfigs;
import net.trpfrog.tweetscreen.viewer.CommentFrame;

import javax.swing.Timer;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CommentProvider {
    private final CommentFrame SCREEN;
    private final List<Comment> ACTIVE_COMMENTS = Collections.synchronizedList(new LinkedList<>());
    private final Queue<String> COMMENT_STR_QUEUE = new ConcurrentLinkedQueue<>();
    private final Set<Comment> NEW_COMMENTS = Collections.synchronizedSet(new HashSet<>());

    private final TreeSet<Integer> INSERTABLE_Y = new TreeSet<>();
    private final Timer TIMER = new Timer(10, e -> moveComments());
    private int updateInterval;
    private int commentSpeed;
    private final int FONT_HEIGHT;
    private ScreenConfigs config;

    public CommentProvider(CommentFrame SCREEN, ScreenConfigs config) {
        this.config = config;
        this.SCREEN = SCREEN;
        this.FONT_HEIGHT = config.FONT_SIZE + 20;
        refreshInsertableY();
        updateInterval = config.DEFAULT_UPDATE_INTERVAL_MS;
        commentSpeed = config.DEFAULT_COMMENT_SPEED;
        TIMER.setDelay(config.DEFAULT_UPDATE_INTERVAL_MS);
    }

    public void refreshInsertableY() {
        synchronized(INSERTABLE_Y) {
            for(int i = 0; i + FONT_HEIGHT <= SCREEN.getHeight(); i += FONT_HEIGHT) {
                INSERTABLE_Y.add(i);
            }
        }
    }

    public void refreshActiveComments() {
        ACTIVE_COMMENTS.forEach(e -> e.setDoubleX(- e.getWidth()));
        NEW_COMMENTS.clear();
        ACTIVE_COMMENTS.clear();
        refreshInsertableY();
    }

    /**
     * スクリーンに新たなコメントを描画します
     * @param comment コメント
     */
    public void addComment(String comment) {
        System.err.println(comment);
        if(!canInsert()) return;
        COMMENT_STR_QUEUE.add(comment);
    }

    private void insertCommentsInQueue() {
        while(canInsert() && !COMMENT_STR_QUEUE.isEmpty()) {
            // X座標としてスクリーンの横幅を指定するのは右端から出現させるため
            Comment newComment = new Comment(COMMENT_STR_QUEUE.poll(), SCREEN.getWidth(), pollOptimalY(), config);
            ACTIVE_COMMENTS.add(newComment);
            NEW_COMMENTS.add(newComment);
        }
    }

    public synchronized boolean canInsert() {
        return !INSERTABLE_Y.isEmpty();
    }

    private synchronized int pollOptimalY() {
        Integer ret = INSERTABLE_Y.pollFirst();
        return ret == null ? - FONT_HEIGHT : ret;
    }

    private synchronized void moveComments() {

        Iterator<Comment> it = ACTIVE_COMMENTS.iterator();
        double dx = - (commentSpeed * updateInterval) / 2048.0; //速度調整

        while(it.hasNext()) {
            Comment cmt = it.next();

            cmt.setDoubleX(cmt.getDoubleX() + dx);

            boolean canInsertOnThisLine = cmt.getDoubleX() + cmt.getWidth() + 10 < SCREEN.getWidth();
            if(canInsertOnThisLine && NEW_COMMENTS.contains(cmt)) {
                synchronized(INSERTABLE_Y){
                    INSERTABLE_Y.add(cmt.getY());
                }
                NEW_COMMENTS.remove(cmt);
            }

            boolean inViewerBounds = cmt.getDoubleX() + cmt.getWidth() >= 0;
            if(!inViewerBounds) {
                it.remove();
                if(NEW_COMMENTS.contains(cmt)) {
                    synchronized(INSERTABLE_Y){
                        INSERTABLE_Y.add(cmt.getY());
                    }
                    NEW_COMMENTS.remove(cmt);
                }
            }
        }

        SCREEN.getInnerPanel().paintComments(ACTIVE_COMMENTS);

        insertCommentsInQueue();
    }

    public synchronized void onWindowStatusChanged() {
        INSERTABLE_Y.clear();
        System.out.println(SCREEN.getHeight());
        for(int i = 0; i + FONT_HEIGHT <= SCREEN.getHeight(); i += FONT_HEIGHT) {
            INSERTABLE_Y.add(i);
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

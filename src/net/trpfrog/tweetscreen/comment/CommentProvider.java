package net.trpfrog.tweetscreen.comment;

import net.trpfrog.tweetscreen.viewer.TwitterScreen;

import javax.swing.Timer;
import java.util.*;

public class CommentProvider {
    private final TwitterScreen SCREEN;
    private List<Comment> comments = Collections.synchronizedList(new LinkedList<>());
    private Timer timer = new Timer(10, e->viewComment());
    private int updateInterval = 50;
    private int commentSpeed = 100;
    private final int FONT_SIZE;

    public CommentProvider(TwitterScreen SCREEN, int fontSize) {
        this.SCREEN = SCREEN;
        this.FONT_SIZE = fontSize;
    }

    public CommentProvider(TwitterScreen SCREEN) {
        this(SCREEN, 30);
    }

    /**
     * スクリーンに新たなコメントを描画します
     * @param comment コメント
     */
    public void addComment(String comment) {
        System.err.println(comment);

        Comment newComment = new Comment(comment.trim(), SCREEN.getWidth(), 0);

        newComment.setBounds(newComment.getX(), newComment.getY(), FONT_SIZE, 0);
        SCREEN.add(newComment);

        newComment.setY(calculateOptimalInsertionHeight(newComment.getHeight()));
        comments.add(newComment);
    }

    private int calculateOptimalInsertionHeight(int height) {
        boolean[] availableY = new boolean[SCREEN.getHeight() / height];
        Arrays.fill(availableY, true);

        for(Comment cmt : comments) {
            boolean canInsert = cmt.getX() + cmt.getWidth() + 10 < SCREEN.getWidth();
            if(!canInsert) {
                availableY[cmt.getY()/height] = false;
            }
        }

        for(int y = 0; y < availableY.length; y++) {
            if (availableY[y]) return y * height;
        }
        return -height;
    }

    private void viewComment() {
        Iterator<Comment> it = comments.iterator();

        while(it.hasNext()) {
            Comment cmt = it.next();

            cmt.setX(cmt.getX() - (commentSpeed * updateInterval) / 2500);
            cmt.setBounds(cmt.getX(), cmt.getY(), cmt.getWidth(), cmt.getHeight());

            boolean inViewerBounds = cmt.getX() + cmt.getWidth() >= 0;
            if(!inViewerBounds) {
                it.remove();
            }
        }
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
        timer.setDelay(updateInterval);
    }

    public int getCommentSpeed() {
        return commentSpeed;
    }

    public void setCommentSpeed(int commentSpeed) {
        this.commentSpeed = commentSpeed;
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}

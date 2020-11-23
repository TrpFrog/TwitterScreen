package net.trpfrog.tweetscreen.viewer;

import net.trpfrog.tweetscreen.TwitterCommentScreen;
import net.trpfrog.tweetscreen.comment.Comment;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class CommentPanel extends JPanel {

    private List<Comment> commentList = new LinkedList<>();

    private void paintFontBorder(Graphics g, Comment c, int borderSize) {
        // 計算量の削減
        if(0 < borderSize && borderSize <= 3) {
            for(int i = -1; i <= 1; i++) {
                for(int j = -1; j <= 1; j++) {
                    int x = c.getX() + i * borderSize;
                    int y = c.getY() + j * borderSize + c.getHeight() - c.getMARGIN();
                    g.drawString(c.getText(), x, y);
                }
            }
        }
        // 処理が重い
        else if(3 < borderSize) {
            for(int i = -borderSize; i <= borderSize; i++) {
                for(int j = -borderSize; j <= borderSize; j++) {
                    int x = c.getX() + i;
                    int y = c.getY() + j + c.getHeight() - c.getMARGIN();
                    g.drawString(c.getText(), x, y);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Comment c : commentList) {
            g.setFont(c.getFont());

            if(TwitterCommentScreen.CONFIG.HAS_FONT_BORDER) {
                g.setColor(TwitterCommentScreen.CONFIG.FONT_BORDER_COLOR);
                paintFontBorder(g, c, TwitterCommentScreen.CONFIG.FONT_BORDER_SIZE_PT);
            }

            g.setColor(c.getFontColor());
            g.drawString(c.getText(), c.getX(), c.getY() + c.getHeight() - c.getMARGIN());
        }
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

}

package net.trpfrog.tweetscreen.viewer;

import net.trpfrog.tweetscreen.comment.Comment;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class CommentPanel extends JPanel {

    private List<Comment> commentList = new LinkedList<>();

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Comment c : commentList) {
            g.setFont(c.getFont());
            g.drawString(c.getText(), c.getX(), c.getY());
        }
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

}

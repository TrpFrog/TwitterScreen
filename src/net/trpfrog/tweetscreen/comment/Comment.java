package net.trpfrog.tweetscreen.comment;

import javax.swing.*;
import java.awt.*;

public class Comment extends JLabel{
    Font font;
    private int x, y;
    private final int COMMENT_WIDTH;
    private final int COMMENT_HEIGHT;
    private final int MARGIN = 10;

    public Comment(String commentStr, int x, int y) {
        super(commentStr);

        final int FONT_SIZE = 30;
        font = new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE);
        setForeground(Color.WHITE);
        setFont(font);


        COMMENT_WIDTH = getFontMetrics(font).stringWidth(commentStr) + MARGIN * 2;
        COMMENT_HEIGHT = font.getSize() + MARGIN * 2;

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return COMMENT_WIDTH;
    }

    @Override
    public int getHeight() {
        return COMMENT_HEIGHT;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

package net.trpfrog.tweetscreen.comment;

import net.trpfrog.tweetscreen.viewer.ScreenConfigs;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Comment implements Comparable<Comment> {
    private String text;
    private Font font;
    private double x;
    private int y;
    private final int COMMENT_WIDTH;
    private final int COMMENT_HEIGHT;
    private final int MARGIN = 10;
    private final Color fontColor;
    public final long CREATED_DATE = System.nanoTime();

    public Comment(String commentStr, double x, int y, ScreenConfigs config) {
        text = commentStr;
        final int FONT_SIZE = config.FONT_SIZE;
        font = new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE);
        fontColor = config.FONT_COLOR;

        COMMENT_WIDTH = new JLabel().getFontMetrics(font).stringWidth(commentStr) + MARGIN * 2;
        COMMENT_HEIGHT = font.getSize() + MARGIN * 2;

        this.x = x;
        this.y = y;
    }

    public int getMARGIN() {
        return MARGIN;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public double getDoubleX() {
        return x;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return COMMENT_WIDTH;
    }

    public int getHeight() {
        return COMMENT_HEIGHT;
    }

    public void setDoubleX(double x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Font getFont() {
        return font;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return CREATED_DATE == comment.CREATED_DATE &&
                Objects.equals(getText(), comment.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(CREATED_DATE, getText());
    }

    @Override
    public int compareTo(Comment o) {
        if (o == null || getClass() != o.getClass()) {
            throw new IllegalArgumentException();
        }
        return CREATED_DATE < o.CREATED_DATE ? -1 : 1;
    }
}

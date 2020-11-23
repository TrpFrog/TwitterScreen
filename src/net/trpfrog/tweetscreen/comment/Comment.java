package net.trpfrog.tweetscreen.comment;

import net.trpfrog.tweetscreen.viewer.ScreenConfigs;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Comment implements Comparable<Comment> {
    private final String text;
    private final Font font;
    private double x;
    private int y;
    private final int width;
    private final int height;
    private final int margin = 10;
    private final Color fontColor;
    private final long createdDate = System.nanoTime();

    public Comment(String commentStr, double x, int y, ScreenConfigs config) {
        text = commentStr;
        final int FONT_SIZE = config.FONT_SIZE;
        font = new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE);
        fontColor = config.FONT_COLOR;

        width = new JLabel().getFontMetrics(font).stringWidth(commentStr) + margin * 2;
        height = font.getSize() + margin * 2;

        this.x = x;
        this.y = y;
    }

    public int getMargin() {
        return margin;
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
        return width;
    }

    public int getHeight() {
        return height;
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
        return createdDate == comment.createdDate &&
                Objects.equals(getText(), comment.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdDate, getText());
    }

    @Override
    public int compareTo(Comment o) {
        if (o == null || getClass() != o.getClass()) {
            throw new IllegalArgumentException();
        }
        return createdDate < o.createdDate ? -1 : 1;
    }
}

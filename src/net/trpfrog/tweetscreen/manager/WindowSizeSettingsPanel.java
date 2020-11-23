package net.trpfrog.tweetscreen.manager;

import net.trpfrog.tweetscreen.viewer.CommentFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class WindowSizeSettingsPanel extends JPanel {

    private final IntegerTextField WIDTH_FIELD;
    private final IntegerTextField HEIGHT_FIELD;
    private final CommentFrame CHILD_SCREEN;

    public WindowSizeSettingsPanel(CommentFrame childScreen) {
        CHILD_SCREEN = childScreen;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        WIDTH_FIELD = new IntegerTextField(CHILD_SCREEN.getWidth());
        HEIGHT_FIELD = new IntegerTextField(CHILD_SCREEN.getHeight());
        WIDTH_FIELD.addActionListener(e -> changeFrameSizeFromFields());
        HEIGHT_FIELD.addActionListener(e -> changeFrameSizeFromFields());

        add(new JLabel("Width"));
        add(WIDTH_FIELD);
        add(new JLabel("Height"));
        add(HEIGHT_FIELD);

        setBorder(new TitledBorder("Window size settings"));
    }

    private void changeFrameSizeFromFields() {
        int w = WIDTH_FIELD.getValue();
        int h = HEIGHT_FIELD.getValue();
        CHILD_SCREEN.resizeScreen(w, h);
    }

}

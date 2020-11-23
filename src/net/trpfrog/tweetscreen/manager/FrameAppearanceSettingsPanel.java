package net.trpfrog.tweetscreen.manager;

import net.trpfrog.tweetscreen.viewer.CommentFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FrameAppearanceSettingsPanel extends JPanel {
    private final CommentFrame CHILD_SCREEN;

    private final JButton button;


    public FrameAppearanceSettingsPanel(CommentFrame CHILD_SCREEN) {
        this.CHILD_SCREEN = CHILD_SCREEN;
        setLayout(new GridLayout(2,1));

        button = new JButton("Refresh insertable area");
        button.addActionListener(e -> CHILD_SCREEN.getCommentProvider().refreshInsertableY());
        add(button);

        JButton refreshActiveCommentsButton = new JButton("Refresh all active comments");
        refreshActiveCommentsButton.addActionListener(e -> CHILD_SCREEN.getCommentProvider().refreshActiveComments());
        add(refreshActiveCommentsButton);

        setBorder(new TitledBorder("Appearance"));
    }

}

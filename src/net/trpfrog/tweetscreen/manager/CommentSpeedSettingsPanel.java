package net.trpfrog.tweetscreen.manager;

import net.trpfrog.tweetscreen.viewer.TwitterScreen;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CommentSpeedSettingsPanel extends JPanel {
    private final TwitterScreen CHILD_SCREEN;

    private final IntegerTextField FPS_FIELD, SPEED_FIELD;

    public CommentSpeedSettingsPanel(TwitterScreen CHILD_SCREEN) {
        this.CHILD_SCREEN = CHILD_SCREEN;

        setLayout(new GridLayout(4, 1));

        add(new JLabel("Update interval (ms)"));
        FPS_FIELD = new IntegerTextField(CHILD_SCREEN.getCommentProvider().getUpdateInterval());
        FPS_FIELD.addActionListener(e -> changeFPSFromField());
        add(FPS_FIELD);

        add(new JLabel("Speed"));
        SPEED_FIELD = new IntegerTextField(CHILD_SCREEN.getCommentProvider().getCommentSpeed());
        SPEED_FIELD.addActionListener(e -> changeCommentSpeedFromField());
        add(SPEED_FIELD);

        setBorder(new TitledBorder("Comment speed"));
    }


    private void changeFPSFromField() {
        CHILD_SCREEN.getCommentProvider().setUpdateInterval(FPS_FIELD.getValue());
    }

    private void changeCommentSpeedFromField() {
        CHILD_SCREEN.getCommentProvider().setCommentSpeed(SPEED_FIELD.getValue());
    }
}

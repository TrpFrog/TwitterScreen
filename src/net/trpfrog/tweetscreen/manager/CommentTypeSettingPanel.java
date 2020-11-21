package net.trpfrog.tweetscreen.manager;

import net.trpfrog.tweetscreen.comment.TwitterCommentFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CommentTypeSettingPanel extends JPanel {
    TwitterCommentFactory factory;

    JCheckBox screenNameCheckbox, nameCheckbox;

    public CommentTypeSettingPanel(TwitterCommentFactory factory) {
        this.factory = factory;

        screenNameCheckbox = new JCheckBox("Set screen name visible");
        screenNameCheckbox.addActionListener(e -> factory.setScreenNameVisible(screenNameCheckbox.isSelected()));
        screenNameCheckbox.setSelected(factory.isScreenNameVisible());

        nameCheckbox = new JCheckBox("Set name visible");
        nameCheckbox.addActionListener(e -> factory.setNameVisible(nameCheckbox.isSelected()));
        nameCheckbox.setSelected(factory.isNameVisible());

        setLayout(new GridLayout(2,1));
        add(nameCheckbox);
        add(screenNameCheckbox);

        setBorder(new TitledBorder("Name settings"));
    }
}

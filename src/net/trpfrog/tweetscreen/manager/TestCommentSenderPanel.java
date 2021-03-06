package net.trpfrog.tweetscreen.manager;

import net.trpfrog.tweetscreen.viewer.CommentFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TestCommentSenderPanel extends JPanel {
    private final JTextField testCommentTextField;
    private final CommentFrame CHILD_SCREEN;

    public TestCommentSenderPanel(CommentFrame childScreen) {
        CHILD_SCREEN = childScreen;
        testCommentTextField = new JTextField();

        setLayout(new BorderLayout());

        testCommentTextField.addActionListener(e -> {
            CHILD_SCREEN.getCommentProvider().addComment(testCommentTextField.getText());
            testCommentTextField.setText("");
        });
        add(testCommentTextField, BorderLayout.CENTER);

        setBorder(new TitledBorder("Add test comments"));
    }

}

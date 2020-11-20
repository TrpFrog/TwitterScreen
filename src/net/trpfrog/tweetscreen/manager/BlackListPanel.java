package net.trpfrog.tweetscreen.manager;

import net.trpfrog.tweetscreen.BlackList;
import net.trpfrog.tweetscreen.viewer.TwitterScreen;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class BlackListPanel extends JPanel {
    private final JTextField usernameTextField;

    public BlackListPanel() {
        usernameTextField = new JTextField();

        setLayout(new BorderLayout());
        add(usernameTextField, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1, 2));

        JButton addBtn = new JButton("BAN");
        addBtn.addActionListener(e -> {
            BlackList.getInstance().add(usernameTextField.getText());
            usernameTextField.setText("");
        });

        JButton removeBtn = new JButton("UNBAN");
        removeBtn.addActionListener(e -> {
            BlackList.getInstance().remove(usernameTextField.getText());
            usernameTextField.setText("");
        });

        btnPanel.add(removeBtn);
        btnPanel.add(addBtn);

        add(btnPanel, BorderLayout.SOUTH);

        setBorder(new TitledBorder("Black list manager"));
    }
}

package net.trpfrog.tweetscreen.manager;

import net.trpfrog.tweetscreen.viewer.TwitterScreen;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class FrameAppearanceSettingsPanel extends JPanel {
    private final TwitterScreen CHILD_SCREEN;

    private final JButton button;


    public FrameAppearanceSettingsPanel(TwitterScreen CHILD_SCREEN) {
        this.CHILD_SCREEN = CHILD_SCREEN;

        button = new JButton("Darken");
        button.addActionListener(e -> {
            CHILD_SCREEN.enableTransparency(!CHILD_SCREEN.isTransparent());
            if(button.getText().equals("Darken")) {
                button.setText("Brighten");
            } else {
                button.setText("Darken");
            }
        });

        add(button);

        setBorder(new TitledBorder("Appearance"));
    }

}

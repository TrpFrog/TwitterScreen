package net.trpfrog.tweetscreen.manager;

import net.trpfrog.tweetscreen.TwitterScreen;
import net.trpfrog.tweetscreen.viewer.ScreenConfigs;
import net.trpfrog.tweetscreen.viewer.CommentFrame;
import net.trpfrog.tweetscreen.stream.WordStreamReader;

import javax.swing.*;

public class ScreenManagerFrame extends JFrame {
    private final CommentFrame CHILD_SCREEN;
    private JButton transparentButton;
    private JTextField widthField, heightField, fpsField, speedField, debugField;

    public ScreenManagerFrame(CommentFrame CHILD_SCREEN, WordStreamReader streamReader, ScreenConfigs config) {
        this.CHILD_SCREEN = CHILD_SCREEN;

        setTitle("Settings");

        ImageIcon icon = new ImageIcon(TwitterScreen.getCurrentFilePath() + "resources/icon.png");
        setIconImage(icon.getImage());

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPane.add(new FrameAppearanceSettingsPanel(CHILD_SCREEN));
        contentPane.add(new WindowSizeSettingsPanel(CHILD_SCREEN));
        contentPane.add(new CommentSpeedSettingsPanel(CHILD_SCREEN));
        contentPane.add(new CommentTypeSettingPanel(streamReader.getCommentFactory()));
        contentPane.add(new BlackListPanel());
        contentPane.add(new TestCommentSenderPanel(CHILD_SCREEN));

        add(contentPane);

        setSize(300, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        setAlwaysOnTop(config.ALWAYS_ON_TOP);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

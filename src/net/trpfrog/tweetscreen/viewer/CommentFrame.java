package net.trpfrog.tweetscreen.viewer;

import net.trpfrog.tweetscreen.TwitterScreen;
import net.trpfrog.tweetscreen.comment.CommentProvider;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * コメントを描画する透明なスクリーン
 */
public class CommentFrame extends JFrame {

    private final CommentProvider COMMENT_PROVIDER;
    private final CommentPanel INNER_PANEL;
    private boolean transparent = false;

    public CommentFrame(ScreenConfigs config) {

        setSize(config.DEFAULT_WINDOW_WIDTH, config.DEFAULT_WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);

        ImageIcon icon = new ImageIcon(TwitterScreen.getCurrentFilePath() + "resources/icon.png");
        setIconImage(icon.getImage());

        // 透明化
        setUndecorated(true);
        setBackground(config.BACKGROUND_COLOR);
        getContentPane().setBackground(config.BACKGROUND_COLOR);

        // 透明なので赤枠を出す
        setAlwaysOnTop(config.ALWAYS_ON_TOP);

        INNER_PANEL = new CommentPanel(config);
        INNER_PANEL.setBounds(0, 0, config.DEFAULT_WINDOW_WIDTH, config.DEFAULT_WINDOW_HEIGHT);

        add(INNER_PANEL);

        // コメントは絶対座標で流したい
        setLayout(null);

        // デコレーションを切っているのでこれでドラッグできるようにする
        var fdr = new FrameDragListener(this);
        addMouseListener(fdr);
        addMouseMotionListener(fdr);

        COMMENT_PROVIDER = new CommentProvider(this, config);

        COMMENT_PROVIDER.addComment("Welcome to TwitterScreen!");
        COMMENT_PROVIDER.start();
        setVisible(true);
    }

    public CommentPanel getInnerPanel() {
        return INNER_PANEL;
    }

    public void resizeScreen(int w, int h) {
        Dimension d = new Dimension(w, h);
        setPreferredSize(d);
        INNER_PANEL.setSize(d);
        setVisible(false);
        pack();
        COMMENT_PROVIDER.onWindowStatusChanged();
        setVisible(true);
    }

    public CommentProvider getCommentProvider() {
        return COMMENT_PROVIDER;
    }

    public void enableTransparency(boolean enabled) {
        if(enabled) {
            setBackground(new Color(0,0,0,0.8f));
        } else {
            setBackground(new Color(0,0,0,0));
        }
        transparent = enabled;
    }

    public boolean isTransparent() {
        return transparent;
    }
}

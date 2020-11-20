package net.trpfrog.tweetscreen.viewer;

import net.trpfrog.tweetscreen.comment.CommentProvider;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * コメントを描画する透明なスクリーン
 */
public class TwitterScreen extends JFrame {

    private final CommentProvider COMMENT_PROVIDER;
    private final JPanel INNER_PANEL;
    private boolean transparent = false;

    public TwitterScreen(ScreenConfigs config) {

        setSize(config.DEFAULT_WINDOW_WIDTH, config.DEFAULT_WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);

        // 透明化
        setUndecorated(true);
        setBackground(config.BACKGROUND_COLOR);
        getContentPane().setBackground(config.BACKGROUND_COLOR);

        // 透明なので赤枠を出す
        setAlwaysOnTop(true);
        Border border = new LineBorder(config.BORDER_COLOR);
        INNER_PANEL = new JPanel();
        INNER_PANEL.setBorder(border);
        INNER_PANEL.setBackground(new Color(0,0,0,0));

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

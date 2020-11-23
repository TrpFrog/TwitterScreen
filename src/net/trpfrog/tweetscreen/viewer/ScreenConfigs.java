package net.trpfrog.tweetscreen.viewer;

import net.trpfrog.tweetscreen.CrashWindow;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ScreenConfigs {
    private final Properties PROPERTIES;

    public final int DEFAULT_WINDOW_WIDTH;
    public final int DEFAULT_WINDOW_HEIGHT;
    public final Color BACKGROUND_COLOR;
    public final Color BORDER_COLOR;

    public final int FONT_SIZE;
    public final Color FONT_COLOR;
    public final boolean HAS_FONT_BORDER;
    public final Color FONT_BORDER_COLOR;
    public final int FONT_BORDER_SIZE_PT;

    public final int DEFAULT_UPDATE_INTERVAL_MS;
    public final int DEFAULT_COMMENT_SPEED;

    public final boolean ALWAYS_ON_TOP;
    public final boolean SHOW_SCREEN_NAME;
    public final boolean SHOW_NAME;

    public final boolean REMOVE_LINKS;
    public final boolean REMOVE_HASHTAGS;
    public final boolean REMOVE_REPLY;

    public ScreenConfigs(String path) {
        PROPERTIES = new Properties();
        try {
            PROPERTIES.load(Files.newBufferedReader(Paths.get(path)));
        } catch (IOException e) {
            new CrashWindow(e);
        }

        DEFAULT_WINDOW_WIDTH = getIntProperty("default_window_width", "800");
        DEFAULT_WINDOW_HEIGHT = getIntProperty("default_window_height", "600");

        BACKGROUND_COLOR = getAlphaColorProperty("background_color_argb", "ff00ff00");
        BORDER_COLOR = getColorProperty("border_color_rgb", "ffffff");

        FONT_SIZE = getIntProperty("font_size", "30");
        FONT_COLOR = getColorProperty("font_color_rgb", "ffffff");
        HAS_FONT_BORDER = getBooleanProperty("show_font_border", "false");
        FONT_BORDER_COLOR = getColorProperty("font_border_color_rgb", "000000");
        FONT_BORDER_SIZE_PT = getIntProperty("font_border_size_pt", "2");

        DEFAULT_UPDATE_INTERVAL_MS = getIntProperty("default_update_interval_ms", "50");
        DEFAULT_COMMENT_SPEED = getIntProperty("default_comment_speed", "100");

        ALWAYS_ON_TOP = getBooleanProperty("always_on_top", "false");

        SHOW_SCREEN_NAME = getBooleanProperty("show_screen_name", "false");
        SHOW_NAME = getBooleanProperty("show_name", "false");

        REMOVE_LINKS = getBooleanProperty("remove_links", "true");
        REMOVE_HASHTAGS = getBooleanProperty("remove_hashtags", "true");
        REMOVE_REPLY = getBooleanProperty("remove_reply", "true");
    }

    private Color getAlphaColorProperty(String key, String defaultValue) {
        Color c = Color.WHITE;
        try {
            c = new Color(Integer.parseUnsignedInt(PROPERTIES.getProperty(key, defaultValue), 16), true);
        } catch (Exception e) {
            new CrashWindow(e);
        }
        return c;
    }

    private Color getColorProperty(String key, String defaultValue) {
        Color c = Color.WHITE;
        try {
            c = new Color(Integer.parseInt(PROPERTIES.getProperty(key, defaultValue), 16));
        } catch (Exception e) {
            new CrashWindow(e);
        }
        return c;
    }

    private int getIntProperty(String key, String defaultValue) {
        int x = 0;
        try {
            x = Integer.parseInt(PROPERTIES.getProperty(key, defaultValue));
        } catch (Exception e) {
            new CrashWindow(e);
        }
        return x;
    }

    private boolean getBooleanProperty(String key, String defaultValue) {
        boolean b = true;
        try {
            b = Boolean.parseBoolean(PROPERTIES.getProperty(key, defaultValue));
        } catch (Exception e) {
            new CrashWindow(e);
        }
        return b;
    }

}

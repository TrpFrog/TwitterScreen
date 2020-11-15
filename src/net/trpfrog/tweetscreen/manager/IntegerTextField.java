package net.trpfrog.tweetscreen.manager;

import javax.swing.*;

public class IntegerTextField extends JTextField {
    private int value;

    public IntegerTextField(int originalValue) {
        super(originalValue + "");
        value = originalValue;
    }

    public IntegerTextField() {
        this(0);
    }

    public int getValue() {
        try {
            value = Integer.parseInt(getText());
        } catch (NumberFormatException ex) {
            // do nothing
        }
        setText(value + "");
        return value;
    }
}

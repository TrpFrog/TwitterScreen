package net.trpfrog.tweetscreen;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CrashWindow extends JFrame {
    public CrashWindow(Exception ex) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        pw.flush();

        ex.printStackTrace();

        setLayout(new BorderLayout());
        setTitle("Crash Report");

        JTextArea stackTrace = new JTextArea(sw.toString());
        stackTrace.setEditable(false);
        stackTrace.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        stackTrace.setForeground(new Color(150, 0, 0));

        add(stackTrace, BorderLayout.CENTER);
        JButton exitBtn = new JButton("Quit");
        exitBtn.addActionListener(e -> System.exit(-1));
        add(exitBtn, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        setVisible(true);

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(-1);
    }
}

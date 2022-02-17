package com.company;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame
{
    public Main()
    {
        this.setTitle("Planer");
        int x = Toolkit.getDefaultToolkit().getScreenSize().width;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = x/4;
        int height = y/8;
        this.setBounds(x/2-width/2, y/2-height/2, width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        pack();
    }
    private void init()
    {
        panel.add(newButton);
        panel.add(readButton);
        this.getContentPane().add(panel, BorderLayout.CENTER);
        newButton.addActionListener(e -> {
            thisFrame.dispose();
            new Planer().setVisible(true);
        });
        readButton.addActionListener(e -> {
            thisFrame.dispose();
            Planer planer = new Planer();
            planer.readFileMethod();
            planer.setVisible(true);
        });
    }

    private JPanel panel = new JPanel();
    private JButton newButton = new JButton("Nowy planer");
    private JButton readButton = new JButton("Wczytaj planer");
    private JFrame thisFrame = this;

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}

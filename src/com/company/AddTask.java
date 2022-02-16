package com.company;

import javax.swing.*;
import java.awt.*;

public class AddTask extends JFrame
{
    public AddTask()
    {
        this.setTitle("Dodaj zadanie");
        int x = Toolkit.getDefaultToolkit().getScreenSize().width;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = x/4;
        int height = y/6;
        this.setBounds(x/2-width/2, y/2-height/2, width, height);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        init();
    }
    private void init()
    {
        textPanel.add(scrollPane);
        buttonPanel.add(buttonAdd);
        this.getContentPane().add(textPanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        scrollPane.createVerticalScrollBar();
        scrollPane.createHorizontalScrollBar();
    }

    private JPanel textPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JTextArea taskText = new JTextArea(20, 30);
    private JScrollPane scrollPane = new JScrollPane(taskText);
    private JButton buttonAdd = new JButton("Dodaj");
    public static void main(String[] args) {
        new AddTask().setVisible(true);
    }
}

package com.company;

import javax.swing.*;
import java.awt.*;

public class AddTask extends JFrame
{
    public AddTask(Planer.Task task)
    {
        this.setTitle("Dodaj zadanie");
        int x = Toolkit.getDefaultToolkit().getScreenSize().width;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = x/4;
        int height = y/6;
        this.setBounds(x/2-width/2, y/2-height/2, width, height);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        init(task);
    }
    private void init(Planer.Task task)
    {
        textPanel.add(scrollPane);
        buttonPanel.add(buttonAdd);
        buttonAdd.addActionListener(e -> {
            task.setTaskDesc(taskText.getText());
            Planer.addTask(task);
            thisFrame.dispose();
        });
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
    private JFrame thisFrame = this;

    public static void main(String[] args) {
    }
}

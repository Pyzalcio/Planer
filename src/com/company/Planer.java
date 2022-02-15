package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Planer extends JFrame
{
    public Planer()
    {
        this.setTitle("Planer");
        int x = Toolkit.getDefaultToolkit().getScreenSize().width;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = 3*x/4;
        int height = 3*y/4;
        this.setBounds(x/2-width/2, y/2-height/2, width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        createFileMenu();
        createEditMenu();
        createCloseMenu();
    }
    private void createFileMenu()
    {
        JMenu file = menuBar.add(new JMenu("Plik"));
        JMenuItem newFile = file.add(new JMenuItem("Nowy"));
        JMenuItem readFile = file.add(new JMenuItem("Wczytaj"));
        JMenuItem saveFile = file.add(new JMenuItem("Zapisz"));
        JMenuItem saveAsFile = file.add(new JMenuItem("Zapisz jako..."));
    }
    private void createEditMenu()
    {
        JMenu edit = menuBar.add(new JMenu("Edytuj"));
        JMenuItem addTask = edit.add(new JMenuItem("Dodaj zadanie"));
        JMenuItem editTask = edit.add(new JMenuItem("Edytuj zadanie"));
        JMenuItem delTask = edit.add(new JMenuItem("Usuń zadanie"));
    }
    private void createCloseMenu()
    {
        JMenu close = menuBar.add(new JMenu("Zamknij"));
        JMenuItem onlyClose = close.add(new JMenuItem("Zamknij bez zapisywania"));
        JMenuItem saveAndClose = close.add(new JMenuItem("Zapisz i zamknij"));
    }
    private void init()
    {
        this.setJMenuBar(menuBar);
        northPanel.add(dateLabel);
        this.getContentPane().add(northPanel, BorderLayout.NORTH);
        this.getContentPane().add(centralPanel, BorderLayout.CENTER);
        this.getContentPane().add(southPanel, BorderLayout.SOUTH);
    }

    private GregorianCalendar calendar = (GregorianCalendar) new GregorianCalendar().clone();
    private JLabel dateLabel = new JLabel(calendar.get(Calendar.DATE)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
    private JPanel northPanel = new JPanel();
    private JPanel centralPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JMenuBar menuBar = new JMenuBar();

    public static void main(String[] args) {
        new Planer().setVisible(true);
    }
}

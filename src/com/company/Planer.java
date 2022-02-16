package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private void init()
    {
        this.setJMenuBar(menuBar);
        this.getContentPane().add(northPanel, BorderLayout.NORTH);
        this.getContentPane().add(centralPanel, BorderLayout.CENTER);
        this.getContentPane().add(southPanel, BorderLayout.SOUTH);
        centralPanel.setLayout(new GridLayout(6, 7));
        createNorthPanel();
        createCalendar();
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
        addTask.addActionListener(e -> new AddTask().setVisible(true));
        JMenuItem editTask = edit.add(new JMenuItem("Edytuj zadanie"));
        JMenuItem delTask = edit.add(new JMenuItem("Usuń zadanie"));
    }
    private void createCloseMenu()
    {
        JMenu close = menuBar.add(new JMenu("Zamknij"));
        JMenuItem onlyClose = close.add(new JMenuItem("Zamknij bez zapisywania"));
        JMenuItem saveAndClose = close.add(new JMenuItem("Zapisz i zamknij"));
    }
    private void createNorthPanel()
    {
        dateLabel.setText(calendar.get(Calendar.DATE)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
        JButton lastMonthButton = new JButton("<");
        JButton nextMonthButton = new JButton(">");
        JButton lastYearButton = new JButton("<<");
        JButton nextYearButton = new JButton(">>");
        northPanel.add(lastYearButton);
        northPanel.add(lastMonthButton);
        northPanel.add(dateLabel);
        northPanel.add(nextMonthButton);
        northPanel.add(nextYearButton);
        lastMonthButton.addActionListener(e -> {
            if(calendar.get(Calendar.MONTH)==Calendar.JANUARY)
                calendar.roll(Calendar.YEAR, -1);
            calendar.roll(Calendar.MONTH, -1);
            centralPanel.removeAll();
            createCalendar();
            dateLabel.setText(calendar.get(Calendar.DATE)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
        });
        nextMonthButton.addActionListener(e -> {
            if(calendar.get(Calendar.MONTH)==Calendar.DECEMBER)
                calendar.roll(Calendar.YEAR, 1);
            calendar.roll(Calendar.MONTH, 1);
            centralPanel.removeAll();
            createCalendar();
            dateLabel.setText(calendar.get(Calendar.DATE)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
        });
        lastYearButton.addActionListener(e -> {
            calendar.roll(Calendar.YEAR, -1);
            centralPanel.removeAll();
            createCalendar();
            dateLabel.setText(calendar.get(Calendar.DATE)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
        });
        nextYearButton.addActionListener(e -> {
            calendar.roll(Calendar.YEAR, 1);
            centralPanel.removeAll();
            createCalendar();
            dateLabel.setText(calendar.get(Calendar.DATE)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
        });
    }
    private void createCalendar()
    {
        int dayInActualMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DATE, 1);
        int fristDay = calendar.get(Calendar.DAY_OF_WEEK)-1;
        calendar.roll(Calendar.MONTH, -1);
        int dayInLastMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.roll(Calendar.MONTH, 1);
        for(int i=1; i<=42; i++)
        {
            JButton dayButton = new JButton();
            if(i<fristDay) {
                dayButton.setText(String.valueOf(dayInLastMonth + i - (fristDay-1)));
                dayButton.setEnabled(false);
            }
            else if( i >= (dayInActualMonth+fristDay) )
            {
                dayButton.setText(String.valueOf(i-dayInActualMonth-1));
                dayButton.setEnabled(false);
            }
            else
                dayButton.setText(String.valueOf(i-fristDay+1));

            centralPanel.add(dayButton);
        }
    }

    private GregorianCalendar calendar = new GregorianCalendar();
    private JLabel dateLabel = new JLabel();
    private JPanel northPanel = new JPanel();
    private JPanel centralPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JMenuBar menuBar = new JMenuBar();

    public static void main(String[] args) {
        new Planer().setVisible(true);
    }
}

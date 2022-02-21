package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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
        createTaskPanel();
    }
    void saveFileMethod()
    {
        try {
            SaveFile.save(fileName, taskList);
            new NewDialog(thisFrame, "Zapis", "Zapisano poprawnie").setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
            new NewDialog(thisFrame, "Zapis", "Błąd zapisu").setVisible(true);
        }
    }
    void readFileMethod()
    {
        try {
            FileDialog fd = new FileDialog(thisFrame,"Wczytaj",FileDialog.LOAD);
            fd.setVisible(true);
            fileName=fd.getDirectory()+"/"+fd.getFile();
            ReadFile readFile = new ReadFile();
            readFile.read(fileName);
            new NewDialog(thisFrame, "Odczyt", "Wczytano poprawnie").setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
            new NewDialog(thisFrame, "Odczyt", "Błąd odczytu").setVisible(true);
        }
    }

    private void createFileMenu()
    {
        JMenu file = menuBar.add(new JMenu("Plik"));
        JMenuItem newFile = file.add(new JMenuItem("Nowy"));
        newFile.addActionListener(e -> {
            taskList.clear();
            fileName = "nowyPlaner.txt";
        });
        JMenuItem readFile = file.add(new JMenuItem("Wczytaj"));
        readFile.addActionListener(e -> readFileMethod());
        JMenuItem saveFile = file.add(new JMenuItem("Zapisz"));
        saveFile.addActionListener(e -> saveFileMethod());
        JMenuItem saveAsFile = file.add(new JMenuItem("Zapisz jako..."));
        saveAsFile.addActionListener(e -> {
            FileDialog fd = new FileDialog(thisFrame,"Zapisz",FileDialog.SAVE);
            fd.setVisible(true);
            fileName=fd.getDirectory()+"/"+fd.getFile();
            saveFileMethod();
        });
    }
    private void createEditMenu()
    {
        JMenu edit = menuBar.add(new JMenu("Edytuj"));
        JMenuItem addTask = edit.add(new JMenuItem("Dodaj zadanie"));
        addTask.addActionListener(e -> {
            Task task = new Task();
            AddTask newAddFrame = new AddTask(task);
            newAddFrame.setVisible(true);
            task.setYear(calendar.get(Calendar.YEAR));
            task.setMonth(calendar.get(Calendar.MONTH)+1);
            task.setDay(calendar.get(Calendar.DATE));
            });
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
        int tempDate = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, 1);
        int fristDay = calendar.get(Calendar.DAY_OF_WEEK)-1;
        calendar.roll(Calendar.MONTH, -1);
        int dayInLastMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.roll(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, tempDate);
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

            dayButton.addActionListener(e -> {
                calendar.set(Calendar.DATE, Integer.parseInt(dayButton.getText()));
                dateLabel.setText(calendar.get(Calendar.DATE)+"."+(calendar.get(Calendar.MONTH)+1)
                        +"."+calendar.get(Calendar.YEAR));
                createTaskPanel();
            });

            centralPanel.add(dayButton);
        }
    }
    private void createTaskPanel()
    {
        southPanel.removeAll();
        southPanel.repaint();
        List<Task> tempList = new ArrayList();
        southPanel.add(southLabel);
        for (Task task : taskList) {
            if (task.getYear() == calendar.get(Calendar.YEAR) &&
                    task.getMonth() == calendar.get(Calendar.MONTH) + 1 &&
                    task.getDay() == calendar.get(Calendar.DATE))
                tempList.add(task);
        }
        if(tempList.size()>0)
        {
            southLabel.setText("Zadania w wybranym dniu: ");
            southPanel.setLayout(new GridLayout(tempList.size()+1, 1));
            for (Task task : tempList) {
                southPanel.add(new JLabel(task.getTaskDesc()));
            }
        }
        else
            southLabel.setText("Brak zadań w wybranym dniu");

    }

    static void addTask(Task task)
    {
        taskList.add(task);
        System.out.println("Dodano zadanie: "+task.getTaskDesc()+": "+task.getDay()+"."+task.getMonth()+"."+task.getYear());
    }
    static class Task
    {
        String taskDesc;
        int year;
        int month;
        int day;

        public String getTaskDesc() {
            return taskDesc;
        }

        public void setTaskDesc(String taskDesc) {
            this.taskDesc = taskDesc;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }

    private JFrame thisFrame = this;
    private GregorianCalendar calendar = new GregorianCalendar();
    private JLabel dateLabel = new JLabel();
    private JPanel northPanel = new JPanel();
    private JPanel centralPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JLabel southLabel = new JLabel("Brak zadań w wybranym dniu");
    private static List<Task> taskList = new ArrayList();
    private String fileName = "nowyPlaner.txt";

    public static void main(String[] args) {
        new Planer().setVisible(true);
    }
}

package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
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
        String statement = null;
        try {
            FileDialog fd = new FileDialog(thisFrame,"Wczytaj",FileDialog.LOAD);
            fd.setVisible(true);
            fileName=fd.getDirectory()+"/"+fd.getFile();
            ReadFile readFile = new ReadFile();
            readFile.read(fileName);
            statement = "Wczytano poprawnie";
            saveFile.setEnabled(true);
            saveAndClose.setEnabled(true);
        } catch (IOException ex) {
            ex.printStackTrace();
            statement = "Błąd odczytu";
        }
        finally {
            new NewDialog(thisFrame, "Odczyt", statement).setVisible(true);
        }
    }

    private void createFileMenu()
    {
        JMenu file = menuBar.add(new JMenu("Plik"));
        JMenuItem newFile = file.add(new JMenuItem("Nowy"));
        newFile.addActionListener(e -> {
            taskList.clear();
            fileName = null;
            saveFile.setEnabled(false);
            saveAndClose.setEnabled(false);
        });
        JMenuItem readFile = file.add(new JMenuItem("Wczytaj"));
        readFile.addActionListener(e -> readFileMethod());
        saveFile = file.add(new JMenuItem("Zapisz"));
        saveFile.addActionListener(e -> saveFileMethod());
        saveFile.setEnabled(fileName != null);
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
        editTask.addActionListener(e -> {
            if(taskList.size()<1)
            {
                new NewDialog(thisFrame, "Brak zadań", "Nie ma żadnych zadań").setVisible(true);
            }
            else {
                EditTask newEditTask = new EditTask(taskList, calendar);
            }
        });
        JMenuItem delTask = edit.add(new JMenuItem("Usuń zadanie"));
        delTask.addActionListener(e -> {
            if(taskList.size()<1)
            {
                new NewDialog(thisFrame, "Brak zadań", "Nie ma żadnych zadań").setVisible(true);
            }
            else {
                DelTask newdelTask = new DelTask(taskList, calendar);
            }
        });
    }
    private void createCloseMenu()
    {
        JMenu close = menuBar.add(new JMenu("Zamknij"));
        JMenuItem onlyClose = close.add(new JMenuItem("Zamknij bez zapisywania"));
        onlyClose.addActionListener(e -> thisFrame.dispose());
        saveAndClose = close.add(new JMenuItem("Zapisz i zamknij"));
        saveAndClose.addActionListener(e -> {
            saveFileMethod();
            thisFrame.dispose();
        });
        saveAndClose.setEnabled(fileName != null);
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
        southPanel.setLayout(new FlowLayout());
        southPanel.removeAll();
        southPanel.repaint();
        List<Task> tempList = new ArrayList<>();
        southPanel.add(southLabel);
        for (Task task : taskList) {
            if (task.getYear() == calendar.get(Calendar.YEAR) &&
                    task.getMonth() == calendar.get(Calendar.MONTH) + 1 &&
                    task.getDay() == calendar.get(Calendar.DATE))
                tempList.add(task);
        }
        Collections.sort(tempList);
        if(tempList.size()>0)
        {
            southLabel.setText("Zadania w wybranym dniu: ");
            southPanel.setLayout(new GridLayout(tempList.size()+1, 1));
            String hour;
            String minute;
            for (Task task : tempList) {
                if(task.getHour()<10)
                    hour = "0"+task.getHour();
                else
                    hour = String.valueOf(task.getHour());
                if(task.getMinute()<10)
                    minute = "0"+task.getMinute();
                else
                    minute = String.valueOf(task.getMinute());
                southPanel.add(new JLabel(hour+":"+minute+ " " + task.getTaskDesc()));
            }
        }
        else
            southLabel.setText("Brak zadań w wybranym dniu");

    }

    static void addTask(Task task)
    {
        taskList.add(task);
        System.out.println("Dodano zadanie: "+task.getTaskDesc()+": "+task.getDay()+"."+task.getMonth()
                +"."+task.getYear() + ", o godzinie "+task.getHour()+":"+task.getMinute());
    }
    static class Task implements Comparable<Task>
    {
        String taskDesc;
        int year;
        int month;
        int day;
        int hour;
        int minute;

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

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        @Override
        public int compareTo(Task o) {
            if( this.hour > o.getHour() )
                return 1;
            if( this.hour == o.getHour() )
            {
                if( this.minute > o.getMinute() )
                    return 1;
                else
                    return -1;
            }
            else
                return -1;
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
    private static List<Task> taskList = new ArrayList<>();
    private String fileName = null;

    private JMenuItem saveFile;
    private JMenuItem saveAndClose;

    public static void main(String[] args) {
        new Planer().setVisible(true);
    }
}

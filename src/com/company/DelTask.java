package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

public class DelTask extends JFrame
{
    public DelTask(List<Planer.Task> taskList, GregorianCalendar calendar)
    {
        this.setTitle("Usuń zadanie");
        int x = Toolkit.getDefaultToolkit().getScreenSize().width;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = x/4;
        int height = y/6;
        this.setBounds(x/2-width/2, y/2-height/2, width, height);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        init(taskList, calendar);
    }
    private void init(List<Planer.Task> taskList, GregorianCalendar calendar)
    {
        Planer.Task temp;
        int j=1;
        for(int i=0; i<taskList.size(); i++)
        {
            temp = taskList.get(i);
            if(temp.getYear() == calendar.get(Calendar.YEAR)
                    && temp.getMonth() == calendar.get(Calendar.MONTH)+1
                    && temp.getDay() == calendar.get(Calendar.DATE))
            {
                comboBox.addItem(new SelectTask(j++, i, temp.getTaskDesc()));
            }
        }
        buttonDel.addActionListener(e -> {
            int idToRemove = ((SelectTask)(Objects.requireNonNull(comboBox.getSelectedItem()))).getId();
            String statement = "Usunięto zadanie: " + ((SelectTask)(comboBox.getSelectedItem())).getDesc();
            new NewDialog(thisFrame, "Usuwanie", statement).setVisible(true);
            taskList.remove(idToRemove);
            thisFrame.dispose();
        });
        centerPanel.add(comboBox);
        buttonPanel.add(buttonDel);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private int []taskInDays;
    private JComboBox<SelectTask> comboBox = new JComboBox<>();
    private JPanel centerPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JButton buttonDel = new JButton("Usuń");
    private JFrame thisFrame = this;

    private class SelectTask
    {
        private int number;
        private int id;
        private String desc;
        public SelectTask(int number, int id, String desc)
        {
            this.number = number;
            this.id = id;
            this.desc = desc;
        }
        public String toString()
        {
            return number+". "+desc;
        }
        public int getId()
        {
            return id;
        }
        public int getNumber()
        {
            return number;
        }
        public String getDesc()
        {
            return desc;
        }
    }
    public static void main(String[] args) {
    }
}

package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

public class EditTask extends JFrame
{
    public EditTask(List<Planer.Task> taskList, GregorianCalendar calendar)
    {
        this.setTitle("Edytuj zadanie");
        int x = Toolkit.getDefaultToolkit().getScreenSize().width;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = x/4;
        int height = y/4;
        this.setBounds(x/2-width/2, y/2-height/2, width, height);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        init(taskList, calendar);
    }
    private void init(List<Planer.Task> taskList, GregorianCalendar calendar)
    {
        comboBoxesInit(taskList, calendar);
        buttonEdit.addActionListener(e -> {
            int idToEdit = ((DelTask.SelectTask)(Objects.requireNonNull(comboBox.getSelectedItem()))).getId();
            String statement = "Edytowano zadanie: " + ((DelTask.SelectTask)(comboBox.getSelectedItem())).getDesc();
            new NewDialog(thisFrame, "Edycja", statement).setVisible(true);
            taskList.get(idToEdit).setTaskDesc(taskText.getText());
            taskList.get(idToEdit).setHour(Integer.parseInt((String) Objects.requireNonNull(comboBoxHour.getSelectedItem())));
            taskList.get(idToEdit).setMinute(Integer.parseInt((String) Objects.requireNonNull(comboBoxMinute.getSelectedItem())));
            thisFrame.dispose();
        });
        northPanel.add(comboBox);
        buttonPanel.add(buttonEdit);
        centerPanel.add(comboBoxHour);
        centerPanel.add(comboBoxMinute);
        centerPanel.add(scrollPane);
        this.getContentPane().add(northPanel, BorderLayout.NORTH);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonEdit, BorderLayout.SOUTH);
        scrollPane.createVerticalScrollBar();
        scrollPane.createHorizontalScrollBar();
        if(comboBox.getItemCount()==0)
        {
            new NewDialog(thisFrame, "Brak zadań", "W wybranym dniu nie ma żadnych zadań!").setVisible(true);
            thisFrame.dispose();
        }
        else
            thisFrame.setVisible(true);
        setSelectedItem(taskList);
    }
    private void comboBoxesInit(List<Planer.Task> taskList, GregorianCalendar calendar)
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
                comboBox.addItem(new DelTask.SelectTask(j++, i, temp.getTaskDesc()));
            }
        }
        for(int i=0; i<24; i++)
        {
            if(i<10)
                comboBoxHour.addItem("0"+i);
            else
                comboBoxHour.addItem(i+"");
        }
        for (int i=0; i<60; i++)
        {
            if(i<10)
                comboBoxMinute.addItem("0"+i);
            else
                comboBoxMinute.addItem(i+"");
        }
        comboBox.addActionListener(e -> setSelectedItem(taskList));
    }
    private void setSelectedItem(List<Planer.Task> taskList)
    {
        int idTask = ((DelTask.SelectTask)(Objects.requireNonNull(comboBox.getSelectedItem()))).getId();
        int indHour = taskList.get(idTask).getHour();
        int indMinute = taskList.get(idTask).getMinute();
        String text = taskList.get(idTask).getTaskDesc();
        comboBoxHour.setSelectedIndex(indHour);
        comboBoxMinute.setSelectedIndex(indMinute);
        taskText.setText(text);
    }

    private JComboBox<DelTask.SelectTask> comboBox = new JComboBox<>();
    private JComboBox<String> comboBoxHour = new JComboBox<>();
    private JComboBox<String> comboBoxMinute = new JComboBox<>();
    private JPanel northPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JTextArea taskText = new JTextArea(5, 30);
    private JScrollPane scrollPane = new JScrollPane(taskText);
    private JButton buttonEdit = new JButton("Edytuj");
    private JFrame thisFrame = this;

    public static void main(String[] args) {
    }
}

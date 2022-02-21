package com.company;

import javax.swing.*;
import java.awt.*;
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
                comboBox.addItem(new DelTask.SelectTask(j++, i, temp.getTaskDesc()));
            }
        }

        buttonEdit.addActionListener(e -> {
            int idToEdit = ((DelTask.SelectTask)(Objects.requireNonNull(comboBox.getSelectedItem()))).getId();
            String statement = "Edytowano zadanie: " + ((DelTask.SelectTask)(comboBox.getSelectedItem())).getDesc();
            new NewDialog(thisFrame, "Edycja", statement).setVisible(true);
            taskList.get(idToEdit).setTaskDesc(taskText.getText());
            thisFrame.dispose();
        });
        centerPanel.add(comboBox);
        buttonPanel.add(buttonEdit);
        textPanel.add(scrollPane);
        this.getContentPane().add(centerPanel, BorderLayout.NORTH);
        this.getContentPane().add(textPanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        if(comboBox.getItemCount()==0)
        {
            new NewDialog(thisFrame, "Brak zadań", "W wybranym dniu nie ma żadnych zadań!").setVisible(true);
            thisFrame.dispose();
        }
        else
            thisFrame.setVisible(true);
    }

    private JComboBox<DelTask.SelectTask> comboBox = new JComboBox<>();
    private JPanel centerPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel textPanel = new JPanel();
    private JTextArea taskText = new JTextArea(20, 30);
    private JScrollPane scrollPane = new JScrollPane(taskText);
    private JButton buttonEdit = new JButton("Edytuj");
    private JFrame thisFrame = this;

    public static void main(String[] args) {
    }
}

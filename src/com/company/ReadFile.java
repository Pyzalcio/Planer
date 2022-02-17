package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadFile
{
    void read(String fileName) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine())
        {
            stringToTask(scanner.nextLine());
        }
        scanner.close();
        reader.close();
    }
    private void stringToTask(String text)
    {
        Planer.Task task = new Planer.Task();
        StringBuilder temp = new StringBuilder();
        int []taskDate = new int[3];
        int pointer=0;
        for(int i=text.length()-1; i>=0; i--)
        {
            if(text.charAt(i) != '.' && pointer<=2)
                temp.append(text.charAt(i));
            else if(text.charAt(i) == '.' && pointer<=2)
            {
                taskDate[pointer] = Integer.parseInt(reserveString(temp.toString()));
                pointer++;
                temp = new StringBuilder();
            }
            else
                temp.append(text.charAt(i));
        }
        task.setTaskDesc(reserveString(temp.toString()));
        task.setDay(taskDate[0]);
        task.setMonth(taskDate[1]);
        task.setYear(taskDate[2]);
        Planer.addTask(task);
    }
    private String reserveString(String text)
    {
        StringBuilder temp = new StringBuilder();
        for(int i=text.length()-1; i>=0; i--)
        {
            temp.append(text.charAt(i));
        }
        return temp.toString();
    }
}

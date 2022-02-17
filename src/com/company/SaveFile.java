package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveFile
{
    static void save(String fileName, List<Planer.Task> taskList) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        Planer.Task tempTask;
        for (Planer.Task task : taskList) {
            tempTask = task;
            writer.write(tempTask.getTaskDesc() + "." + tempTask.getYear() + "." + tempTask.getMonth() + "." + tempTask.getDay());
            writer.newLine();
        }
        writer.close();
    }
}

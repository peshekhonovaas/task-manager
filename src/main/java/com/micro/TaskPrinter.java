package com.micro;

import com.micro.model.Task;
import nonapi.io.github.classgraph.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class TaskPrinter {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"; // Format for date display
    private static final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

   public static void printTasks(List<Task> tasks) {
       if (tasks != null && !tasks.isEmpty()) {
           for (Task task : tasks) {
               if (task.getCategory().equals("Task")) {
                   System.out.println("-----------------------------------------------------");
                   System.out.printf("Task ID: %s%n", task.getId() != null ? task.getId() : "No ID");
                   System.out.printf("Name: %s%n", task.getName());
                   System.out.printf("Description: %s%n", task.getDescription());
                   System.out.printf("Category: %s%n", task.getCategory());
                   System.out.printf("Deadline: %s%n", task.getDeadline() != null ? formatter.format(task.getDeadline()) : "No deadline");
                   System.out.printf("Created Date: %s%n", task.getDate() != null ? formatter.format(task.getDate()) : "No creation date");

                   printSubTasks(task.getSubTasks());
               }
           }
       } else {
           System.out.println("No tasks found.");
       }
   }

   public static void printSubTasks(List<Task> subTasks) {
       if (subTasks != null && !subTasks.isEmpty()) {
           System.out.println("Subtasks:");
           for (Task subTask : subTasks) {
               System.out.printf("    SubTask ID: %s%n", subTask.getId());
               System.out.printf("    Name: %s%n", subTask.getName());
               System.out.printf("    Description: %s%n", subTask.getDescription());
           }
       } else {
           System.out.println("No subtasks.");
       }
   }
}

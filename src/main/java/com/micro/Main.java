package com.micro;

import com.micro.dao.TaskDAO;
import com.micro.model.Task;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        TaskDAO taskDAO = new TaskDAO();
        //5.Perform insert
        List<Task> subTasks = initializeSubTasks();

        taskDAO.saveTasks(subTasks);
        taskDAO.saveTasks(initializeTasks(subTasks));

        System.out.println("1.Display on console all tasks:");
        List<Task> tasks = taskDAO.getAllTasks();
        TaskPrinter.printTasks(tasks);

        System.out.println("2.Display overdue tasks:");
        TaskPrinter.printTasks(taskDAO.getOverdueTasks());

        System.out.println("3.Display all tasks with a specific category Task:");
        TaskPrinter.printTasks(taskDAO.getTasksByCategory("Task"));

        System.out.println("4.Display all subtasks related to tasks with a specific category SubTask:");
        TaskPrinter.printSubTasks(taskDAO.getSubTasksByCategory("SubTask"));

        //5.Perform update
        taskDAO.updateDeadlineForOverdueTasks(new Date());

        //6.Perform insert subtask of a given task
        taskDAO.insertSubtask(tasks.get(3).getId(), new Task("Subtask 4", "Description 4", "SubTasks", null, null));

        //6.Perform update all subtask of a given task (query parameter)
        taskDAO.updateNameOfSubtaskToTask(tasks.get(3).getSubTasks().get(0).getId(), "Subtask 3.1");

        System.out.println("7.Support full-text search by word in the task description /1/: ");
        TaskPrinter.printTasks(taskDAO.findByWordDescription("1"));

        System.out.println("8.Support full-text search by a sub-task name /Subtask 2/: ");
        TaskPrinter.printTasks(taskDAO.findTaskBySubTaskName("Subtask 2"));

        //6.Perform delete all subtask of a given task (query parameter)
        taskDAO.deleteSubtasks(tasks.get(3).getSubTasks().get(0).getId());

        //5.Perform delete
        taskDAO.deleteTask(tasks.get(3).getId());
    }

    private static List<Task> initializeSubTasks() {
        Task subtask1 = new Task("Subtask 1", "Description 1", "SubTask", null, null);
        Task subtask2 = new Task("Subtask 2", "Description 2", "SubTask", null, null);
        return Arrays.asList(subtask1, subtask2);
    }

    private static List<Task> initializeTasks(List<Task> subtasks) {
        Task task1 = new Task("Task 1", "Task with subtasks", "Task",
                new Calendar.Builder().setDate(2025, Calendar.NOVEMBER, 1).build().getTime(), new Date());
        task1.getSubTasks().add(subtasks.get(0));
        task1.getSubTasks().add(subtasks.get(1));

        Task task2 = new Task("Task 2", "Task with 1 subtask", "Task",
                new Calendar.Builder().setDate(2023, Calendar.NOVEMBER, 1).build().getTime(),
                new Calendar.Builder().setDate(2022, Calendar.NOVEMBER, 1).build().getTime());
        task2.getSubTasks().add(subtasks.get(0));
        return Arrays.asList(task1, task2);
    }
}
package com.micro.dao;

import com.micro.model.Task;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.filters.Filters;
import dev.morphia.query.updates.UpdateOperators;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskDAO {
    private final static String DATABASE = "MongoDBTasks";
    private final static String MONGODB_URI = "mongodb://localhost:27017/";
    private final Datastore datastore;

    public TaskDAO() {
        this.datastore = Morphia.createDatastore(MongoClients.create(MONGODB_URI), DATABASE);
        this.datastore.getMapper().map(Task.class);
        datastore.ensureIndexes();
    }

    public void saveTask(Task task) {
        this.datastore.save(task);
    }

    public void saveTasks(List<Task> tasks) {
        this.datastore.save(tasks);
    }

    public List<Task> getSubTasksByCategory(String category) {
       return this.datastore.find(Task.class)
                .filter(Filters.eq("category", category))
                .stream()
                .toList();
    }

    public List<Task> getTasksByCategory(String category) {
        return this.datastore.find(Task.class)
                .filter(Filters.eq("category", category)).stream().toList();
    }

    public List<Task> getOverdueTasks() {
        return this.datastore.find(Task.class)
                .filter(Filters.lt("deadline",  new Date())).stream().toList();
    }

    public List<Task> getAllTasks() {
        return this.datastore.find(Task.class).stream().toList();
    }

    public void updateDeadlineForOverdueTasks(Date date) {
        var overdueTasksQuery =  this.datastore.find(Task.class)
                .filter(Filters.lt("deadline",  date));
        overdueTasksQuery.update(UpdateOperators.set("deadline", date)).execute();
    }

    public void deleteTask(ObjectId taskId) {
        this.datastore.find(Task.class).filter(Filters.eq("_id", taskId))
                .delete();
    }

    public void insertSubtask(ObjectId taskId, Task subTask){
        this.datastore.save(subTask);
        this.datastore.find(Task.class).filter(Filters.eq("_id", taskId))
                .update(UpdateOperators.addToSet("subTasks", subTask)).execute();
    }

    public void updateNameOfSubtaskToTask(ObjectId subTaskId, String name){
        var query =  this.datastore.find(Task.class)
                .filter(Filters.eq("_id", subTaskId));
        query.update(UpdateOperators.set("name", name)).execute();
    }

    public void deleteSubtasks(ObjectId taskId){
        this.datastore.find(Task.class)
                .filter(Filters.eq("_id", taskId))
                .update(UpdateOperators.set("subTasks", new ArrayList<>())).execute();
    }

    public List<Task> findByWordDescription(String word) {
        return this.datastore.find(Task.class)
                .filter(Filters.text(word))
                .stream()
                .filter(task -> task.getDescription() != null && task.getDescription().contains(word)) // Validate "description" field
                .toList();
    }

    public List<Task> findTaskBySubTaskName(String subtaskName) {
        return this.datastore.find(Task.class)
                .filter(Filters.text(subtaskName))
                .stream()
                .filter(task -> task.getSubTasks() != null && task.getSubTasks().stream()
                        .anyMatch(subTask -> subTask.getName() != null && subTask.getName().contains(subtaskName))) // Match sub-task name
                .toList();
    }
}

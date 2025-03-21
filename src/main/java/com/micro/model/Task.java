package com.micro.model;

import dev.morphia.annotations.*;
import dev.morphia.utils.IndexType;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity("tasks")
@Indexes(@Index(fields = @Field(value = "$**", type = IndexType.TEXT)))
public class Task {
    @Id
    private ObjectId id;
    private String name;
    private String description;
    private String category;
    private Date deadline;
    private Date date;
    @Reference
    private List<Task> subTasks;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<Task> subTasks) {
        this.subTasks = subTasks;
    }

    public Task() {
    }

    public Task(String name, String description,
                String category, Date deadline, Date date) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.deadline = deadline;
        this.date = date;
        this.subTasks = new ArrayList<>();
    }
}

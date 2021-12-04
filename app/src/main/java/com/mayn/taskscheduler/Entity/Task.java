package com.mayn.taskscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    public long taskId;

    public String task;

    public Date dueDate;

    public Date dateCreated;

    public Priority priority;

    public boolean isDone;

    //Constructor
    public Task(String task, Date dueDate, Date dateCreated, Priority priority, boolean isDone) {
        this.task = task;
        this.dueDate = dueDate;
        this.dateCreated = dateCreated;
        this.isDone = isDone;
        this.priority = priority;
    }

    //Getter & Setter


    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }


}

package com.mayn.taskscheduler.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "completed_task_table")
public class CompletedTask {

    @PrimaryKey(autoGenerate = true)
    public long cTaskId;

    public String cTask;

    public Date cDueDate;

    public Date cDateCreated;

    public boolean cIsDone;

    //Constructor


    public CompletedTask(String cTask, Date cDueDate, Date cDateCreated, boolean cIsDone) {
        this.cTask = cTask;
        this.cDueDate = cDueDate;
        this.cDateCreated = cDateCreated;
        this.cIsDone = cIsDone;
    }

    //Getter & setter


    public long getcTaskId() {
        return cTaskId;
    }

    public void setcTaskId(long cTaskId) {
        this.cTaskId = cTaskId;
    }

    public String getcTask() {
        return cTask;
    }

    public void setcTask(String cTask) {
        this.cTask = cTask;
    }

    public Date getcDueDate() {
        return cDueDate;
    }

    public void setcDueDate(Date cDueDate) {
        this.cDueDate = cDueDate;
    }

    public Date getcDateCreated() {
        return cDateCreated;
    }

    public void setcDateCreated(Date cDateCreated) {
        this.cDateCreated = cDateCreated;
    }

    public boolean iscIsDone() {
        return cIsDone;
    }

    public void setcIsDone(boolean cIsDone) {
        this.cIsDone = cIsDone;
    }


}

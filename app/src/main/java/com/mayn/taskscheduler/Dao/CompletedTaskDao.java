package com.mayn.taskscheduler.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mayn.taskscheduler.Entity.CompletedTask;
import com.mayn.taskscheduler.Entity.Task;

import java.util.List;


@Dao
public interface CompletedTaskDao {


    @Insert
    void insertCompletedTask(CompletedTask completedTask);

    @Delete
    void deleteCompletedTask(CompletedTask completedTask);


    @Query("SELECT * FROM completed_task_table ORDER BY cTaskId ASC")
    LiveData<List<CompletedTask>> getAllCompletedTask();

    @Query("SELECT * FROM completed_task_table WHERE cTaskId ==:id")
    LiveData<CompletedTask> getC (long id);

}

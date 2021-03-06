package com.mayn.taskscheduler.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mayn.taskscheduler.Entity.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM task_table ORDER BY taskId ASC")
    LiveData<List<Task>> getAllTask();

    @Query("SELECT * FROM task_table WHERE task_table.taskId ==:id")
    LiveData<Task> get(long id);

}

package com.mayn.taskscheduler.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mayn.taskscheduler.DB.TaskRoomDatabase;
import com.mayn.taskscheduler.Dao.CompletedTaskDao;
import com.mayn.taskscheduler.Dao.TaskDao;
import com.mayn.taskscheduler.Entity.CompletedTask;
import com.mayn.taskscheduler.Entity.Task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskSchedulerRepository {

    private final TaskDao taskDao;

    private final CompletedTaskDao completedTaskDao;

    private final LiveData<List<Task>> taskList;

    private final LiveData<List<CompletedTask>> completedTaskList;


    ExecutorService executors = Executors.newSingleThreadExecutor();

    public TaskSchedulerRepository(Application application){

        //Create Task DataBase Object and Calling getInstance method
        TaskRoomDatabase database = TaskRoomDatabase.getInstance(application);
        taskDao = database.taskDao();
        taskList = taskDao.getAllTask();

        completedTaskDao = database.completedTaskDao();
        completedTaskList = completedTaskDao.getAllCompletedTask();

    }

    public LiveData<List<Task>> getAllTask(){

        return taskList;
    }
    public LiveData<List<CompletedTask>> getAllCompletedTask(){

        return completedTaskList;
    }

    public LiveData<Task> get(long id){
        return taskDao.get(id);
    }
    public LiveData<CompletedTask> getC(long id){
        return completedTaskDao.getC(id);
    }

    public void insertTask(Task task){

            executors.execute(new Runnable() {
                @Override
                public void run() {

                    taskDao.insertTask(task);
                }
            });
    }

    public void insertCompletedTask( CompletedTask completedTask){

        executors.execute(new Runnable() {
            @Override
            public void run() {
                completedTaskDao.insertCompletedTask(completedTask);
            }
        });
    }

    public void updateTask(Task task){

        executors.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.updateTask(task);
            }
        });
    }

    public  void deleteTask(Task task){

        executors.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteTask(task);
            }
        });
    }

    public void deleteCompletedTask(CompletedTask completedTask){

        executors.execute(new Runnable() {
            @Override
            public void run() {
                completedTaskDao.deleteCompletedTask(completedTask);
            }
        });
    }



}

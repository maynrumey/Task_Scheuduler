package com.mayn.taskscheduler.ViewModel;

import android.app.Application;
import android.hardware.lights.LightState;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mayn.taskscheduler.Entity.CompletedTask;
import com.mayn.taskscheduler.Entity.Task;
import com.mayn.taskscheduler.Repository.TaskSchedulerRepository;

import java.util.List;

public class TaskSchedulerViewModel  extends AndroidViewModel {

    //Creating Object of repository
    public static TaskSchedulerRepository repository;

    public final LiveData<List<Task>> taskList;

    public final LiveData<List<CompletedTask>> completedTaskList;



    public TaskSchedulerViewModel(@NonNull Application application) {
        super(application);

        repository = new TaskSchedulerRepository(application);

        taskList = repository.getAllTask();
        completedTaskList = repository.getAllCompletedTask();

    }

    public LiveData<List<Task>> getAllTask(){
        return taskList;
    }
    public LiveData<List<CompletedTask>> getAllCompletedTask(){

        return completedTaskList;
    }

    public LiveData<Task> get(long id){

        return repository.get(id);
    }

    public LiveData<CompletedTask> getC(long id){

        return repository.getC(id);
    }



    public static void insertTask(Task task){
        repository.insertTask(task);
    }

    public static void insertCompletedTask(CompletedTask completedTask){
        repository.insertCompletedTask(completedTask);
    }

    public static void updateTask(Task task){
        repository.updateTask(task);
    }

    public static void deleteTask(Task task){
        repository.deleteTask(task);
    }

    public static void deleteCompletedTask(CompletedTask completedTask){
        repository.deleteCompletedTask(completedTask);
    }


}

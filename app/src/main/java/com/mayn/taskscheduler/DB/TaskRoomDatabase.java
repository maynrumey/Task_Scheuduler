package com.mayn.taskscheduler.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mayn.taskscheduler.Dao.CompletedTaskDao;
import com.mayn.taskscheduler.Dao.TaskDao;
import com.mayn.taskscheduler.Entity.CompletedTask;
import com.mayn.taskscheduler.Entity.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, CompletedTask.class}, version = 3, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class TaskRoomDatabase extends RoomDatabase {


    private static TaskRoomDatabase instance;

    public abstract TaskDao taskDao();

    public abstract CompletedTaskDao completedTaskDao();


    public static synchronized TaskRoomDatabase getInstance(Context context){

        if (instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext()
                        , TaskRoomDatabase.class, "task_scheduler_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
        }

        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            TaskDao taskDao = instance.taskDao();
            CompletedTaskDao completedTaskDao = instance.completedTaskDao();


            ExecutorService executorService = Executors.newSingleThreadExecutor();

            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    //Add Task In DataBase

                }
            });

        }
    };

}

package com.mayn.taskscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mayn.taskscheduler.Adapter.CompletedTaskAdapter;
import com.mayn.taskscheduler.Adapter.RecyclerViewAdapter;
import com.mayn.taskscheduler.Entity.CompletedTask;
import com.mayn.taskscheduler.Entity.Task;
import com.mayn.taskscheduler.Utils.OnCompleteTaskClickListener;
import com.mayn.taskscheduler.ViewModel.TaskSchedulerViewModel;

import java.util.List;

public class CompletedTaskActivity extends AppCompatActivity implements OnCompleteTaskClickListener {
    Toolbar toolbar;
    RecyclerView completedTaskRecycler;
    LinearLayoutManager linearLayoutManager;
    private CompletedTaskAdapter completedTaskAdapter;
    private TaskSchedulerViewModel taskSchedulerViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task);
        toolbar = findViewById(R.id.taskToolbar_completed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Completed Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        completedTaskRecycler = findViewById(R.id.recycler_view_completedTask);


        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        completedTaskRecycler.setHasFixedSize(true);
        completedTaskRecycler.setLayoutManager(linearLayoutManager);

        //initializing TaskViewModel
        taskSchedulerViewModel = new ViewModelProvider.AndroidViewModelFactory(
                CompletedTaskActivity.this.getApplication())
                .create(TaskSchedulerViewModel.class);

        taskSchedulerViewModel.getAllCompletedTask().observe(this, new Observer<List<CompletedTask>>() {
            @Override
            public void onChanged(List<CompletedTask> completedTaskList) {
                //Setting recyclerView and Adapter

                completedTaskAdapter = new CompletedTaskAdapter(completedTaskList, CompletedTaskActivity.this::onCompleteTaskRadioButtonClick);
                completedTaskRecycler.setAdapter(completedTaskAdapter);

            }
        });




    }




    //TO Enable TO-Back Action Button

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCompleteTaskRadioButtonClick(CompletedTask completedTask) {

        TaskSchedulerViewModel.deleteCompletedTask(completedTask);
        completedTaskAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT).show();

    }




}
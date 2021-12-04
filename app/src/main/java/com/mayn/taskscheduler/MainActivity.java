package com.mayn.taskscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mayn.taskscheduler.Adapter.RecyclerViewAdapter;
import com.mayn.taskscheduler.Entity.CompletedTask;
import com.mayn.taskscheduler.Entity.Priority;
import com.mayn.taskscheduler.Entity.Task;
import com.mayn.taskscheduler.Utils.OnTaskClickListener;
import com.mayn.taskscheduler.ViewModel.SharedViewModel;
import com.mayn.taskscheduler.ViewModel.TaskSchedulerViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnTaskClickListener {

    private TaskSchedulerViewModel taskSchedulerViewModel;

    BottomSheetFragment bottomSheetFragment;

    RecyclerViewAdapter recyclerViewAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView taskRecyclerView;
    FloatingActionButton fabButton;
    Toolbar toolbar;

    //"ViewModel" for transfer data from Activity to Fragment/Activity
    private SharedViewModel sharedViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.myCustomToolbar);
        setSupportActionBar(toolbar);
        //To hide toolbar Auto App title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fabButton = findViewById(R.id.fab);
        taskRecyclerView = findViewById(R.id.task_recycler_view);

        taskRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        taskRecyclerView.setLayoutManager(linearLayoutManager);

        //Initializing bottom Sheet Fragment
        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottom_sheet); //Getting BottomSheet Layout
        //Using BottomSheet Behaviour to Invoke bottomShett
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);


        //Initializing Task View model
        taskSchedulerViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this.getApplication())
                .create(TaskSchedulerViewModel.class);



        taskSchedulerViewModel.getAllTask().observe(this, (List<Task> taskList) -> {
            //Setting recyclerView and Adapter
            recyclerViewAdapter = new RecyclerViewAdapter(taskList, this);
            taskRecyclerView.setAdapter(recyclerViewAdapter);
        });

        //Initializing SharedViewModel
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);



        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create and calling method to showBottomSheet Dialog
                showBottomSheetDialog();

            }
        });






    }

    //Method for BottomSheet Dialog
    private void showBottomSheetDialog(){

        //getTag is an Underlining Id that given to every fragment by Manager
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

    }
















    //------>Overrides method for Menu<--------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.custom_menu,menu);

        return true;

    }
    //Methods for setting Listener for menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menuAddTask:

                showBottomSheetDialog();

                break;
            case R.id.menuCompletedTask:

                Intent intent = new Intent(MainActivity.this, CompletedTaskActivity.class);
                startActivity(intent);

                break;
            case R.id.aboutMe:

                Intent intent1 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent1);

                break;
            default:

                break;

        }

        return true;
    }

    //Methods for Implements Interface OntaaskClick Listener

    @Override
    public void onTaskClick(Task task) {
        //Log.d("Click", "onTaskCLick" + adapterPosition);
        //Passing the task to SharedViewModel- Class
        sharedViewModel.selectItem(task);
        sharedViewModel.setIsEdit(true);

        //By Click on Task need to show bottomSheet Dialog
        showBottomSheetDialog();


    }




    @SuppressLint({"NotifyDataSetChanged", "ShowToast"})
    @Override
    public void onTaskRadioButtonClick(Task task) {
//        Log.d("Click","OnRadioClick" + task.getTask());
        //OnClick - Task -Completed

        //Getting Completed Task to these variable
        String completedTask = task.getTask();
        Date completedDueDate = task.getDueDate();
        Date dateCreated = task.getDateCreated();
        Date completedDate = Calendar.getInstance().getTime();
        Priority taskPriority = task.getPriority();
        long completedTaskID = task.getTaskId();

        //Insert completedTask to Completed Task table
        CompletedTask completedTaskD = new CompletedTask(completedTask,completedDueDate, completedDate,
                true);
        TaskSchedulerViewModel.insertCompletedTask(completedTaskD);


        TaskSchedulerViewModel.deleteTask(task);
        recyclerViewAdapter.notifyDataSetChanged();
        Snackbar.make(taskRecyclerView, "Task Completed", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: code to undo task
                Task undoTask = new Task(completedTask,completedDueDate,dateCreated,taskPriority,false);
                task.setTaskId(completedTaskID);
                TaskSchedulerViewModel.insertTask(undoTask);

            }
        }).show();

    }

}
package com.mayn.taskscheduler;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.mayn.taskscheduler.Entity.Priority;
import com.mayn.taskscheduler.Entity.Task;
import com.mayn.taskscheduler.Utils.Utils;
import com.mayn.taskscheduler.ViewModel.SharedViewModel;
import com.mayn.taskscheduler.ViewModel.TaskSchedulerViewModel;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private EditText writeTask;
    private ImageButton calenderButton, timeButton, saveButton;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Chip todayChip, tomorrowChip, nextWeekChip;
    private TextView dateView, timeView;
    private TimePicker timePicker;
    private Group timeGroup;
    private Priority priority;

    //Variable for get The Due Date --> From Calender or chip
    private Date dueDate;

    private Date dateCreated;


    //Created Calender Object-To get the calender from Java
    Calendar calendar = Calendar.getInstance();

    //Object of sharedViewModel --->Initialize this view model on "onViewCreated"
    private SharedViewModel sharedViewModel;
    private boolean isEdit;



    public BottomSheetFragment() {
        //Required Empty Constructor

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.bottomsheet_fragment, container, false);

        writeTask = view.findViewById(R.id.editTextTask);
        calenderButton = view.findViewById(R.id.calendar_button);
        timeButton = view.findViewById(R.id.time_button);
        saveButton = view.findViewById(R.id.save_todo_button);
        todayChip = view.findViewById(R.id.today_chip);
        tomorrowChip= view.findViewById(R.id.tomorrow_chip);
        nextWeekChip = view.findViewById(R.id.next_week_chip);
        calendarView = view.findViewById(R.id.calendar_view);
        calendarGroup = view.findViewById(R.id.calendar_group);
        dateView = view.findViewById(R.id.textViewDate);
        timeView = view.findViewById(R.id.textViewTime);
        timeGroup = view.findViewById(R.id.time_group);
        timePicker = view.findViewById(R.id.time_pickerView);



        //Passing Onclick for Chip --As i have Implements onClick on Class - will do the task on "onClick" method- below
        todayChip.setOnClickListener(this);
        tomorrowChip.setOnClickListener(this);
        nextWeekChip.setOnClickListener(this);





        return view;
    }

    //To clear the EditText- set Task to edittext on resume


    @Override
    public void onResume() {
        super.onResume();
        //Get Task from shared view model
        isEdit = sharedViewModel.getIsEdit();
        if (sharedViewModel.getSelectedItem().getValue() != null && isEdit){
            Task task = sharedViewModel.getSelectedItem().getValue();
            writeTask.setText(task.getTask());
        }

    }

    //Initializing view on this method
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initialize "SharedViewModel" -->
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);




        //Initialization Fragment UI

        calenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeGroup.setVisibility(View.GONE);

                //To hide and show calender by clicking button
                calendarGroup.setVisibility(
                        calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);


                Utils.hideSoftKeyboard(view);
            }

        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarGroup.setVisibility(View.GONE);

                //To hide and show calender by clicking button
                timeGroup.setVisibility(
                        timeGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                Utils.hideSoftKeyboard(view);
            }
        });



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                //Log.d("Cal", "onViewCreated: ==> month" + (month + 1) + ", dayOfMonth " + dayOfMonth);
                calendar.clear();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                dueDate = calendar.getTime();
                String setDateToTextView = Utils.formatDate(dueDate);
                dateView.setText(setDateToTextView);

            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
               
                dueDate = calendar.getTime();

                String setTimeToTextView = Utils.formatDate(dueDate);
                timeView.setText(setTimeToTextView);

            }
        });



        saveButton.setOnClickListener(view1 -> {

            String task = writeTask.getText().toString().trim(); //Use trim to to get unnecessary spaces

            //Set Date Created as today
            dateCreated = Calendar.getInstance().getTime();
            Utils.formatDate(dateCreated);

            //Creating default due date- if not selected
            if (dueDate == null){
                dueDate = dateCreated;
            }

            // Checking Priority High or Low
            if (dateCreated.compareTo(dueDate) >= 0 ){
                priority = Priority.HIGH;
            }else {
                priority = Priority.LOW;
            }


            if (!TextUtils.isEmpty(task) && dueDate != null && priority != null) {
                Task myTask = new Task(task, dueDate,
                        dateCreated, priority, false);

                if (isEdit) {

                    Task updatedTask = sharedViewModel.getSelectedItem().getValue();

                    updatedTask.setTask(task);

                    updatedTask.setDueDate(dueDate);

                    updatedTask.setDateCreated(Calendar.getInstance().getTime());

                    updatedTask.setPriority(priority);

                    TaskSchedulerViewModel.updateTask(updatedTask);
                    sharedViewModel.setIsEdit(false);


                }else {
                    TaskSchedulerViewModel.insertTask(myTask);
                }

                writeTask.setText("");
                if (this.isVisible()){
                    this.dismiss();
                }

            }else{

                Toast.makeText(requireActivity(), "Add task to save", Toast.LENGTH_SHORT).show();
            }



        });





    }

    //Methods for implementing ---> OnClickListener
    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){

            case R.id.today_chip:
                //Set date for today
                calendar.add(Calendar.DAY_OF_YEAR,0);
                dueDate = calendar.getTime(); //Setting today to dueDate variable
                break;
            case R.id.tomorrow_chip:
                //set Date for tomorrow
                calendar.add(Calendar.DAY_OF_YEAR,1); //added 1 to today - to get the tomorrow
                dueDate = calendar.getTime();
                break;
            case R.id.next_week_chip:
                //set Date for next Week
                calendar.add(Calendar.DAY_OF_YEAR, 7);//added 7 to today - to get the tomorrow
                dueDate = calendar.getTime();
                break;
        }

    }
}

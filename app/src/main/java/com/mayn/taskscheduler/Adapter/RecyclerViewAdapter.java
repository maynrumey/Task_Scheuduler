package com.mayn.taskscheduler.Adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.mayn.taskscheduler.Entity.Task;
import com.mayn.taskscheduler.Utils.OnTaskClickListener;
import com.mayn.taskscheduler.R;
import com.mayn.taskscheduler.Utils.Utils;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TaskViewHolder> {

    private final List<Task> taskList;

    //Object of Interface TaskClickListener
    private final OnTaskClickListener taskClickListener;


    public RecyclerViewAdapter(List<Task> taskList, OnTaskClickListener onTaskClickListener) {
        this.taskList = taskList;
        this.taskClickListener = onTaskClickListener;
    }



    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view_design,parent,false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        //Binding view
        Task currentTask = taskList.get(position);
        //For- formatting date - created a string container & Util class
        String dateFormatted = Utils.formatDate(currentTask.getDueDate());


        //Using colorState List with 2d array & mono d array - to color the due Task
        ColorStateList colorStateList = new ColorStateList(new
                int[][]{
                 new int[] {-android.R.attr.state_enabled},
                new int[] {android.R.attr.state_enabled}
                },

                new int[]{
                        Color.rgb(82,151,244), //disable color
                        Utils.priorityColor(currentTask)
                });


        holder.taskName.setText(currentTask.getTask());

        holder.dateChip.setText(dateFormatted);//--> Format the date.

        holder.dateChip.setTextColor(Utils.priorityColor(currentTask));
        holder.dateChip.setChipIconTint(colorStateList); //For tint need to use color state list
        holder.radioButton.setButtonTintList(colorStateList);



    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public Task getTask(int position){
        return taskList.get(position);
    }



    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public AppCompatRadioButton radioButton;
        public AppCompatTextView taskName;
        public Chip dateChip;

        //Object of interface OnTaskClickListener
        private OnTaskClickListener onTaskClickListener;


        //ViewHolder Constructor
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.task_radio_button);
            taskName = itemView.findViewById(R.id.textViewTask);
            dateChip = itemView.findViewById(R.id.task_date_chip);

            //Passing interface  "onTaskClickListener" to the class label object "taskClickListener"
            this.onTaskClickListener = taskClickListener;

            //Set ClickListener for ItemView
            itemView.setOnClickListener(this);

            radioButton.setOnClickListener(this);

        }



        //Default method for - Implements system OnClickListener
        @Override
        public void onClick(View view) {

            int id = view.getId(); //Getting itemView Click Id
            Task currentTask = taskList.get(getAdapterPosition()); // Getting Task - task object position
            if (id == R.id.task_view_parent){

                onTaskClickListener.onTaskClick(currentTask);
            }else if (id == R.id.task_radio_button){

                radioButton.setChecked(true);
                onTaskClickListener.onTaskRadioButtonClick(currentTask);

            }

        }


    }
}


package com.mayn.taskscheduler.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.mayn.taskscheduler.Entity.CompletedTask;
import com.mayn.taskscheduler.R;
import com.mayn.taskscheduler.Utils.OnCompleteTaskClickListener;
import com.mayn.taskscheduler.Utils.Utils;

import java.util.List;

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.CompletedTaskViewHolder> {

    private final List<CompletedTask> completedTaskList;

    //Object of Interface OnCompleteTaskClickListener
    private final OnCompleteTaskClickListener completeTaskClickListener;


    public CompletedTaskAdapter(List<CompletedTask> completedTaskList, OnCompleteTaskClickListener onCompleteTaskClickListener) {
        this.completedTaskList = completedTaskList;
        this.completeTaskClickListener = onCompleteTaskClickListener;
    }



    @NonNull
    @Override
    public CompletedTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_task_view_design,parent,false);


        return new CompletedTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedTaskViewHolder holder, int position) {

        CompletedTask comCurrentTask = completedTaskList.get(position);
        String comDateFormatted = Utils.formatDate(comCurrentTask.getcDueDate());

        holder.completedTaskName.setText(comCurrentTask.getcTask());
        holder.completedDateChip.setText(comDateFormatted);
    }

    @Override
    public int getItemCount() {

        return completedTaskList.size();
    }




    public class CompletedTaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public AppCompatRadioButton completedRadioButton;
        public AppCompatTextView completedTaskName;
        public Chip completedDateChip;

        //Object of OnCompleteTaskClickListener;
        private OnCompleteTaskClickListener onCompleteTaskClickListener;


        //ViewHolder Constructor
        public CompletedTaskViewHolder(@NonNull View itemView) {
            super(itemView);

            completedRadioButton = itemView.findViewById(R.id.task_radio_button_completed);
            completedTaskName = itemView.findViewById(R.id.textViewCompletedTask);
            completedDateChip = itemView.findViewById(R.id.task_date_chip_completed);

            //Passing interface  "onTaskClickListener" to the class label object "taskClickListener"
            this.onCompleteTaskClickListener = completeTaskClickListener;

            completedRadioButton.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

            int id = view.getId(); //Getting itemView Click Id
            CompletedTask completedTask = completedTaskList.get(getAdapterPosition()); /// Getting Task - task object position
            if (id == R.id.task_radio_button_completed){
                completedRadioButton.setChecked(true);
                onCompleteTaskClickListener.onCompleteTaskRadioButtonClick(completedTask);
            }

        }
    }



}

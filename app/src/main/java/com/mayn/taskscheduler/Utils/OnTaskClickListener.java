package com.mayn.taskscheduler.Utils;

import com.mayn.taskscheduler.Entity.Task;

public interface OnTaskClickListener {

    //Method for Interface --
    void onTaskClick(Task task);

    void onTaskRadioButtonClick(Task task);

}

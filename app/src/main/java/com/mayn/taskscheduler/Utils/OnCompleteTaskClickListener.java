package com.mayn.taskscheduler.Utils;

import com.mayn.taskscheduler.Entity.CompletedTask;
import com.mayn.taskscheduler.Entity.Task;

public interface OnCompleteTaskClickListener {

    void onCompleteTaskRadioButtonClick(CompletedTask completedTask);
}

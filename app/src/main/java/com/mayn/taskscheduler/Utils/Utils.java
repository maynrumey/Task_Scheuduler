package com.mayn.taskscheduler.Utils;

import android.content.Context;
import android.graphics.Color;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mayn.taskscheduler.Entity.Priority;
import com.mayn.taskscheduler.Entity.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String formatDate(Date date){

        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();

        //Applying Date Pattern
        simpleDateFormat.applyPattern("EEE, MMM d, HH:mm a");

        return simpleDateFormat.format(date);
    }

    //To hide Keyboard - where needed
    public static void hideSoftKeyboard(View view){

        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    //To color chnage on priority - used this method on recycler adapter
    public static int priorityColor(Task task){
        int color;
        if(task.getPriority() == Priority.HIGH){
            color = Color.rgb(252,97,0);
        }else {
            color = Color.rgb(82,151,244);
        }

        return color;
    }



}

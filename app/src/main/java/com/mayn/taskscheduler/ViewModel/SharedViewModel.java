package com.mayn.taskscheduler.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mayn.taskscheduler.Entity.Task;

public class SharedViewModel extends ViewModel {

    // MutableLiveData - by which i can change the data through the process
    private final MutableLiveData<Task> selectedItem = new MutableLiveData<>();

    private boolean isEdit;

    //Method to pass/set task to selectedItem which hold MutableLiveData
    public void selectItem(Task task){

        selectedItem.setValue(task);

    }

    //Method to get Task by LiveData & MutableLiveData
    public LiveData<Task> getSelectedItem(){

        return selectedItem;
    }

    public void setIsEdit(boolean isEdit){
        this.isEdit = isEdit;
    }

    public boolean getIsEdit(){
        return isEdit;
    }
}

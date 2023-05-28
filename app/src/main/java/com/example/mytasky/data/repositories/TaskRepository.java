package com.example.mytasky.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mytasky.data.database.entity.TaskEntity;
import com.example.mytasky.data.datasourse.TasksDataSource;
import com.example.mytasky.data.protocols.TaskProtocol;

import java.util.List;

public class TaskRepository implements TaskProtocol {
    private final Context context;
    private final TasksDataSource dataSource;


     public TaskRepository(Context context){
         this.context = context;
         this.dataSource = new TasksDataSource(context);
     }


    @Override
    public LiveData<List<TaskEntity>> getTaskList() {
        return dataSource.getTasks();
    }

    @Override
    public LiveData<TaskEntity> getTaskItem(int position) {
        return dataSource.getTask(position);
    }
}

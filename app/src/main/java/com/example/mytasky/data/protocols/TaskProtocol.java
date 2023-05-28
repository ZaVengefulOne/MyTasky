package com.example.mytasky.data.protocols;

import androidx.lifecycle.LiveData;

import com.example.mytasky.data.database.entity.TaskEntity;

import java.util.List;

public interface TaskProtocol {

    LiveData<List<TaskEntity>> getTaskList();
    LiveData<TaskEntity> getTaskItem(int position);
}

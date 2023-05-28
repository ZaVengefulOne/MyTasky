package com.example.mytasky.ui.stateholders.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytasky.data.database.entity.TaskEntity;
import com.example.mytasky.data.repositories.TaskRepository;

import java.util.List;

public class MenuViewModel extends AndroidViewModel {
    private final TaskRepository repository = new TaskRepository(getApplication());
    public LiveData<List<TaskEntity>> listLiveData = repository.getTaskList();


    public MenuViewModel(@NonNull Application application) {
        super(application);
    }
}

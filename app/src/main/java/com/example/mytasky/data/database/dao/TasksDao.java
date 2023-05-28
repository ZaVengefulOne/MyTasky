package com.example.mytasky.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mytasky.data.database.entity.TaskEntity;

import java.util.List;

@Dao
public interface TasksDao {
    @Query("SELECT * FROM tasks_table")
    LiveData<List<TaskEntity>> getTasks();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TaskEntity task);

    @Query("SELECT * FROM tasks_table WHERE :id LIKE id")
    LiveData<TaskEntity> getItem(int id);

    @Delete
    void deleteTask(TaskEntity task);

    @Query("SELECT * FROM tasks_table")
    List<TaskEntity> getTasksSync();
}
package com.example.mytasky.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mytasky.data.database.dao.TasksDao;
import com.example.mytasky.data.database.dao.UsersDao;
import com.example.mytasky.data.database.entity.Converter;
import com.example.mytasky.data.database.entity.TaskEntity;

@Database(entities = {TaskEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class TasksDataBase extends RoomDatabase {
    private static volatile TasksDataBase INSTANCE;

    public abstract TasksDao tasksDao();

    public static TasksDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TasksDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TasksDataBase.class, "tasks_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

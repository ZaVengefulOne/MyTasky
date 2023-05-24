package com.example.mytasky.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mytasky.data.database.dao.UsersDao;
import com.example.mytasky.data.database.entity.UsersLogin;

@Database(entities = {UsersLogin.class}, version = 1, exportSchema = false)
public abstract class UsersDataBase extends RoomDatabase {
    private static volatile UsersDataBase INSTANCE;

    public abstract UsersDao usersDao();

    public static UsersDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UsersDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UsersDataBase.class, "users_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

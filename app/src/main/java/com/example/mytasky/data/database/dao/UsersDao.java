package com.example.mytasky.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mytasky.data.database.entity.UsersLogin;

import java.util.List;

@Dao
public interface UsersDao {
    @Query("SELECT * FROM users_table")
    static LiveData<List<UsersLogin>> getUsersList() {
        return null;
    }
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UsersLogin user);
    @Query("SELECT * FROM users_table WHERE :id LIKE id")
    LiveData<UsersLogin> getItem(int id);
}
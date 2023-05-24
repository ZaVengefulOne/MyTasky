package com.example.mytasky.data.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_table")
public class UsersLogin {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public Integer uid;
    private final String login;
    private final String password;

    public UsersLogin(String login, String password){
        this.login = login;
        this.password = password;

    }
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
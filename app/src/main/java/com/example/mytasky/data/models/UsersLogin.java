package com.example.mytasky.data.models;

public class UsersLogin {
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
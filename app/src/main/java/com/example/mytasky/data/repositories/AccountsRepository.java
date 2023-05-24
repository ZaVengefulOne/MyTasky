package com.example.mytasky.data.repositories;
import android.content.Context;

import com.example.mytasky.data.datasourse.AccountsDataSource;
import com.example.mytasky.data.database.entity.UsersLogin;
import com.example.mytasky.data.protocols.AccountsProtocol;


public class AccountsRepository implements AccountsProtocol {
    private final Context context;
    private final AccountsDataSource dataSource;

    public AccountsRepository(Context context){
        this.context = context;
        dataSource = new AccountsDataSource(context);
    }


    @Override
    public boolean userLogin(UsersLogin loginUser) {
        return dataSource.checkLoginUserValid(loginUser);
    }
}
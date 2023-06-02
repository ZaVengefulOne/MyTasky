package com.example.mytasky.ui.stateholders.viewModels;

import android.app.Application;
import com.example.mytasky.data.database.entity.UsersLogin;
import com.example.mytasky.data.repositories.AccountsRepository;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class AuthViewModel extends AndroidViewModel {

    private final AccountsRepository accountsRepository = new AccountsRepository(getApplication());

    public AuthViewModel(@NonNull Application application) {
        super(application);
    }
    public boolean loginAccount(String login, String password){
        UsersLogin usersLogin = new UsersLogin(login, password);
        return accountsRepository.userLogin(usersLogin);
    }
    public boolean adminLogin(String login, String password){
        UsersLogin usersLogin = new UsersLogin(login, password);
        return accountsRepository.adminLogin(usersLogin);
    }
}

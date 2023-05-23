package com.example.mytasky.data.protocols;


import com.example.mytasky.data.models.UsersLogin;

public interface AccountsProtocol {

//    boolean adminLogin(LoginAdministrator loginAdministrator, boolean allowed);

    boolean userLogin(UsersLogin loginUser);

}
package com.example.mytasky.data.datasourse;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


import com.example.mytasky.data.database.UsersDataBase;
import com.example.mytasky.data.database.dao.UsersDao;
import com.example.mytasky.data.database.entity.UsersLogin;
import com.example.mytasky.data.workers.UserDataWorker;

import java.util.ArrayList;
import java.util.List;

public class AccountsDataSource {
    private final Context context;
    private final WorkManager workManager;
    List<UsersLogin> users = new ArrayList<>();

    public LiveData<List<UsersLogin>> getUsersList()
    {

        UsersDataBase db = UsersDataBase.getDatabase(context);
        UsersDao usersDao = db.usersDao();
        db.getQueryExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (UsersLogin usersLogin : users){
                    usersDao.insert(usersLogin);
                }
            }
        });
        return UsersDao.getUsersList();
    }


    public AccountsDataSource(Context context) {
        this.context = context;
        workManager = WorkManager.getInstance(context);
    }

    private Data createInputData(String login){
        Data.Builder Databuilder = new Data.Builder();
        Databuilder.putString(UserDataWorker.KEY_LOG, login);
        return Databuilder.build();
    }



    public boolean checkLoginUserValid(UsersLogin loginUser){
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(UserDataWorker.class)
                .setInputData(createInputData(loginUser.getLogin())).build();
        workManager.enqueue(workRequest);
        return loginUser.getLogin().equals("") &&
                loginUser.getPassword().equals("");
    }

}


//    public boolean checkAdminUserValid(LoginAdministrator loginAdministrator, boolean allowed){
//        if (allowed) {
//            String filename = "Key";
//            String fileContents = loginAdministrator.getPasskey();
//            File file_key = new File("/storage/emulated/0/Android/data", filename);
//            try {
//                FileOutputStream fos = new FileOutputStream(file_key);
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
//                writer.write(fileContents);
//                writer.close();
//                fos.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return !loginAdministrator.getAdmlogin().equals("") &&
//                !loginAdministrator.getPasskey().equals("");
//    }
//}
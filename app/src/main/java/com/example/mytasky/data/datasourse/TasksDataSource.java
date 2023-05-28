package com.example.mytasky.data.datasourse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.mytasky.data.database.TasksDataBase;
import com.example.mytasky.data.database.dao.TasksDao;
import com.example.mytasky.data.database.entity.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class TasksDataSource {
    private static Context context = null;
    static List<TaskEntity> tasks = new ArrayList<>();

    public TasksDataSource(Context context) {
        TasksDataSource.context = context;
    }

    // Метод, который добавляет новую задачу в базу данных
    public void addTask(TaskEntity task) {
        tasks.add(task);
        TasksDataBase db = TasksDataBase.getDatabase(context);
        TasksDao tasksDao = db.tasksDao();
        db.getQueryExecutor().execute(new Runnable() {
            @Override
            public void run() {
                    tasksDao.insert(task);
            }
        });
    }

    public static TasksDataSource getInstance(Context context) {
        TasksDataBase tasksDataBase = TasksDataBase.getDatabase(context);
        TasksDao tasksDao = tasksDataBase.tasksDao();
        return new TasksDataSource(context);
    }

    public static LiveData<List<TaskEntity>> getTasks() {
        TasksDataBase db = TasksDataBase.getDatabase(context);
        TasksDao tasksDao = db.tasksDao();
        LiveData<List<TaskEntity>> listLiveData = tasksDao.getTasks();
        db.getQueryExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (TaskEntity task : tasks) {
                    tasksDao.insert(task);
                }
            }
        });
        return listLiveData;
    }

    public LiveData<TaskEntity> getTask(int position) {
        TasksDataBase db = TasksDataBase.getDatabase(context);
        TasksDao tasksDao = db.tasksDao();
//        MutableLiveData<TaskEntity> liveData = new MutableLiveData<>();
        LiveData<TaskEntity> taskLiveData = tasksDao.getItem(position + 1);
//        if (doctorLiveData != null) {
//            liveData.setValue(Mapper.mapDoctorListItemToDoctor(doctorLiveData.getValue()));
//        }
        return taskLiveData;
    }

    public void deleteTask(int position) {
        new AsyncTask<Void, Void, List<TaskEntity>>() {
            @Override
            protected List<TaskEntity> doInBackground(Void... voids) {
                TasksDataBase db = TasksDataBase.getDatabase(context);
                TasksDao tasksDao = db.tasksDao();
                return tasksDao.getTasksSync();
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(List<TaskEntity> taskEntities) {
                if (position < taskEntities.size()) {
                    TaskEntity taskToDelete = taskEntities.get(position);
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            TasksDataBase db = TasksDataBase.getDatabase(context);
                            TasksDao tasksDao = db.tasksDao();
                            tasksDao.deleteTask(taskToDelete);
                            return null;
                        }
                    }.execute();
                }
            }
        }.execute();
    }
}
package com.example.mytasky.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.mytasky.data.database.entity.TaskEntity;
import com.example.mytasky.data.datasourse.TasksDataSource;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class NotiService extends Service {
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Получаем список дел пользователя и проверяем наличие задач, у которых скоро заканчивается срок выполнения
        List<TaskEntity> tasks = TasksDataSource.getTasks().getValue();
        assert tasks != null;
        for (TaskEntity task : tasks) {
            if (task.isDueSoon()) { // isDueSoon() - ваш метод, который определяет, что задача скоро заканчивается
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    // Устанавливаем уведомление с помощью AlarmManager на определенное время (например, за два часа до срока выполнения)
                    LocalDateTime notificationTime = null; // устанавливаем уведомление за 2 часа до срока выполнения
                    notificationTime = task.getExpirationDate().minusHours(2);
                    long notificationTimeInMillis = notificationTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    Intent notificationIntent = new Intent(this, NotificationReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, notificationTimeInMillis, pendingIntent);
                }
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

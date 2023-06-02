package com.example.mytasky.data.database.entity;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity(tableName = "tasks_table")
public class TaskEntity implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public Integer uid;
    public String taskText;

    public CalendarDay date;

    public LocalDateTime expirationDate;

    public TaskEntity(String taskText, CalendarDay date, LocalDateTime expirationDate) {
        this.taskText = taskText;
        this.date = date;
        this.expirationDate = expirationDate;
    }

    public String getTaskText() {
        return taskText;
    }

    public CalendarDay getDate() {
        return date;
    }
    public LocalDateTime getExpirationDate() { return expirationDate;}

    public void setTaskText(String taskText) {
        this.taskText  = taskText;
    }
    public void setDate (CalendarDay date) {
        this.date  = date;
    }
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isDueSoon() {
        LocalDateTime now = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();

            LocalDateTime dueDate = getExpirationDate();
            long hoursDifference = 0;
            hoursDifference = ChronoUnit.HOURS.between(now, dueDate);
            return hoursDifference <= 2;
        }
        else {
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(taskText);
        parcel.writeParcelable(date, i);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            parcel.writeSerializable(expirationDate);
        }
    }
    public static final Parcelable.Creator<TaskEntity> CREATOR = new Parcelable.Creator<TaskEntity>() {
        public TaskEntity createFromParcel(Parcel in) {
            return new TaskEntity(in);
        }

        public TaskEntity[] newArray(int size) {
            return new TaskEntity[size];
        }
    };

    private TaskEntity(Parcel in) {
        taskText = in.readString();
        date = in.readParcelable(CalendarDay.class.getClassLoader());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            expirationDate = (LocalDateTime) in.readSerializable();
        }
    }
}

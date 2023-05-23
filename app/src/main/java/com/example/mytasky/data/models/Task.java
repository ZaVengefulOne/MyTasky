package com.example.mytasky.data.models;

import com.prolificinteractive.materialcalendarview.CalendarDay;

public class Task {
    private String taskText;
    private CalendarDay date;

    public Task(String taskText, CalendarDay date) {
        this.taskText = taskText;
        this.date = date;
    }

    public String getTaskText() {
        return taskText;
    }

    public CalendarDay getDate() {
        return date;
    }
}

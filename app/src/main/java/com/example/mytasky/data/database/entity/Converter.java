package com.example.mytasky.data.database.entity;

import android.os.Build;

import androidx.room.TypeConverter;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Converter {
    @TypeConverter
    public static CalendarDay fromTimestamp(Long value) {
        return value == null ? null : CalendarDay.from(new Date(value));
    }

    @TypeConverter
    public static Long dateToTimestamp(CalendarDay date) {
        return date == null ? null : date.getCalendar().getTimeInMillis();
    }

    @TypeConverter
    public static LocalDateTime fromTimestampForLocalDateTime(Long value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return value == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
        }
        else {
            return null;
        }
    }


    @TypeConverter
    public static Long dateToTimestampForLocalDateTime(LocalDateTime date) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return date == null ? null : date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        else {
            return null;
        }
    }
}
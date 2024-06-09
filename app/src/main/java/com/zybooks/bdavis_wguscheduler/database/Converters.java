package com.zybooks.bdavis_wguscheduler.database;

import androidx.room.TypeConverter;

import com.zybooks.bdavis_wguscheduler.entities.Course;
import com.zybooks.bdavis_wguscheduler.entities.Term;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


}

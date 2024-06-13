package com.zybooks.bdavis_wguscheduler.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.zybooks.bdavis_wguscheduler.dao.AssessmentDAO;
import com.zybooks.bdavis_wguscheduler.dao.CourseDAO;
import com.zybooks.bdavis_wguscheduler.dao.TermDAO;
import com.zybooks.bdavis_wguscheduler.entities.Assessment;
import com.zybooks.bdavis_wguscheduler.entities.Course;
import com.zybooks.bdavis_wguscheduler.entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version=2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DatabaseBuilder extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();


    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (DatabaseBuilder.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class,"Scheduler.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

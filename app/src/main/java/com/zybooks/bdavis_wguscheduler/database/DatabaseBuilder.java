package com.zybooks.bdavis_wguscheduler.database;

import androidx.room.Database;

import com.zybooks.bdavis_wguscheduler.entities.Assessment;
import com.zybooks.bdavis_wguscheduler.entities.Course;
import com.zybooks.bdavis_wguscheduler.entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version=0, exportSchema = false)
public class DatabaseBuilder {
}

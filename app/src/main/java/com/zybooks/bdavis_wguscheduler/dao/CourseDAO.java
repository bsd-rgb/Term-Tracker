package com.zybooks.bdavis_wguscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zybooks.bdavis_wguscheduler.entities.Course;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("SELECT * FROM courses ORDER BY courseId ASC")
    List<Course> getAllCourses();

    @Query("SELECT * FROM courses WHERE termId =:term ORDER BY courseId ASC")
    List<Course> getAssociatedCourses(int term);

}

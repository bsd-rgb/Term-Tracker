package com.zybooks.bdavis_wguscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zybooks.bdavis_wguscheduler.entities.Assessment;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertAssessment(Assessment assessment);

    @Update
    void updateAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentId ASC")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE courseId=:course ORDER BY assessmentId ASC")
    List<Assessment> getAssociatedAssessments(int course);
}

package com.zybooks.bdavis_wguscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.zybooks.bdavis_wguscheduler.database.Converters;
import com.zybooks.bdavis_wguscheduler.entities.Term;

import java.util.ArrayList;
import java.util.List;

@Dao
@TypeConverters({Converters.class})
public interface TermDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTerm(Term term);

    @Update
    void updateTerm(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("SELECT * FROM terms ORDER BY termId ASC")
    List<Term> getAllTerms();


}

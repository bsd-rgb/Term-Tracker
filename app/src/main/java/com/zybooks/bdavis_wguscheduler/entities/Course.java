package com.zybooks.bdavis_wguscheduler.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

@Entity(tableName = "courses")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String courseName;
    private String status;
    private Date startDate;
    private Date endDate;
    private int termId;
    private String instructorName;
    private String instructorEmail;
    private String instructorPhone;
    private String note;

    public Course(int courseId, String courseName, String status, Date startDate, Date endDate, int termId, String instructorName, String instructorEmail, String instructorPhone, String note) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termId = termId;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
        this.note = note;
    }

    @Ignore
    public Course(int courseId, String courseName, String status, Date startDate, Date endDate, int termId, String instructorName, String instructorEmail, String instructorPhone) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termId = termId;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
        this.note = "";
    }

    public String toString(){
        return courseName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

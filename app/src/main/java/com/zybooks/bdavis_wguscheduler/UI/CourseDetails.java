package com.zybooks.bdavis_wguscheduler.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.bdavis_wguscheduler.R;
import com.zybooks.bdavis_wguscheduler.database.Repository;
import com.zybooks.bdavis_wguscheduler.entities.Assessment;
import com.zybooks.bdavis_wguscheduler.entities.Course;
import com.zybooks.bdavis_wguscheduler.entities.Term;
import com.zybooks.bdavis_wguscheduler.util.TextFormatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CourseDetails extends AppCompatActivity {
    Repository repository;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    EditText editInstructorName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    EditText editNote;
    Spinner statusSpinner;
    Spinner termSpinner;
    int courseId;
    int termId;
    int courseTermId;
    String courseName;
    String courseStatus;
    String instructorName;
    String instructorEmail;
    String instructorPhone;
    String start;
    String end;
    String note;
    boolean isEmpty;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fab = findViewById(R.id.addAssessmentFAB);


        repository = new Repository(getApplication());

        termSpinner = (Spinner)findViewById(R.id.spTerms);
        editName = findViewById(R.id.courseNameEditText);
        editStart = findViewById(R.id.courseStartEditText);
        editEnd = findViewById(R.id.courseEndEditText);
        editInstructorName = findViewById(R.id.courseInstructorNameEditText);
        editInstructorEmail = findViewById(R.id.courseInstructorEmailEditText);
        editInstructorPhone = findViewById(R.id.courseInstructorPhoneEditText);
        editNote = findViewById(R.id.courseNoteEdit);
        statusSpinner = findViewById(R.id.spCourseStatus);

        courseId = getIntent().getIntExtra("id", -1);
        termId = getIntent().getIntExtra("termId", -1);
        Log.d("CourseDetails", "Term ID: " + termId);
        courseTermId = getIntent().getIntExtra("courseTermId", -1);
        Log.d("CourseDetails", "Course Term ID: " + courseTermId);
        courseName = getIntent().getStringExtra("name");
        courseStatus = getIntent().getStringExtra("status");
        start = getIntent().getStringExtra("startDate");
        end = getIntent().getStringExtra("endDate");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        note = getIntent().getStringExtra("note");

        editName.setText(courseName);
        editStart.setText(start);
        editEnd.setText(end);
        editInstructorName.setText(instructorName);
        editInstructorEmail.setText(instructorEmail);
        editInstructorPhone.setText(instructorPhone);
        editNote.setText(note);
        selectSpinnerItemByValue(statusSpinner, courseStatus);

        ArrayList<Term> termArrayList = new ArrayList<Term>();
        for(Term terms: repository.getmAllTerms()){
            termArrayList.add(terms);
        }

        ArrayAdapter<Term> termAdapter = new ArrayAdapter<Term>(this, android.R.layout.simple_spinner_item, termArrayList);
        termSpinner.setAdapter(termAdapter);

        if(editName.getText().toString().isEmpty() && editStart.getText().toString().isEmpty() && editEnd.getText().toString().isEmpty()
            && editInstructorName.getText().toString().isEmpty() && editInstructorEmail.getText().toString().isEmpty() && editInstructorPhone.getText().toString().isEmpty()){
            isEmpty = true;

             fab.setVisibility(View.INVISIBLE);
            TextView associatedAssessmentText = findViewById(R.id.textView6);
            associatedAssessmentText.setVisibility(View.INVISIBLE);
        }
        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editStart.setText(TextFormatter.simpleDateFormat.format(myCalendarStart.getTime()));


            }
        };

        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(CourseDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editEnd.setText(TextFormatter.simpleDateFormat.format(myCalendarEnd.getTime()));

            }
        };

        if (termId != -1) {
            int position = getTermPositionById(termArrayList, termId);
            if (position >= 0) {
                termSpinner.setSelection(position);
            }
        }else if(courseTermId != -1){
            int courseTermIdPosition = getTermPositionById(termArrayList, courseTermId);
            if(courseTermIdPosition > 0){
                termSpinner.setSelection(courseTermIdPosition);
            }

        }else {
            termSpinner.setSelection(0);
        }

        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter((this));
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for(Assessment assessment: repository.getmAllAssessments()){
            if(assessment.getAssessmentId() == courseId){
                filteredAssessments.add(assessment);
            }
        }
        assessmentAdapter.setAssessments(filteredAssessments);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                intent.putExtra("associatedCourseIdFromCourse", courseId);
                startActivity(intent);
            }
        });
    }

    public static void selectSpinnerItemByValue(Spinner spinner, String value){
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        if(adapter != null){
            for(int position = 0; position < adapter.getCount(); position++) {
                String item = adapter.getItem(position);
                if (item != null && item.equals(value) ) {
                    spinner.setSelection(position);
                    return;
                }
            }
        }
    }

    private int getTermPositionById(ArrayList<Term> termList, int termId) {
        for (int i = 0; i < termList.size(); i++) {
            if (termList.get(i).getTermId() == termId) {
                return i;
            }
        }
        return -1;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(isEmpty){
            getMenuInflater().inflate(R.menu.menu_new, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_details, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        String startString = editStart.getText().toString();
        String endString = editEnd.getText().toString();
        Term selectedTerm = (Term)termSpinner.getSelectedItem();
        Date startDate = null;
        Date endDate = null;

        try{
            startDate = TextFormatter.simpleDateFormat.parse(startString);
            endDate = TextFormatter.simpleDateFormat.parse(endString);
        }catch(ParseException e){
            System.out.println(e.getMessage());
        }

        if(menuItem.getItemId() == R.id.saveDetails || menuItem.getItemId() == R.id.saveItem){
            Course course;
            if(courseId == -1){
                if(repository.getmAllCourses().size() == 0){
                    courseId = 1;
                }else{
                    courseId = repository.getmAllCourses().get(repository.getmAllCourses().size() - 1).getCourseId() + 1;
                }
                if(editNote.getText().toString().isEmpty()){
                    course = new Course(courseId, editName.getText().toString(), statusSpinner.getSelectedItem().toString(), startDate, endDate,selectedTerm.getTermId()
                            ,editInstructorName.getText().toString(), editInstructorEmail.getText().toString(), editInstructorPhone.getText().toString());
                }else{
                    course = new Course(courseId, editName.getText().toString(), statusSpinner.getSelectedItem().toString(), startDate, endDate,selectedTerm.getTermId()
                            ,editInstructorName.getText().toString(), editInstructorEmail.getText().toString(), editInstructorPhone.getText().toString(), editNote.getText().toString());
                }
                repository.insert(course);
                this.finish();
            }else{
                if(editNote.getText().toString().isEmpty()) {
                    course = new Course(courseId, editName.getText().toString(), statusSpinner.getSelectedItem().toString(), startDate, endDate, selectedTerm.getTermId()
                            , editInstructorName.getText().toString(), editInstructorEmail.getText().toString(), editInstructorPhone.getText().toString());
                }else{
                    course = new Course(courseId, editName.getText().toString(), statusSpinner.getSelectedItem().toString(), startDate, endDate, selectedTerm.getTermId()
                            , editInstructorName.getText().toString(), editInstructorEmail.getText().toString(), editInstructorPhone.getText().toString(),editNote.getText().toString());
                }
                repository.update(course);
                this.finish();
            }
        }
        if(menuItem.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return true;
    }

}
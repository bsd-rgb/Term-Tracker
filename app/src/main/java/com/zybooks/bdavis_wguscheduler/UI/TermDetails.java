package com.zybooks.bdavis_wguscheduler.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.zybooks.bdavis_wguscheduler.entities.Course;
import com.zybooks.bdavis_wguscheduler.entities.Term;
import com.zybooks.bdavis_wguscheduler.util.TextFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {

    int termId;
    String name;
    String start;
    String end;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    boolean isEmpty;

    final Calendar myCalendarStart = Calendar.getInstance();

    final Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_term_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fab = findViewById(R.id.addCourseFAB);

        editName = findViewById(R.id.termNameEditText);
        editStart = findViewById(R.id.termStartEditText);
        editEnd = findViewById(R.id.termEndEditText);
        termId = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        start = getIntent().getStringExtra("startDate");
        end = getIntent().getStringExtra("endDate");

        editName.setText(name);
        editStart.setText(start);
        editEnd.setText(end);


        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(TermDetails.this, startDate, myCalendarStart
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

                new DatePickerDialog(TermDetails.this, endDate, myCalendarEnd
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

        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter((this));
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for(Course course: repository.getmAllCourses()){
            if(course.getTermId() == termId){
                filteredCourses.add(course);
            }
        }
        courseAdapter.setCourses(filteredCourses);


        if(editName.getText().toString().isEmpty() && editStart.getText().toString().isEmpty() && editEnd.getText().toString().isEmpty()){
            isEmpty = true;
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                intent.putExtra("courseTermId", termId);
                Log.d("TermDetails", "Passing term ID: " + termId);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(isEmpty){
            getMenuInflater().inflate(R.menu.menu_new, menu);
        }else {
            getMenuInflater().inflate(R.menu.menu_details, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        String startString = editStart.getText().toString();
        String endString = editEnd.getText().toString();

        Date startDate = null;
        Date endDate = null;

        try{
            startDate = TextFormatter.simpleDateFormat.parse(startString);
            endDate = TextFormatter.simpleDateFormat.parse(endString);
        }catch(ParseException e){
            System.out.println(e.getMessage());
        }

        if(menuItem.getItemId() == R.id.saveDetails || menuItem.getItemId() == R.id.saveItem){

            Term term;
            if(termId ==  -1){

                if(repository.getmAllTerms().size() == 0){
                    termId = 1;
                }else{
                    termId = repository.getmAllTerms().get(repository.getmAllTerms().size() - 1).getTermId() + 1;
                }
                term = new Term(termId, editName.getText().toString(),startDate, endDate);
                repository.insert(term);
                this.finish();

            }else{
                term = new Term(termId,editName.getText().toString(),startDate, endDate);
                repository.update(term);
                this.finish();

            }
            return true;
        }
        if(menuItem.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }

        return true;
    }

}
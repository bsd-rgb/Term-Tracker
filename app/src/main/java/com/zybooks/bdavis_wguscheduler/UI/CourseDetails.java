package com.zybooks.bdavis_wguscheduler.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.zybooks.bdavis_wguscheduler.R;
import com.zybooks.bdavis_wguscheduler.database.Repository;
import com.zybooks.bdavis_wguscheduler.entities.Term;

import java.util.ArrayList;
import java.util.Calendar;

public class CourseDetails extends AppCompatActivity {

    Repository repository;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    EditText editInstructorName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    Spinner statusSpinner;
    Spinner termSpinner;
    int courseId;
    int termId;
    String courseName;
    String courseStatus;
    String instructorName;
    String instructorEmail;
    String instructorPhone;
    String start;
    String end;
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

        repository = new Repository(getApplication());

      termSpinner = (Spinner)findViewById(R.id.spTerms);
          ArrayList<Term> termArrayList = new ArrayList<Term>();
        for(Term terms: repository.getmAllTerms()){
            termArrayList.add(terms);
        }

        ArrayAdapter<Term> termAdapter = new ArrayAdapter<Term>(this, android.R.layout.simple_spinner_item, termArrayList);
        termSpinner.setAdapter(termAdapter);

        editName = findViewById(R.id.courseNameEditText);
        editStart = findViewById(R.id.courseStartEditText);
        editEnd = findViewById(R.id.courseEndEditText);
        editInstructorName = findViewById(R.id.courseInstructorNameEditText);
        editInstructorEmail = findViewById(R.id.courseInstructorEmailEditText);
        editInstructorPhone = findViewById(R.id.courseInstructorPhoneEditText);
        statusSpinner = findViewById(R.id.spCourseStatus);

        courseId = getIntent().getIntExtra("id", -1);
        courseName = getIntent().getStringExtra("name");
        courseStatus = getIntent().getStringExtra("status");
        start = getIntent().getStringExtra("startDate");
        end = getIntent().getStringExtra("endDate");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        instructorPhone = getIntent().getStringExtra("instructorPhone");

        editName.setText(courseName);
        editStart.setText(start);
        editEnd.setText(end);
        editInstructorName.setText(instructorName);
        editInstructorEmail.setText(instructorEmail);
        editInstructorPhone.setText(instructorPhone);
        selectSpinnerItemByValue(statusSpinner, courseStatus);



        if(editName.getText().toString().isEmpty() && editStart.getText().toString().isEmpty() && editEnd.getText().toString().isEmpty()
            && editInstructorName.getText().toString().isEmpty() && editInstructorEmail.getText().toString().isEmpty() && editInstructorPhone.getText().toString().isEmpty()){
            isEmpty = true;


        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(isEmpty){
            getMenuInflater().inflate(R.menu.menu_new, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_details, menu);
        }
        return true;
    }
}
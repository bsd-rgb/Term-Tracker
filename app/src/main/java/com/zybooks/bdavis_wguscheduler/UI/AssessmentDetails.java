package com.zybooks.bdavis_wguscheduler.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.zybooks.bdavis_wguscheduler.R;
import com.zybooks.bdavis_wguscheduler.database.Repository;
import com.zybooks.bdavis_wguscheduler.util.TextFormatter;

import java.util.Calendar;

public class AssessmentDetails extends AppCompatActivity {

    Repository repository;
    int assessmentId;
    int associatedCourseId;
    String assessmentName;
    String assessmentType;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    Spinner assessmentTypeSpinner;
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
        setContentView(R.layout.activity_assessment_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = new Repository(getApplication());

        assessmentTypeSpinner = findViewById(R.id.assessmentSpinner);
        editName = findViewById(R.id.assessmentNameEditText);
        editStart = findViewById(R.id.assessmentStartEditText);
        editEnd = findViewById(R.id.assessmentEndEditText);

        assessmentId = getIntent().getIntExtra("id", -1);
        assessmentName = getIntent().getStringExtra("name");
        assessmentType = getIntent().getStringExtra("type");
        associatedCourseId = getIntent().getIntExtra("associatedCourseId", -1);
        start = getIntent().getStringExtra("startDate");
        end = getIntent().getStringExtra("endDate");

        editName.setText(assessmentName);
        editStart.setText(start);
        editEnd.setText(end);
        selectSpinnerItemByValue(assessmentTypeSpinner, assessmentType);

        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(AssessmentDetails.this, startDate, myCalendarStart
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

                new DatePickerDialog(AssessmentDetails.this, endDate, myCalendarEnd
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
        //check to see if fields are empty to set the menu type
        //work add the menu
        //test saving/updating
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
}
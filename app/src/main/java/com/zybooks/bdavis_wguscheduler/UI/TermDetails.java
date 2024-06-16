package com.zybooks.bdavis_wguscheduler.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.zybooks.bdavis_wguscheduler.R;
import com.zybooks.bdavis_wguscheduler.database.Repository;
import com.zybooks.bdavis_wguscheduler.entities.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {

    int termId;
    String termName;
    String start;
    String end;

    EditText editName;
    EditText editStart;
    EditText editEnd;
    ImageButton saveButton;

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
        saveButton = findViewById(R.id.termDetailsSaveButton);
        editName = findViewById(R.id.termNameEditText);
        editStart = findViewById(R.id.termStartEditText);
        editEnd = findViewById(R.id.termEndEditText);
        termId = getIntent().getIntExtra("id", -1);
        termName = getIntent().getStringExtra("name");
        start = getIntent().getStringExtra("startDate");
        end = getIntent().getStringExtra("endDate");

        editName.setText(termName);
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
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editStart.setText(sdf.format(myCalendarStart.getTime()));

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
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editEnd.setText(sdf.format(myCalendarEnd.getTime()));

            }
        };

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String start = editStart.getText().toString();
                String end = editEnd.getText().toString();
                String myFormat = "MM/dd/yy";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(myFormat);
                LocalDate startDate = null;
                LocalDate endDate = null;

                try{
                    startDate = LocalDate.parse(start, formatter);
                    endDate = LocalDate.parse(end, formatter);

                }catch(DateTimeParseException e){

                    System.out.println(e.getMessage());
                }

                Term term;


            }
        });
    }

}
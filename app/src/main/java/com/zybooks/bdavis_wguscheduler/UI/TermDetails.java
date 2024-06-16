package com.zybooks.bdavis_wguscheduler.UI;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.zybooks.bdavis_wguscheduler.R;
import com.zybooks.bdavis_wguscheduler.database.Repository;

import java.time.LocalDate;

public class TermDetails extends AppCompatActivity {

    int termId;
    String termName;
    String start;
    String end;

    EditText editName;
    EditText editStart;
    EditText editEnd;
    CalendarView calendarViewEnd;
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


    }
}
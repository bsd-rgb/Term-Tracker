package com.zybooks.bdavis_wguscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.zybooks.bdavis_wguscheduler.R;
import com.zybooks.bdavis_wguscheduler.database.Repository;
import com.zybooks.bdavis_wguscheduler.entities.Assessment;
import com.zybooks.bdavis_wguscheduler.entities.Course;
import com.zybooks.bdavis_wguscheduler.entities.Term;

import java.time.LocalDate;

public class HomeScreen extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_screen);
        Button termButton = findViewById(R.id.termsButton);
        Button courseButton = findViewById(R.id.coursesButton);
        Button assessmentButton = findViewById(R.id.assessments_button);

        termButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreen.this, TermList.class);
                startActivity(intent);

            }
        });

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreen.this, CourseList.class);
                startActivity(intent);

            }
        });

        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, AssessmentList.class);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_homescreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.sample) {
            repository = new Repository(getApplication());
            //Toast.makeText(HomeScreen.this, "Sample data to be added", Toast.LENGTH_LONG).show();
            Term term = new Term(0, "Term 1", LocalDate.of(2024, 01, 02), LocalDate.of(2024, 06, 30));
            repository.insert(term);
            term = new Term(0, "Term 2", LocalDate.of(2024, 06, 01), LocalDate.of(2024, 12, 31));
            repository.insert(term);
            Course course = new Course(0, "Mobile Application Development","In progress",LocalDate.of(2024, 01, 05),
                    LocalDate.of(2024, 03, 30), 1,"Instructor", "instructor@wgu.edu", "5555555555");
            repository.insert(course);
            Assessment assessment = new Assessment(0, "Mobile app project","Performance",LocalDate.of(2024, 04, 01) ,1);
            repository.insert(assessment);
            return true;

        }

            return true;
        }

}
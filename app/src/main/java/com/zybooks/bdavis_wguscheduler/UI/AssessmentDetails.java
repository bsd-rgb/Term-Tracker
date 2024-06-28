package com.zybooks.bdavis_wguscheduler.UI;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.zybooks.bdavis_wguscheduler.util.TextFormatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AssessmentDetails extends AppCompatActivity {

    Repository repository;
    int assessmentId;
    int associatedCourseId;
    int assessmentCourseId;
    String assessmentName;
    String assessmentType;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    Spinner assessmentTypeSpinner;
    Spinner courseSpinner;
    String start;
    String end;
    boolean isEmpty;
    Assessment currentAssessment;
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

        courseSpinner = (Spinner) findViewById(R.id.associatedCourseSpinner);
        assessmentTypeSpinner = findViewById(R.id.assessmentSpinner);
        editName = findViewById(R.id.assessmentNameEditText);
        editStart = findViewById(R.id.assessmentStartEditText);
        editEnd = findViewById(R.id.assessmentEndEditText);

        assessmentId = getIntent().getIntExtra("id", -1);
        assessmentName = getIntent().getStringExtra("name");
        assessmentType = getIntent().getStringExtra("type");
        associatedCourseId = getIntent().getIntExtra("associatedCourseId", -1);
        Log.d("AssessmentDetails", "Associated Course ID: " + associatedCourseId);
        assessmentCourseId = getIntent().getIntExtra("associatedCourseIdFromCourse", -1);
        Log.d("AssessmentDetails", "Assessment Course ID: " + assessmentCourseId);
        start = getIntent().getStringExtra("startDate");
        end = getIntent().getStringExtra("endDate");

        editName.setText(assessmentName);
        editStart.setText(start);
        editEnd.setText(end);
        selectSpinnerItemByValue(assessmentTypeSpinner, assessmentType);

        ArrayList<Course> courseArrayList = new ArrayList<Course>();
        for(Course course: repository.getmAllCourses()){
            courseArrayList.add(course);
            Log.d("AssessmentDetails", "Course added to spinner: " + course.getCourseId());
        }

        ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_spinner_item, courseArrayList);
        courseSpinner.setAdapter(courseArrayAdapter);

        Log.d("AssessmentDetails", "Associated Course ID: " + assessmentId);
        Log.d("AssessmentDetails", "Assessment Course ID: " + assessmentCourseId);

        if(associatedCourseId != -1){
            int position = getCoursePositionById(courseArrayList, associatedCourseId);
            Log.d("AssessmentDetails", "Position for assessmentId " + associatedCourseId + ": " + position);
            if(position >= 0){
                courseSpinner.setSelection(position);
            }
        }else if(assessmentCourseId != -1){
            int assessmentCourseIdPosition = getCoursePositionById(courseArrayList, assessmentCourseId);
            Log.d("AssessmentDetails", "Position for assessmentCourseId " + assessmentCourseId + ": " + assessmentCourseIdPosition);
            if(assessmentCourseIdPosition > 0){
                courseSpinner.setSelection(assessmentCourseIdPosition);
            }
        }else{
            courseSpinner.setSelection(0);
        }

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

        if(editName.getText().toString().isEmpty() && editStart.getText().toString().isEmpty() && editEnd.getText().toString().isEmpty()){
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

    private int getCoursePositionById(ArrayList<Course> courseArrayList, int associatedCourseId) {
        for (int i = 0; i < courseArrayList.size(); i++) {
            if (courseArrayList.get(i).getCourseId() == associatedCourseId) {
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
            MenuItem shareItem = menu.findItem(R.id.shareNote);
            shareItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        Course selectedCourse = (Course) courseSpinner.getSelectedItem();
        Log.d("AssessmentDetails", "Selected course name " + selectedCourse.getCourseName());
        String startString = editStart.getText().toString();
        String endString = editEnd.getText().toString();
        Date startDate = null;
        Date endDate = null;
        Date currentDate = new Date();

        try{
            startDate = TextFormatter.simpleDateFormat.parse(startString);
            endDate = TextFormatter.simpleDateFormat.parse(endString);
            currentDate = TextFormatter.simpleDateFormat.parse(TextFormatter.simpleDateFormat.format(currentDate));
        }catch(ParseException e){
            System.out.println(e.getMessage());
        }

        if(item.getItemId() == R.id.saveDetails || item.getItemId() == R.id.saveItem){
            Assessment assessment;
            if(assessmentId == -1){
                if(repository.getmAllAssessments().size() == 0){
                    assessmentId = 1;
                }else{
                    assessmentId = repository.getmAllAssessments().get(repository.getmAllAssessments().size() - 1).getAssessmentId() + 1;
                }
                assessment = new Assessment(assessmentId, editName.getText().toString(), assessmentTypeSpinner.getSelectedItem().toString(), endDate, startDate, selectedCourse.getCourseId());
                Log.d("AssessmentDetails", "Saved course Name: " + selectedCourse.getCourseName());
                repository.insert(assessment);
                this.finish();
            }else{
                assessment = new Assessment(assessmentId, editName.getText().toString(), assessmentTypeSpinner.getSelectedItem().toString(), endDate, startDate, selectedCourse.getCourseId());
                Log.d("AssessmentDetails", "Updated course Name: " + selectedCourse.getCourseName());
                repository.update(assessment);

                this.finish();
            }
        }

        if(item.getItemId() == R.id.deleteDetails){

            for(Assessment assessment: repository.getmAllAssessments()){
                if(assessment.getAssessmentId() == assessmentId){
                    currentAssessment = assessment;
                }
            }

            AlertDialog.Builder deleteDialog = createDeleteConfirmationDialog();
            deleteDialog.show();
        }

        if(item.getItemId() == R.id.notify){

            Long startTrigger = startDate.getTime();
            Long endTrigger = endDate.getTime();

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            if (startDate.equals(currentDate)) {
                Intent startIntent = new Intent(AssessmentDetails.this, MyReceiver.class);
                startIntent.putExtra("message", assessmentName + " starts today.");
                PendingIntent startSender = PendingIntent.getBroadcast(AssessmentDetails.this, ++HomeScreen.numAlert, startIntent, PendingIntent.FLAG_IMMUTABLE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
            }

            if (endDate.equals(currentDate)) {
                Intent endIntent = new Intent(AssessmentDetails.this, MyReceiver.class);
                endIntent.putExtra("message", assessmentName + " ends today.");
                PendingIntent endSender = PendingIntent.getBroadcast(AssessmentDetails.this, ++HomeScreen.numAlert, endIntent, PendingIntent.FLAG_IMMUTABLE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
            }

            Toast.makeText(AssessmentDetails.this, "The date notification is set.", Toast.LENGTH_LONG).show();

        }
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }


        return true;
    }

    private AlertDialog.Builder createDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this assessment?");
        builder.setTitle("Confirm Assessment Deletion");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                repository.delete(currentAssessment);
                AssessmentDetails.this.finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder;
    }
}


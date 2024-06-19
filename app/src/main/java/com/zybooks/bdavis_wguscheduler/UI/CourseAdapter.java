package com.zybooks.bdavis_wguscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.bdavis_wguscheduler.R;
import com.zybooks.bdavis_wguscheduler.entities.Course;
import com.zybooks.bdavis_wguscheduler.util.TextFormatter;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> mCourses;
    private final Context context;

    private final LayoutInflater mInflater;

    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseItemViewer;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemViewer = itemView.findViewById(R.id.courseListItem);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("id", current.getCourseId());
                    intent.putExtra("name", current.getCourseName());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("termId", current.getTermId());
                    intent.putExtra("startDate", TextFormatter.simpleDateFormat.format(current.getStartDate()));
                    intent.putExtra("endDate", TextFormatter.simpleDateFormat.format(current.getEndDate()));
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("instructorPhone", current.getInstructorPhone());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses != null){
            Course current = mCourses.get(position);
            String name = current.getCourseName();
            holder.courseItemViewer.setText(name);
        }else{
            holder.courseItemViewer.setText("No course(s).");
        }

    }

    @Override
    public int getItemCount() {
        if(mCourses != null){
            return mCourses.size();
        }else {
            return 0;
        }
    }

    public void setCourses(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }


}

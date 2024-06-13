package com.zybooks.bdavis_wguscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.bdavis_wguscheduler.R;
import com.zybooks.bdavis_wguscheduler.entities.Assessment;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context =context;
    }

    public class AssessmentViewHolder extends RecyclerView.ViewHolder {

        private final TextView assessmentItemViewer;
        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assessmentItemViewer = itemView.findViewById(R.id.assessmentListItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("id", current.getAssessmentId());
                    intent.putExtra("name", current.getAssessmentName());
                    intent.putExtra("type", current.getAssessmentType());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("courseId", current.getCourseId());
                    context.startActivity(intent);

                }
            });
        }
    }
    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments != null){
            Assessment current = mAssessments.get(position);
            String name = current.getAssessmentName();
            holder.assessmentItemViewer.setText(name);
        }else {
            holder.assessmentItemViewer.setText("No assessment(s).");
        }

    }

    @Override
    public int getItemCount() {
        if(mAssessments != null){
            return mAssessments.size();
        }else {
            return 0;
        }
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }


}

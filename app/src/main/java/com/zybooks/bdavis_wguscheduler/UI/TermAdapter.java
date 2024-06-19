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
import com.zybooks.bdavis_wguscheduler.entities.Term;
import com.zybooks.bdavis_wguscheduler.util.TextFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder>{

    //list of terms
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    public class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView termItemViewer;
        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            termItemViewer = itemView.findViewById(R.id.termListItemTextview);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);

                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("id", current.getTermId());
                    intent.putExtra("name", current.getTermName());
                    intent.putExtra("startDate", TextFormatter.simpleDateFormat.format(current.getStartDate()));
                    intent.putExtra("endDate", TextFormatter.simpleDateFormat.format(current.getEndDate()));
                    context.startActivity(intent);

                }
            });

        }
    }
    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {

        if(mTerms != null){
            Term current = mTerms.get(position);
            String name = current.getTermName();
            holder.termItemViewer.setText(name);
        }else{
            holder.termItemViewer.setText("No term name.");
        }

    }

    @Override
    public int getItemCount() {
        if(mTerms !=null){
            return mTerms.size();

        }else {
            return 0;
        }
    }

    public void setTerms(List<Term> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }


}

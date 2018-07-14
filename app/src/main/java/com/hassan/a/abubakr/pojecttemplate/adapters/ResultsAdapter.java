package com.hassan.a.abubakr.pojecttemplate.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.hassan.a.abubakr.pojecttemplate.R;
import com.hassan.a.abubakr.pojecttemplate.models.Result;

import java.util.List;

/**
 * results are kept as simple/elegant/useful as it could.
 */
public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultHolder> {

    Context _context;
    List<Result> results;
    RecyclerView recyclerView;
    int mExpandedPosition=-1;

    public ResultsAdapter(Context context,List<Result> results,RecyclerView recyclerView) {
        _context=context;
        this.results=results;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(_context).inflate(R.layout.results_item,parent,false);

        return new ResultHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int position;
        boolean isExpanded;
        View v;
        TextView studentName;
        TextView index;
        TextView gpa;
        TextView status;
        TableLayout resultsTable;

        public ResultHolder(View itemView) {
            super(itemView);
            studentName = (TextView) itemView.findViewById(R.id.student_name);
            index = (TextView) itemView.findViewById(R.id.index);
            gpa = (TextView) itemView.findViewById(R.id.gpa);
            status = (TextView) itemView.findViewById(R.id.status);
            resultsTable = (TableLayout) itemView.findViewById(R.id.result_table);
            v = itemView;
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            this.position=position;
            isExpanded = position==mExpandedPosition;
            resultsTable.setVisibility(isExpanded?View.VISIBLE:View.GONE);
            v.setActivated(isExpanded);

            Result result = results.get(position);
            studentName.setText(result.getStudent().getName());
            index.setText(String.valueOf(result.getStudent().getUserId()));
            gpa.setText(String.valueOf(result.getGPA()));
            if (result.getGPA()>4){
                status.setText("Pass");
                status.setTextColor(_context.getResources().getColor(R.color.success));
            }else{
                status.setText("Fail");
                status.setTextColor(_context.getResources().getColor(R.color.red));
            }
            resultsTable.removeAllViews();

            for (Result.ResultEntry entry:result.getResult()){
                View v = LayoutInflater.from(_context).inflate(R.layout.result_row,resultsTable,false);

                TextView course = v.findViewById(R.id.course);
                TextView code = v.findViewById(R.id.code);
                TextView grade = v.findViewById(R.id.grade);

                course.setText(entry.getCourseName());
                code.setText(entry.getCourseId());
                grade.setText(entry.getGrade());
                resultsTable.addView(v);

            }

        }

        @Override
        public void onClick(View view) {
            mExpandedPosition = isExpanded ? -1:position;
            TransitionManager.beginDelayedTransition(recyclerView);
            notifyItemChanged(position);
        }
    }
}

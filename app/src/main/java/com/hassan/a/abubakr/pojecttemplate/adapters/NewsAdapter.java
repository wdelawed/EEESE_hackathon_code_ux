package com.hassan.a.abubakr.pojecttemplate.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hassan.a.abubakr.pojecttemplate.R;
import com.hassan.a.abubakr.pojecttemplate.models.Announcement;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * we followed the way that the easier it is for the user to get to his goals the better app is
 * so the user is about one click away from expanding the news (announcements)
 * it should be noted that the user see's his most relevant posts first
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ResultHolder> {

    Context _context;
    List<Announcement> announcements;
    RecyclerView recyclerView;
    int mExpandedPosition=-1;

    public NewsAdapter(Context context, List<Announcement> results, RecyclerView recyclerView) {
        _context=context;
        this.announcements =results;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(_context).inflate(R.layout.news_item,parent,false);

        return new ResultHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public class ResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int position;
        boolean isExpanded;
        View v;
        TextView title;
        TextView body;
        ImageView image;
        TextView creationDate;

        public ResultHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            body = (TextView) itemView.findViewById(R.id.body);
            image = (ImageView) itemView.findViewById(R.id.image);
            creationDate = (TextView) itemView.findViewById(R.id.creation_date);
            v = itemView;
            itemView.setOnClickListener(this);
        }

        // a simple trick is enhance the UI with smooth animation, android handled this for us with
        // Transition animations
        public void bind(int position) {
            this.position=position;
            Announcement announcement = announcements.get(position);
            title.setText(announcement.getTitle());
            body.setText(announcement.getBody());
            if (announcement.getDate()!=null){
                creationDate.setText(announcement.getDate());
            }

            isExpanded = position==mExpandedPosition;
            body.setVisibility(isExpanded?View.VISIBLE:View.GONE);
            v.setActivated(isExpanded);

        }

        @Override
        public void onClick(View view) {
            mExpandedPosition = isExpanded ? -1:position;
            TransitionManager.beginDelayedTransition(recyclerView);
            // calling notify item changed to not waste processing power
            notifyItemChanged(position);
        }
    }
}

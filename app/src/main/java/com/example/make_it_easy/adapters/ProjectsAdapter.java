package com.example.make_it_easy.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.make_it_easy.R;
import com.example.make_it_easy.models.Project;

import java.util.Date;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.MyViewHolder> {
    List<Project> projectList;
    private Context context;
    private static ClickListener clickListener;

    public ProjectsAdapter(Context context, List<Project> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView projectName;
        private final TextView daysTV;
        private final ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            projectName = itemView.findViewById(R.id.project_name_tv);
            progressBar = itemView.findViewById(R.id.progress_Bar);
            daysTV = itemView.findViewById(R.id.days_tv);
        }


        @Override
        public void onClick(View v) {
            try {
                clickListener.onItemClick(getAdapterPosition(), v);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        void setName(String value) {
            projectName.setText(value);
        }

        void setDaysTV(Date value) {

          daysTV.setText(String.valueOf(((new Date(System.currentTimeMillis())).getDate()-value.getDate())));
        }

        void setProgressBar(int value) {
            progressBar.setProgress(value);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View listViewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_card, null);
        return new MyViewHolder(listViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Project project = projectList.get(position);
        holder.setName(project.name);
        holder.setDaysTV(project.deadline);
        holder.setProgressBar(project.progress);
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v) throws Exception;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ProjectsAdapter.clickListener = clickListener;
    }
}

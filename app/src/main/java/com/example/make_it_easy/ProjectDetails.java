package com.example.make_it_easy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.make_it_easy.models.Project;
import com.example.make_it_easy.pdo.ProjectsPDO;
import com.owl93.dpb.CircularProgressView;

import java.util.concurrent.atomic.AtomicReference;

public class ProjectDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        Project project=project(getIntent().getStringExtra("id"));
        TextView projectName=findViewById(R.id.project_name_tv);
        CircularProgressView progressBar=findViewById(R.id.progress_bar_circle);
        projectName.setText(project.name);
        progressBar.setProgress(project.progress);
        progressBar.setText(project.progress+"%");
    }

    Project project(String id){
        AtomicReference<Project> project= new AtomicReference<>(new Project());
        ProjectsPDO.projectList.forEach(p -> {
            if(p.id.equals(id))
                project.set(p);

        });
        return project.get();
    }
}
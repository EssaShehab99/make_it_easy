package com.example.make_it_easy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.make_it_easy.models.Project;
import com.example.make_it_easy.pdo.ProjectsPDO;
import com.example.make_it_easy.pdo.UserPDO;
import com.google.firebase.firestore.FirebaseFirestore;
import com.owl93.dpb.CircularProgressView;

import java.util.concurrent.atomic.AtomicReference;

public class ProjectDetails extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView stepOne;
    TextView stepTwo;
    TextView stepThree;
    TextView stepFour;
    CircularProgressView progressBar;
    Project project;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
         project = project(getIntent().getStringExtra("id"));
        TextView projectName = findViewById(R.id.project_name_tv);
         progressBar = findViewById(R.id.progress_bar_circle);
        projectName.setText(project.name);
updateProgressBar();
        stepOne = findViewById(R.id.step_1);
        stepTwo = findViewById(R.id.step_2);
        stepThree = findViewById(R.id.step_3);
        stepFour = findViewById(R.id.step_4);
        stepOne.setText(project.steps.get(0).name);
        if (project.steps.size() > 1) {
            stepTwo.setText(project.steps.get(1).name);
            if (project.steps.get(1).status) {
                stepTwo.setPaintFlags(stepTwo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                stepTwo.setPaintFlags(0);
            }
        }

        if (project.steps.size() > 2) {
            stepThree.setText(project.steps.get(2).name);
            stepThree.setPaintFlags(stepThree.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (project.steps.get(2).status) {
                stepThree.setPaintFlags(stepThree.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                stepThree.setPaintFlags(0);
            }
        }


        if (project.steps.size() > 3) {
            stepFour.setText(project.steps.get(3).name);
            stepFour.setPaintFlags(stepFour.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if (project.steps.get(3).status) {
                stepFour.setPaintFlags(stepFour.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                stepFour.setPaintFlags(0);
            }
        }

        findViewById(R.id.back_btn).setOnClickListener(v -> finish());
        stepOne.setOnClickListener(v -> {
            project.steps.get(0).status = !project.steps.get(0).status;
            if (project.steps.get(0).status) {
                stepOne.setPaintFlags(stepOne.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                project.progress += 25;
            } else {
                stepOne.setPaintFlags(0);
                project.progress -= 25;
            }
            updateProgressBar();

            db.collection("users").document(UserPDO.user.email).collection("projects").document(project.id).update(project.toMap());
        });
        stepTwo.setOnClickListener(v -> {
            project.steps.get(1).status = !project.steps.get(1).status;
            if (project.steps.get(1).status) {
                stepTwo.setPaintFlags(stepTwo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                project.progress += 25;

            } else {
                stepTwo.setPaintFlags(0);
                project.progress -= 25;

            }
            updateProgressBar();

            db.collection("users").document(UserPDO.user.email).collection("projects").document(project.id).update(project.toMap());
        });
        stepThree.setOnClickListener(v -> {
            project.steps.get(2).status = !project.steps.get(2).status;
            if (project.steps.get(2).status) {
                stepThree.setPaintFlags(stepThree.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                project.progress += 25;

            } else {
                stepThree.setPaintFlags(0);
                project.progress -= 25;

            }
            updateProgressBar();

            db.collection("users").document(UserPDO.user.email).collection("projects").document(project.id).update(project.toMap());
        });
        stepFour.setOnClickListener(v -> {
            project.steps.get(3).status = !project.steps.get(3).status;

            if (project.steps.get(3).status) {
                stepFour.setPaintFlags(stepFour.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                project.progress += 25;
            } else {
                stepFour.setPaintFlags(0);
                project.progress -= 25;
            }
            updateProgressBar();

            db.collection("users").document(UserPDO.user.email).collection("projects").document(project.id).update(project.toMap());
        });
        if (project.steps.get(0).status)
            stepOne.setPaintFlags(stepOne.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    Project project(String id) {
        AtomicReference<Project> project = new AtomicReference<>(new Project());
        ProjectsPDO.projectList.forEach(p -> {
            if (p.id.equals(id))
                project.set(p);

        });
        return project.get();
    }
    void updateProgressBar(){
        progressBar.setProgress(project.progress);
        progressBar.setText(project.progress + "%");
    }
}
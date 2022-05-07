package com.example.make_it_easy;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.make_it_easy.models.Project;
import com.example.make_it_easy.pdo.ProjectsPDO;
import com.example.make_it_easy.pdo.UserPDO;
import com.example.make_it_easy.shared.Shared;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.owl93.dpb.CircularProgressView;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    BarChart chart;
    CircularProgressView progressBar;
    TextView howManyDone;
    Button task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = view.findViewById(R.id.progress_bar_circle);
        task = view.findViewById(R.id.task_btn);
        howManyDone = view.findViewById(R.id.how_many_done_tv);
        chart = view.findViewById(R.id.lc_chart1);
        fetchData(view);
        return view;
    }

    int projectTaskSize = 0;
    int projectTaskDone = 0;

    void checkToday() {
        @SuppressLint("SimpleDateFormat") Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        projectTaskSize = 0;
        projectTaskDone = 0;
        ProjectsPDO.projectList.forEach(project -> {
            if (formatter.format(new Date(System.currentTimeMillis())).equals(formatter.format(project.deadline))) {
                project.steps.forEach(projectTask -> {
                    projectTaskSize++;
                    if (projectTask.status) {
                        projectTaskDone++;
                    }
                });
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void setTodayChart() {
        float average = ((float) (projectTaskDone) / (float) (projectTaskSize)) * 100;
        progressBar.setProgress(average);
        progressBar.setText(Math.round(average) + "%");
        howManyDone.setText("You marked " + projectTaskDone + "/" + projectTaskSize + " tasks are done!");
        task.setText(projectTaskDone + "/" + projectTaskSize);
    }

    void fetchData(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(UserPDO.user.email).collection("projects").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ProjectsPDO.projectList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    ProjectsPDO.projectList.add(Project.fromMap(document.getData(), document.getId()));
                                    checkToday();
                                    setTodayChart();
                                    checkWeek();
                                } catch (ParseException e) {
                                    Shared.showSnackBar(view, "Error Occur");
                                }

                            }
                        } else {
                            Shared.showSnackBar(view, "Error Occur");
                        }
                    }
                });
    }

    int valueDay=0;

    void checkWeek(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        Date today=new Date(System.currentTimeMillis());
       for(int i=0;i<7;i++){
           valueDay=0;
           getProjectWithDate(today.getDate()-i).forEach(project -> {
               project.steps.forEach(projectTask -> {
                   if(projectTask.status)
                       valueDay++;
               });
           });
           BarEntry barEntry = new BarEntry(i, valueDay*10);
           barEntries.add(barEntry);

       }
        BarDataSet barDataSet = new BarDataSet(barEntries, "Week");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setDrawIcons(false);
        chart.setData(new BarData(barDataSet));
        chart.animateY(5000);
        chart.getDescription().setText("Days");
        chart.getDescription().setTextColor(Color.BLUE);
    }
 List<Project> getProjectWithDate(int date){
     List<Project> projects=new ArrayList<>();
     for (Project project:ProjectsPDO.projectList)
     {
         if(date==project.date.getDate())
             projects.add(project);
     }
     return projects;
    }
}
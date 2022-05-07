package com.example.make_it_easy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.make_it_easy.adapters.ProjectsAdapter;
import com.example.make_it_easy.models.Project;
import com.example.make_it_easy.models.User;
import com.example.make_it_easy.pdo.ProjectsPDO;
import com.example.make_it_easy.pdo.UserPDO;
import com.example.make_it_easy.shared.Shared;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ProjectFragment extends Fragment {
    RecyclerView recyclerView;
    ProjectsAdapter projectsAdapter;
    TextView allFilter;
    TextView weekFilter;
    TextView monthFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        allFilter = view.findViewById(R.id.all_filter);
        weekFilter = view.findViewById(R.id.week_filter);
        monthFilter = view.findViewById(R.id.month_filter);
        allFilter.setOnClickListener(v -> {
            selectFilter(view.getContext(), 1);
        });
        weekFilter.setOnClickListener(v -> {
            selectFilter(view.getContext(), 2);
            Log.d("tttttttttttttt ",ProjectsPDO.projectList.size()+"");
        });
        monthFilter.setOnClickListener(v -> {
            selectFilter(view.getContext(), 3);
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        fetchData(view);
//        List<String> textList = new ArrayList<String>();
//        textList.add("aa");
//        textList.add("bb");
//        ProjectsPDO.projectList.add(new Project("1", "Database", 50, new Date(System.currentTimeMillis()), "", textList));
//        ProjectsPDO.projectList.add(new Project("2", "Database", 70, new Date(System.currentTimeMillis()), "", textList));
//        ProjectsPDO.projectList.add(new Project("3", "Application", 50, new Date(System.currentTimeMillis()), "", textList));
//        ProjectsPDO.projectList.add(new Project("4", "Database", 50, new Date(2022, 6, 2), "", textList));
        projectsAdapter = new ProjectsAdapter(view.getContext(), ProjectsPDO.projectList);
//        GridLayoutManager mGridLayoutManager = new GridLayoutManager(view.getContext(), 2);
//        recyclerView.setLayoutManager(mGridLayoutManager);
//        recyclerView.setAdapter(projectsAdapter);
        projectsAdapter.setOnItemClickListener((position, v) -> {
            Intent intent = new Intent(view.getContext(), ProjectDetails.class);
            intent.putExtra("id", ProjectsPDO.projectList.get(position).id);
            startActivity(intent);
        });
        return view;
    }

    void selectFilter(Context context, int item) {
        switch (item) {
            case 1:
                weekFilter.setBackground(null);
                monthFilter.setBackground(null);
                allFilter.setBackground(ContextCompat.getDrawable(context, R.drawable.radius_shape));
                break;
            case 2:
                allFilter.setBackground(null);
                monthFilter.setBackground(null);
                weekFilter.setBackground(ContextCompat.getDrawable(context, R.drawable.radius_shape));
                break;
            case 3:
                allFilter.setBackground(null);
                weekFilter.setBackground(null);
                monthFilter.setBackground(ContextCompat.getDrawable(context, R.drawable.radius_shape));
                break;
        }
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
                                    ProjectsPDO.projectList.add(Project.fromMap(document.getData(),document.getId()));
                                } catch (ParseException e) {
                                    Shared.showSnackBar(view,"Error Occur");
                                }

                            }
                            GridLayoutManager mGridLayoutManager = new GridLayoutManager(view.getContext(), 2);
                            recyclerView.setLayoutManager(mGridLayoutManager);
                            recyclerView.setAdapter(projectsAdapter);
                        } else {
                            Shared.showSnackBar(view,"Error Occur");
                        }
                    }
                });
    }
}
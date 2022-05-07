package com.example.make_it_easy;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.make_it_easy.models.Project;
import com.example.make_it_easy.models.ProjectTask;
import com.example.make_it_easy.pdo.ProjectsPDO;
import com.example.make_it_easy.pdo.UserPDO;
import com.example.make_it_easy.shared.Shared;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddFragment extends Fragment {
    EditText projectName, projectDeadline, projectAttached, stepOne, stepTwo, stepThree, stepFour;
    Button createProject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        RelativeLayout containerTwo = view.findViewById(R.id.container_two);
        RelativeLayout containerThree = view.findViewById(R.id.container_three);
        RelativeLayout containerFour = view.findViewById(R.id.container_four);

        projectName = view.findViewById(R.id.project_name_et);
        projectDeadline = view.findViewById(R.id.project_deadline_et);
        projectAttached = view.findViewById(R.id.project_attached_et);
        stepOne = view.findViewById(R.id.step_one_et);
        stepTwo = view.findViewById(R.id.step_two_et);
        stepThree = view.findViewById(R.id.step_three_et);
        stepFour = view.findViewById(R.id.step_four_et);
        createProject = view.findViewById(R.id.create_project_btn);

        Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (vi, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDate(myCalendar);
        };
        projectDeadline.setOnClickListener(v -> {
            new DatePickerDialog(view.getContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        createProject.setOnClickListener(v -> {
            List<ProjectTask> stepList = new ArrayList<>();
            if (TextUtils.isEmpty(projectName.getText()))
                projectName.setError("Enter Value");
            else if (TextUtils.isEmpty(projectDeadline.getText()))
                projectDeadline.setError("Enter Value");
//            else if (TextUtils.isEmpty(projectAttached.getText()))
//                projectAttached.setError("Enter Value");
            else if (TextUtils.isEmpty(stepOne.getText()))
                stepOne.setError("Enter Value");
            else if ((containerTwo.getVisibility() == View.VISIBLE) && TextUtils.isEmpty(stepTwo.getText()))
                stepTwo.setError("Enter Value");
            else if ((containerThree.getVisibility() == View.VISIBLE) && TextUtils.isEmpty(stepThree.getText()))
                stepThree.setError("Enter Value");
            else if ((containerFour.getVisibility() == View.VISIBLE) && TextUtils.isEmpty(stepFour.getText()))
                stepFour.setError("Enter Value");
            else {
                stepList.add(new ProjectTask(stepOne.getText().toString(),false));
                if (!TextUtils.isEmpty(stepTwo.getText()))
                    stepList.add(new ProjectTask(stepTwo.getText().toString(),false));
                if (!TextUtils.isEmpty(stepThree.getText()))
                    stepList.add(new ProjectTask(stepThree.getText().toString(),false));
                if (!TextUtils.isEmpty(stepFour.getText()))
                    stepList.add(new ProjectTask(stepFour.getText().toString(),false));
                @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Project project = new Project("", projectName.getText().toString(), 0, formatter.parse(projectDeadline.getText().toString()), "", stepList);
                    db.collection("users").document(UserPDO.user.email).collection("projects")
                            .add(project.toMap())
                            .addOnSuccessListener(aVoid -> {
                                Shared.showSnackBar(container,"Project inserted done");
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Shared.showSnackBar(container,"Error writing document");

                                }
                            });
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        });
        Button addStep = view.findViewById(R.id.add_step_btn);
        addStep.setOnClickListener(v -> {
            if (containerTwo.getVisibility() == View.GONE)
                containerTwo.setVisibility(View.VISIBLE);
            else if (containerThree.getVisibility() == View.GONE)
                containerThree.setVisibility(View.VISIBLE);
            else {
                containerFour.setVisibility(View.VISIBLE);
                addStep.setVisibility(View.GONE);
            }

        });
        return view;
    }

    private void setDate(Calendar calendar) {
        String format = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        projectDeadline.setText(sdf.format(calendar.getTime()));
    }
}
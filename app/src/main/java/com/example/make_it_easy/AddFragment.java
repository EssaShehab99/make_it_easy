package com.example.make_it_easy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class AddFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add, container, false);
        RelativeLayout containerThree=view.findViewById(R.id.container_three);
        RelativeLayout containerFour=view.findViewById(R.id.container_four);
        Button addStep=view.findViewById(R.id.add_step_btn);
        addStep.setOnClickListener(v -> {
            if(containerThree.getVisibility()==View.GONE)
                containerThree.setVisibility(View.VISIBLE);
            else{
                containerFour.setVisibility(View.VISIBLE);
                addStep.setVisibility(View.GONE);
        }
        });
        return view;
    }
}
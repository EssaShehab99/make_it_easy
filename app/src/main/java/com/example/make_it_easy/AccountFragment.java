package com.example.make_it_easy;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AccountFragment extends Fragment {
    ImageView profileColor;
    ImageView profileIMG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        profileColor = view.findViewById(R.id.profile_color);
        profileIMG = view.findViewById(R.id.profile_img);
        view.findViewById(R.id.color_btn_1).setOnClickListener(v -> {
            changeColor(1);
        });
        view.findViewById(R.id.color_btn_2).setOnClickListener(v -> {
            changeColor(2);
        });
        view.findViewById(R.id.color_btn_3).setOnClickListener(v -> {
            changeColor(3);
        });
        view.findViewById(R.id.color_btn_4).setOnClickListener(v -> {
            changeColor(4);
        });
        view.findViewById(R.id.profile_btn_1).setOnClickListener(v -> {
            changeProfileImage(view.getContext(),1);
        });
        view.findViewById(R.id.profile_btn_2).setOnClickListener(v -> {
            changeProfileImage(view.getContext(),2);
        });
        view.findViewById(R.id.profile_btn_3).setOnClickListener(v -> {
            changeProfileImage(view.getContext(),3);
        });
        view.findViewById(R.id.profile_btn_4).setOnClickListener(v -> {
            changeProfileImage(view.getContext(),4);
        });
        return view;
    }

    void changeColor(int item) {
        switch (item) {
            case 1:
                profileColor.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_1)));
                break;
            case 2:
                profileColor.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_2)));
                break;
            case 3:
                profileColor.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_3)));
                break;
            case 4:
                profileColor.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_4)));
                break;
        }
    }
    void changeProfileImage( Context context,int item) {
        switch (item) {
            case 1:
                profileIMG.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profile_1));
                break;
            case 2:
                profileIMG.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profile_2));
                break;
            case 3:
                profileIMG.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profile_3));
                break;
            case 4:
                profileIMG.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profile_4));
                break;
        }
    }
}
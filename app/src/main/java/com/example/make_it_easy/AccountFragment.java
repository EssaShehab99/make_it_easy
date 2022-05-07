package com.example.make_it_easy;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.make_it_easy.pdo.UserPDO;

public class AccountFragment extends Fragment {
    ImageView profileColor, profileIMG;
    EditText name, email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        name = view.findViewById(R.id.name_et);
        email = view.findViewById(R.id.email_et);
        name.setText(UserPDO.user.name);
        email.setText(UserPDO.user.email);
        profileColor = view.findViewById(R.id.profile_color);
        profileColor.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.circle_shape));
        profileColor.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(UserPDO.user.color)));
        profileIMG = view.findViewById(R.id.profile_img);
        profileIMG.setBackground(ContextCompat.getDrawable(view.getContext(), UserPDO.user.image));

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
            changeProfileImage(view.getContext(), 1);
        });
        view.findViewById(R.id.profile_btn_2).setOnClickListener(v -> {
            changeProfileImage(view.getContext(), 2);
        });
        view.findViewById(R.id.profile_btn_3).setOnClickListener(v -> {
            changeProfileImage(view.getContext(), 3);
        });
        view.findViewById(R.id.profile_btn_4).setOnClickListener(v -> {
            changeProfileImage(view.getContext(), 4);
        });
        return view;
    }

    void changeColor(int item) {
        switch (item) {
            case 1:
                profileColor.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_1)));
                UserPDO.user.color = R.color.color_1;
                UserPDO.updateColorUser();
                break;
            case 2:
                profileColor.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_2)));
                UserPDO.user.color = R.color.color_2;
                UserPDO.updateColorUser();
                break;
            case 3:
                profileColor.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_3)));
                UserPDO.user.color = R.color.color_3;
                UserPDO.updateColorUser();
                break;
            case 4:
                profileColor.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_4)));
                UserPDO.user.color = R.color.color_4;
                UserPDO.updateColorUser();
                break;
        }
    }

    void changeProfileImage(Context context, int item) {
        switch (item) {
            case 1:
                profileIMG.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profile_1));
                UserPDO.user.image = R.drawable.ic_profile_1;
                UserPDO.updateImageUser();
                break;
            case 2:
                profileIMG.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profile_2));
                UserPDO.user.image = R.drawable.ic_profile_2;
                UserPDO.updateImageUser();
                break;
            case 3:
                profileIMG.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profile_3));
                UserPDO.user.image = R.drawable.ic_profile_3;
                UserPDO.updateImageUser();
                break;
            case 4:
                profileIMG.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profile_4));
                UserPDO.user.image = R.drawable.ic_profile_4;
                UserPDO.updateImageUser();
                break;
        }
    }
}
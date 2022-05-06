package com.example.make_it_easy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.make_it_easy.models.User;
import com.example.make_it_easy.pdo.UserPDO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstName = findViewById(R.id.first_name_et);
        lastName = findViewById(R.id.last_name_et);
        email = findViewById(R.id.email_et);
        password = findViewById(R.id.password_et);
        findViewById(R.id.have_account).setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
        });
        findViewById(R.id.create_btn).setOnClickListener(v -> {
            UserPDO.user = new User("", firstName.getText().toString() + " " + lastName.getText().toString(), email.getText().toString(), password.getText().toString(), R.drawable.ic_profile_1, R.color.color_1);
            signUp();
        });
    }

    public void signUp() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("users")
                .add(UserPDO.user.toMap())
                .addOnSuccessListener(aVoid -> {
//                    UserPDO.user.id=aVoid;
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    Log.d("TAG", "Success writing document");
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }
}
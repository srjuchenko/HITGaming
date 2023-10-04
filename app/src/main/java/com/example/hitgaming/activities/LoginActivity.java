package com.example.hitgaming.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hitgaming.R;
import com.example.hitgaming.models.User;
import com.example.hitgaming.services.FirebaseDB;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    // widgets
    private EditText emailField;
    private EditText passField;
    private Button loginButton;
    private Button registerButton;
    // firebase
    private FirebaseDB db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFields();
        setListeners();
    }

    private void setListeners() {
        registerButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passField.getText().toString();
            if (email.isEmpty() || password.isEmpty()) return;
            regFunc(email, password);
        });
        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passField.getText().toString();
            if (email.isEmpty() || password.isEmpty()) return;
            loginFunc(email, password);
        });
    }

    public void regFunc(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        showToast("Reg Success");
                        clearInputs();

                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        assert currentUser != null;
                        User user = new User(email, currentUser.getUid());
                        db.addUser(user);
                    } else {
                        showToast("Reg Failure");
                        clearInputs();
                    }
                });
    }

    public void loginFunc(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        showToast("Login Success");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        // Finish the LoginActivity to prevent going back to it on pressing the back button
                        finish();

                    } else {
                        showToast("Login Failure");
                        clearInputs();
                    }
                });
    }

    private void initFields() {
        db = FirebaseDB.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.input_email);
        passField = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        registerButton = findViewById(R.id.btn_register);
    }

    private void clearInputs() {
        passField.setText("");
        emailField.setText("");
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
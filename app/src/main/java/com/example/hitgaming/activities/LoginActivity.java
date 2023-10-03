package com.example.hitgaming.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hitgaming.R;
import com.example.hitgaming.models.User;
import com.example.hitgaming.services.FirebaseDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField;
    private EditText passField;
    private Button loginButton;
    private Button registerButton;
    private FirebaseDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFields();
        setListeners();
    }

    private void setListeners() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password = passField.getText().toString();
                if (email.isEmpty() || password.isEmpty()) return;
                regFunc(email, password);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password = passField.getText().toString();
                if (email.isEmpty() || password.isEmpty()) return;
                loginFunc(email, password);
            }
        });
    }

    public void regFunc(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            showToast("Reg Success");
                            clearInputs();

                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            User user = new User(email, currentUser.getUid());
                            db.addUser(user);
                        } else {
                            showToast("Reg Failure");
                            clearInputs();
                        }
                    }
                });
    }

    public void loginFunc(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
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
package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText emailField, passwordField;
    Button loginBtn;
    TextView registerText;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // UI elements
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginBtn = findViewById(R.id.loginBtn);
        registerText = findViewById(R.id.registerText);

        // LOGIN
        loginBtn.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            loginBtn.setEnabled(false);
            loginBtn.setText("Logging in...");

            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(result -> {
                        String uid = auth.getCurrentUser().getUid();

                        //  Check if this user is admin
                        db.collection("users").document(uid).get()
                                .addOnSuccessListener(doc -> {
                                    boolean isAdmin = doc.getBoolean("isAdmin") != null
                                            && doc.getBoolean("isAdmin");

                                    if (isAdmin) {
                                        // Go to Admin Dashboard
                                        startActivity(new Intent(LoginActivity.this,
                                                AdminDashboardActivity.class));
                                    } else {
                                        // 👉 Normal user → Home
                                        startActivity(new Intent(LoginActivity.this,
                                                HomeActivity.class));
                                    }
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this,
                                            "Failed to load profile: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                    loginBtn.setEnabled(true);
                                    loginBtn.setText("Login");
                                });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this,
                                "Login failed: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                        loginBtn.setEnabled(true);
                        loginBtn.setText("Login");
                    });
        });

        // Go to Sign Up
        registerText.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }
}

package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupEmail, signupPassword;
    Button createAccountBtn;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signupName);
        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        createAccountBtn = findViewById(R.id.createAccountBtn);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        createAccountBtn.setOnClickListener(v -> {
            String name = signupName.getText().toString().trim();
            String email = signupEmail.getText().toString().trim();
            String password = signupPassword.getText().toString().trim();

            // VALIDATION
            if (name.isEmpty()) {
                signupName.setError("Enter your full name");
                return;
            }
            if (email.isEmpty()) {
                signupEmail.setError("Enter your email");
                return;
            }
            if (!email.contains("@")) {
                signupEmail.setError("Enter a valid email");
                return;
            }
            if (password.length() < 6) {
                signupPassword.setError("Password must be at least 6 characters");
                return;
            }

            createAccountBtn.setEnabled(false);
            createAccountBtn.setText("Creating...");

            // CREATE USER IN FIREBASE AUTH
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(result -> {
                        String userId = auth.getCurrentUser().getUid();

                        // SAVE PROFILE IN FIRESTORE
                        HashMap<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("email", email);
                        user.put("isAdmin", false);

                        db.collection("users")
                                .document(userId)
                                .set(user)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(SignupActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(SignupActivity.this, "Firestore Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    createAccountBtn.setEnabled(true);
                                    createAccountBtn.setText("Create Account");
                                });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(SignupActivity.this, "Signup Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        createAccountBtn.setEnabled(true);
                        createAccountBtn.setText("Create Account");
                    });
        });
    }
}

package com.example.mobileapp13;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnLogin, btnRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        btnLogin = findViewById(R.id.loginButton);
        btnRegister = findViewById(R.id.registerButton);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Введите и логин, и пароль!",
                        Toast.LENGTH_SHORT).show();
            } else {
                loginUser(emailInput, passwordInput, mAuth);
            }
        });

        btnRegister.setOnClickListener(view -> {
            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Введите и логин, и пароль!",
                        Toast.LENGTH_SHORT).show();
            } else {
                registerUser(emailInput, passwordInput, mAuth);
            }
        });

    }

    public void registerUser(String email, String password, @NonNull FirebaseAuth mAuth) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Регистрация успешна!",
                                Toast.LENGTH_SHORT).show();
                        Log.d("FirebaseAuth", "User registered successfully");
                    } else {
                        Toast.makeText(this, "Регистрация провальна!",
                                Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseAuth", "Registration failed", task.getException());
                    }
                });
    }

    public void loginUser(String email, String password, @NonNull FirebaseAuth mAuth) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Авторизация успешна!",
                                Toast.LENGTH_SHORT).show();
                        Log.d("FirebaseAuth", "User signed in successfully");
                    } else {
                        Toast.makeText(this, "Авторизация провальна!",
                                Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseAuth", "Login failed", task.getException());
                    }
                });
    }
}
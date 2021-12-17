package com.example.teamproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity{
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignin;
    TextView textviewSignup;
    TextView textviewFindPassword;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_SafeTrip);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textviewSignup= (TextView) findViewById(R.id.textViewSignup);
        textviewFindPassword = (TextView) findViewById(R.id.textViewFindpassword);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        progressDialog = new ProgressDialog(this);

        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        textviewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        textviewFindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, FindActivity.class));
            }
        });
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
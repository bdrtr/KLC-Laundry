package com.kilicarslan.KLC.LOGSCREEN;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kilicarslan.KLC.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPage extends AppCompatActivity {

    private Button signButton;
    private EditText email,passwd1, passwd2;
    private FirebaseAuth mAuth;
    private ProgressBar progress;
    private ImageView img;
    private String getEmail,pass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        definitions();
        events();
    }




    protected boolean IswrongPassword(String pass1 , String pass2) {

        if (pass1.isEmpty() || pass2.isEmpty()) {
            Toast.makeText(SignUpPage.this, R.string.emptyWarning,Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (pass1.length()<6) {
            Toast.makeText(SignUpPage.this, R.string.shotPassWarning,Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (!pass1.equals(pass2)) {
            Toast.makeText(SignUpPage.this, R.string.doesntmatchPass,Toast.LENGTH_SHORT).show();
            return true;
        }


        return false;
    }

    protected boolean Iswrong(String value) {
        if (value.isEmpty()) {
            Toast.makeText(SignUpPage.this, R.string.emptyWarning,Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    protected boolean entriesControl() {
        getEmail = email.getText().toString();
        pass1 = passwd1.getText().toString();
        String pass2 = passwd2.getText().toString();

        if (Iswrong(getEmail)) {
            return false;
        }
        else if (IswrongPassword(pass1, pass2))
            return false;

        return true;
    }
    protected void events() {

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.signUpSupIMG,Toast.LENGTH_LONG).show();
            }
        });
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(entriesControl()) {
                    Registered();
                }
            }
        });
    }

    protected void definitions() {
        img = findViewById(R.id.imageInfo);
        img.setClickable(true);
        signButton = findViewById(R.id.signUpButton);
        progress = findViewById(R.id.signUpProgressBar);
        progress.setVisibility(View.INVISIBLE);
        email = findViewById(R.id.signUpEmail);
        passwd1 = findViewById(R.id.signUppassword);
        passwd2 = findViewById(R.id.signUppasswordagain);

        mAuth = FirebaseAuth.getInstance();//nesne oluşturduk internete baglanmak için

    }

    protected void Registered() {
        mAuth.createUserWithEmailAndPassword(getEmail, pass1)
                .addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), R.string.signup_success,Toast.LENGTH_LONG).show();
                            progress.setVisibility(View.VISIBLE);

                            startActivity(new Intent(SignUpPage.this, loginScreen.class));
                        }
                        else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}
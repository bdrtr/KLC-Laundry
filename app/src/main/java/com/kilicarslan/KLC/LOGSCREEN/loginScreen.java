package com.kilicarslan.KLC.LOGSCREEN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kilicarslan.KLC.MainPages.MainActivity;
import com.kilicarslan.KLC.R;
import com.kilicarslan.KLC.Services.PreferenceService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginScreen extends AppCompatActivity {
    private Button logButton,signUp;
    private ProgressBar progressBar;
    private TextView upPageText, downPageText;
    private EditText userNameText, passwordText;
    private FirebaseAuth mAuth;
    private PreferenceService pService;

    String Email,Passwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_KLCLaundry);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        definitions();
        initialize();
        events();

    }


    protected void  Login() {
        mAuth.signInWithEmailAndPassword(Email, Passwd) //dokumantasyon
                .addOnSuccessListener(this ,new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        RegTheId();
                        startActivity(new Intent(loginScreen.this, MainActivity.class));
                    }
                }) .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(loginScreen.this, e.getMessage().toString(),Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }
    protected void events() {


        downPageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.hideWarning,Toast.LENGTH_SHORT).show();
            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = userNameText.getText().toString();
                Passwd = passwordText.getText().toString();

                if (Email.isEmpty() || Passwd.isEmpty() ) {
                    Toast.makeText(loginScreen.this,R.string.emptyWarning, Toast.LENGTH_SHORT).show();
                }

                else {
                    progressBar.setVisibility(View.VISIBLE);
                    Login();
                }

            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginScreen.this, SignUpPage.class));
            }
        });
    }

    protected  void initialize() {
        progressBar.setVisibility(View.INVISIBLE);
        logButton.setText(R.string.log_in);
        signUp.setText(R.string.sign_up);
        //upPageText.setText(R.string.loginUpText);
        downPageText.setText(R.string.loginDownText);
        downPageText.setClickable(true);
        userNameText.setText(pService.get("email",""));
        passwordText.setText(pService.get("password",""));
    }

    protected void definitions() {
        progressBar = findViewById(R.id.progressBar);
        logButton = findViewById(R.id.logScreenLoginButton);
        //upPageText = findViewById(R.id.logScreenText);
        downPageText = findViewById(R.id.logScreenUnderText);
        userNameText = findViewById(R.id.logScreenUsername);
        passwordText = findViewById(R.id.logScreenPassword);
        signUp = findViewById(R.id.logScreenSignUp);
        pService = new PreferenceService(getApplicationContext()); //hafizaya kaydetmek için bir nesne olşuturuyorum

        mAuth = FirebaseAuth.getInstance();
    }

    protected void RegTheId() {

        /*
        char[] chars = Email.toCharArray();
        String id="";
        for(int i=0;i<Email.length();i++) {
            if(chars[i] == '@' && (i+1)<Email.length()) {
                id = Email.split("@")[0];
            }
        }
         */
        //giriş yapan son kişinin kullanıcı adı

        pService.push("id",Email.split("@")[0]);
        pService.push("email",Email);
        pService.push("password",Passwd);
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }

}
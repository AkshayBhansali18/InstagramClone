package com.example.instagramclone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextView register_textView;
    EditText emaileditText,passwordeditText;
    Button loginButton;
    private static final String TAG="Login Activity";
    ProgressBar loginProgressbar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Login ACtivity","Oncreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register_textView=findViewById(R.id.register_textView);
        emaileditText=findViewById(R.id.email_edittext);
        loginProgressbar=findViewById(R.id.login_progressbar);
        passwordeditText=findViewById(R.id.password_edittext);
        loginButton=findViewById(R.id.loginButton);
        loginProgressbar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        register_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginfirebase();
    }

    private void loginfirebase() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgressbar.setVisibility(View.VISIBLE);
                String email = emaileditText.getText().toString();
                String password = passwordeditText.getText().toString();
                if (email.isEmpty()) {
                    loginProgressbar.setVisibility(View.GONE);
                    emaileditText.setError("Please Enter Valid Email");
                }
                if (password.isEmpty()) {
                    loginProgressbar.setVisibility(View.GONE);
                    passwordeditText.setError("Please Enter Valid Email");
                }
                 else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if(user.isEmailVerified())
                                        {
                                            Toast.makeText(LoginActivity.this, "Sign In Successful", Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            loginProgressbar.setVisibility(View.GONE);

                                        }
                                        else
                                        {
                                            loginProgressbar.setVisibility(View.GONE);
                                            Toast.makeText(LoginActivity.this, "Login Unsuccessful, Please Verify Email", Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                        }

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        loginProgressbar.setVisibility(View.VISIBLE);
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        loginProgressbar.setVisibility(View.GONE);
                                    }


                                    // ...
                                }

                            });
                }
            }
        });

    }


}

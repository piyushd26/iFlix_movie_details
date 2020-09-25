package com.example.myappnew.Activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myappnew.Interfaces.DataPassInterface;
import com.example.myappnew.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    FirebaseAuth auth;
    ProgressBar progressBar;
    Button button;
    private String task1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.reset_et);
        progressBar = findViewById(R.id.reset_pb);
        button = findViewById(R.id.reset_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotpassword();
            }
        });

    }

    private void forgotpassword() {
        progressBar.setVisibility(View.VISIBLE);
        if (!isValidEmail(email.getText().toString().trim())) {
            Toast.makeText(ForgotPasswordActivity.this, "Not a valid email", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        } else {
            final String newResetEmail = email.getText().toString().trim();
            auth.getInstance().sendPasswordResetEmail(newResetEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "Email is send to " + newResetEmail, Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);


                            } else {

                                Toast.makeText(ForgotPasswordActivity.this, "Reset Password Failed Try Again Later", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);


                                Toast.makeText(ForgotPasswordActivity.this, task1, Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }

    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


}

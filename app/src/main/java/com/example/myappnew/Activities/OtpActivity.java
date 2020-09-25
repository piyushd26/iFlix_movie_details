package com.example.myappnew.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myappnew.Interfaces.DataPassInterface;
import com.example.myappnew.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity{

     ProgressDialog progressdialog;
    DataPassInterface dataPassInterface;


    private EditText editTextMobile;
    private String mVerificationId;
    VerifyPhoneActivity verifyPhoneActivity=new VerifyPhoneActivity();
    FirebaseAuth firebaseAuth;
    //FirebaseAuth firebaseAuthOfOtpActivity;
     EditText editTextCode;
    FirebaseUser firebaseUser;
    String newemail;
    String newpassword;
    Button buttonSignIn;
    String mobile;
    String name;

    Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        final Intent intent = getIntent();

        Bundle bundle = getIntent().getExtras();
        newemail = bundle.getString("email");
        name=bundle.getString("name");
        newpassword = bundle.getString("password");

        editTextCode = findViewById(R.id.editTextCode);
        editTextMobile = findViewById(R.id.editTextMobile);
        firebaseAuth = FirebaseAuth.getInstance();
        buttonSignIn=findViewById(R.id.buttonSignIn);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              mobile  = editTextMobile.getText().toString().trim();

                if (mobile.isEmpty() || mobile.length() < 10) {
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }


                    intent1 = new Intent(OtpActivity.this, VerifyPhoneActivity.class);
                    intent1.putExtra("mobile", mobile);
                    intent1.putExtra("name", name);
                    intent1.putExtra("email", newemail);
                        intent1.putExtra("password", newpassword);
                    startActivity(intent1);

            }
        });
    }

}







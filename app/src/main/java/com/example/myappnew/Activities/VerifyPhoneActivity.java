package com.example.myappnew.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myappnew.Interfaces.DataPassInterface;
import com.example.myappnew.R;
import com.example.myappnew.User;
import com.example.myappnew.Utility;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    String mEmail;
    String nName;
    String mPassword;
    String messegeCode;
    FirebaseAuth mAuth;
    EditText editTextCode;
    String mVerificationId;
    private int returning = 2;
    ProgressDialog progressdialog;
    String mobile;
    FirebaseUser mfirebaseuserAuth;
    Context Acivity_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        editTextCode = findViewById(R.id.editTextCode);
        mAuth = FirebaseAuth.getInstance();
        mfirebaseuserAuth = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        nName = intent.getStringExtra("name");
        mEmail = intent.getStringExtra("email");
        mPassword = intent.getStringExtra("password");

        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }
                //verifyVerificationCode(code);
                if (messegeCode.equals(code)) {
                    createFirebaseUser();
                } else {
                    Toast.makeText(VerifyPhoneActivity.this, "Code is not matching", Toast.LENGTH_LONG).show();
                }
            }
        });


        firebaseVerify();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void firebaseVerify() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


            messegeCode = phoneAuthCredential.getSmsCode();

            if (messegeCode != null) {
                // editTextCode.setText(code);
                // verifyVerificationCode(code);

            } else {
                Toast.makeText(VerifyPhoneActivity.this, "Checck your code", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, mEmail + " " + mPassword + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("phoneauth", "onVerificationFailed: " + e);
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.e("codeof", "onCodeSent: s - " + s + forceResendingToken);

            mVerificationId = s;

        }
    };

    private void verifyVerificationCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (createFirebaseUser() == 1) {

                                Intent intent = new Intent(VerifyPhoneActivity.this, LogedInActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                        } else {


                            String message = "Somthing is wrong, we will fix it soon...";
                            Toast.makeText(VerifyPhoneActivity.this, "Else", Toast.LENGTH_LONG).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }


    private int createFirebaseUser() {

        mAuth.getInstance().createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(VerifyPhoneActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Utility.saveSP_Userdata(nName, mEmail, mPassword, Acivity_context);


                            mfirebaseuserAuth = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nName)
                                    .build();
                            mfirebaseuserAuth.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("Username", "User profile updated." + " " + mfirebaseuserAuth.getDisplayName());
                                }
                            });


                            Toast.makeText(VerifyPhoneActivity.this, "New User", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(VerifyPhoneActivity.this, LogedInActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            progressdialog.dismiss();

                            returning = 1;
                        } else {
                            Toast.makeText(VerifyPhoneActivity.this, "failed to create user", Toast.LENGTH_LONG).show();
                            returning = 0;
                        }

                    }
                });
        return returning;
    }


}

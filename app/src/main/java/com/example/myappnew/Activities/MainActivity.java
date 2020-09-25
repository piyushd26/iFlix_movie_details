package com.example.myappnew.Activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myappnew.MyPasswordTransformationMethod;
import com.example.myappnew.R;
import com.example.myappnew.SharedPrefManager;
import com.example.myappnew.Utility;
import com.example.myappnew.pojo.LoginCallback;
import com.example.myappnew.presenter.LoginPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    ProgressDialog progressDialog;
    LoginCallback loginCallback;
    String Stringemail;
    String Stringpassword;
    Context context;
    String Myemail = "p.dhiman260@gmail.com";
    String Mypassword = "piyush";
    LoginPresenter loginPresenter;
    FirebaseAuth firebaseAuth;
    String thisEmail;
    TextView fortpassword;

    String thisPassword;
    LogedInActivity logedInActivity;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password=findViewById(R.id.passwordet);
        password.setTransformationMethod(new MyPasswordTransformationMethod());
        fortpassword=findViewById(R.id.forotpassword);
        firebaseAuth = FirebaseAuth.getInstance();

        initialize();

        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

              String s =  SharedPrefManager.getInstance(MainActivity.this).gettoken();
            Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }
        };
        if(SharedPrefManager.getInstance(this).gettoken()==null) {
            String s = SharedPrefManager.getInstance(MainActivity.this).gettoken();
            //  Log.d("myfcmtokenshared", SharedPrefManager.getInstance(this).gettoken());
        }
  //     registerReceiver(broadcastReceiver,new IntentFilter(MyFirebaseInstanceIdService.TOKEN_BROADCAST));

    }

    private void forotpass() {
        Intent intent = new Intent(MainActivity.this,ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public Context getMainActivityContext(){
        return context;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(MainActivity.this, LogedInActivity.class));
        }
    }

    public void loginaccount(View view) {
        thisEmail = email.getText().toString();
        thisPassword = password.getText().toString();

        if (validation() == false) {
            progressDialog.dismiss();
           // Toast.makeText(MainActivity.this, "Email and password Doesnt Match", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.dismiss();


            firebaseAuth.signInWithEmailAndPassword(thisEmail, thisPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "login is done", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(MainActivity.this, LogedInActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private Boolean validation() {
        Boolean valid = false;
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        if (TextUtils.isEmpty(thisEmail)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(thisPassword)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            valid = false;
            return valid;
        }


        return true;

    }


    private void initialize() {
        progressDialog = new ProgressDialog(MainActivity.this,
                R.style.AppTheme);

        email = findViewById(R.id.emailet);
        password = findViewById(R.id.passwordet);

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    password.setHint("");
                else
                    password.setHint("Password");
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    email.setHint("");
                else
                    email.setHint("Email");
            }
        });

        fortpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forotpass();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // rest of the code
        if(firebaseAuth.getCurrentUser()!=null)
        {

            finish();
                Intent intent =new Intent(MainActivity.this,LogedInActivity.class);
                startActivity(intent);

        }
    }

    public void signuphere(View view) {
        startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
    }
    public void pushFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.container_loggedin, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}






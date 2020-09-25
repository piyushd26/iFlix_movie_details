package com.example.myappnew.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myappnew.Interfaces.DataPassInterface;
import com.example.myappnew.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity extends AppCompatActivity{

    TextView signup;
    EditText myemail;
    EditText mypassword;
    String confirmpassword;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String newemail;
    String newpassword;
    EditText myUsername;
    String newname;
    EditText ConfirmPassword_et;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        myemail = findViewById(R.id.cemailet);
        mypassword = findViewById(R.id.cpasswordet);
        ConfirmPassword_et = findViewById(R.id.editText);
        signup = findViewById(R.id.signup_btn);
        myUsername=findViewById(R.id.newname);

        FirebaseApp.initializeApp(this);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


        mypassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    mypassword.setHint("");
                else
                    mypassword.setHint("Password");
            }
        });
        myemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    myemail.setHint("");
                else
                    myemail.setHint("Email");
            }
        });

        ConfirmPassword_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    ConfirmPassword_et.setHint("");
                else
                    ConfirmPassword_et.setHint("Email");
            }
        });
        myUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    myUsername.setHint("");
                else
                    myUsername.setHint("Email");
            }
        });
    }

    private void registerUser() {
        newname=myUsername.getText().toString().trim();
        newemail = myemail.getText().toString().trim();
        newpassword = mypassword.getText().toString().trim();
        confirmpassword = ConfirmPassword_et.getText().toString().trim();

        if (TextUtils.isEmpty(newemail)) {

            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(newpassword)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(newname)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_LONG).show();
            return;
        }
        if (!newpassword.equals(confirmpassword)) {
            Toast.makeText(this, "Password and Confirm Password R not same", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Registering please wait.......");
        progressDialog.show();

        Intent intent=new Intent(this,OtpActivity.class);
       Bundle bundle=new Bundle();
       bundle.putString("email",newemail);
       bundle.putString("password",newpassword);
       bundle.putString("name",newname);
       intent.putExtras(bundle);
       startActivity(intent);





    }

}

package com.example.myappnew.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myappnew.Activities.MainActivity;
import com.example.myappnew.R;
import com.example.myappnew.User;
import com.example.myappnew.Utility;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    Button button_logout;
    Button button_deleteuser;
    TextView firebaseUserName;
    User user;
    View view;
    Utility utility = new Utility();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab2, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        button_logout = view.findViewById(R.id.btn_logout_profile);
        button_deleteuser = view.findViewById(R.id.button_deleteuser);
        firebaseUserName = view.findViewById(R.id.profiile_name);


        firebaseUserName.setText(utility.loadSP_UserData_Username(getContext()));

        Toast.makeText(getContext(), utility.loadSP_UserData_Username(getContext()), Toast.LENGTH_LONG).show();


        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        button_deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getCurrentUser().delete();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        return view;
    }

    public void firebaseButtons() {


    }
}

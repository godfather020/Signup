package com.example.signup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {

    Button phonelogin,loginphonebutton;
    View phoneCC, phonenumber;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        phonelogin = v.findViewById(R.id.loginwithphone);
        loginphonebutton = v.findViewById(R.id.loginbuttonphone);
        phoneCC = v.findViewById(R.id.loginCC);
        phonenumber = v.findViewById(R.id.loginPhoneNumber);

        phonelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginphonebutton.setVisibility(View.VISIBLE);
                phoneCC.setVisibility(View.VISIBLE);
                phonenumber.setVisibility(View.VISIBLE);
            }
        });

        return v;

    }
}
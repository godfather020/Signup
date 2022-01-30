package com.example.signup;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {

    EditText name, username, email, pass, compass, cc, phonenum;
    Button register;
    Boolean isDataValid = false;
    FirebaseAuth fAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        name = v.findViewById(R.id.username);
        username = v.findViewById(R.id.userusername);
        email = v.findViewById(R.id.useremail);
        pass = v.findViewById(R.id.password);
        compass = v.findViewById(R.id.compass);
        cc = v.findViewById(R.id.CC);
        phonenum = v.findViewById(R.id.phonenum);

        fAuth = FirebaseAuth.getInstance();

        //validating the data

        validateData(name);
        validateData(username);
        validateData(email);
        validateData(pass);
        validateData(cc);
        validateData(compass);
        validateData(phonenum);

        if(!pass.getText().toString().equals(compass.getText().toString())){

            isDataValid = false;
            compass.setError("Password Do Not Match");
        }else {

            isDataValid = true;
        }

        if (isDataValid){
            //proceed with the registration
            fAuth.createUserWithEmailAndPassword(email.getText().toString(), compass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(getActivity(), "User Account is Created", Toast.LENGTH_SHORT).show();
                    //send User to verify Phone number
                    Intent phone = new Intent(getActivity(), VerifyPhone.class);
                    phone.putExtra("phone", "+"+cc.getText().toString()+phonenum.getText().toString());
                    startActivity(phone);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }


        return v;
    }

    public void validateData(EditText field){

        if (field.getText().toString().isEmpty()){
            isDataValid = false;
            field.setError("Required field");
        }else {
            isDataValid = true;
        }

    }
}
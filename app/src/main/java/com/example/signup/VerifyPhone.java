package com.example.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyPhone extends AppCompatActivity {

    EditText otpnum1, otpnum2, otpnum3, otpnum4, otpnum5, otpnum6;
    Button verify,resend;
    Boolean otpValid = true;
    FirebaseAuth firebaseAuth;
    PhoneAuthCredential phoneAuthCredential;
    PhoneAuthProvider.ForceResendingToken token;
    String verificationId;
    String phone;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varify_phone);

        Intent data = getIntent();
        phone = data.getStringExtra("phone");

        firebaseAuth = FirebaseAuth.getInstance();

        otpnum1 = findViewById(R.id.otpnum1);
        otpnum2 = findViewById(R.id.otpnum2);
        otpnum3 = findViewById(R.id.otpnum3);
        otpnum4 = findViewById(R.id.otpnum4);
        otpnum5 = findViewById(R.id.otpnum5);
        otpnum6 = findViewById(R.id.otpnum6);

        verify = findViewById(R.id.verify);
        resend = findViewById(R.id.resendbtn);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData(otpnum1);
                validateData(otpnum2);
                validateData(otpnum3);
                validateData(otpnum4);
                validateData(otpnum5);
                validateData(otpnum6);

                if(otpValid){
                    //send otp to user
                    String otp = otpnum1.getText().toString()+otpnum2.getText().toString()+otpnum3.getText().toString()+otpnum4.getText().toString()
                            +otpnum5.getText().toString()+otpnum6.getText().toString();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);

                    verifyAuthentication(credential);

                }
            }
        });



        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationId = s;
                token = forceResendingToken;
                resend.setVisibility(View.GONE);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);

                resend.setVisibility(View.VISIBLE);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verifyAuthentication(phoneAuthCredential);
                resend.setVisibility(View.GONE);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(VerifyPhone.this, "OTP Verification Failed", Toast.LENGTH_SHORT).show();
            }
        };

        sendOTP(phone);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOTP(phone);
            }
        });
    }

    public void sendOTP(String phoneNumber){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks);
    }

    public void resendOTP(String phoneNumber){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks, token);
    }

    public void validateData(EditText field){

        if (field.getText().toString().isEmpty()){
            otpValid = false;
            field.setError("Required field");
        }else {
            otpValid = true;
        }

    }

    public void verifyAuthentication(PhoneAuthCredential credential){

        Objects.requireNonNull(firebaseAuth.getCurrentUser()).linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(VerifyPhone.this, "Account Created and Linked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
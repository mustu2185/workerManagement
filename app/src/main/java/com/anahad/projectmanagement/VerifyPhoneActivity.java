package com.anahad.projectmanagement;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class VerifyPhoneActivity extends AppCompatActivity {

    // this id is sent to user
    private String verificationId;
    // to input code
    private EditText editTextCode;
    //Firebase auth object
    private FirebaseAuth mauth;
    // Button class
    private Button signIn;

    Session session = Session.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        //Initialising objects
        editTextCode = findViewById(R.id.editTextCode);
        signIn = findViewById(R.id.signIn);
        mauth = FirebaseAuth.getInstance(); // getInstance() - Initializes a new PhoneAuthProvider using the default FirebaseAuth instance.

        //to extract mobile number from Intent
        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");
        sendVerificationCode(mobile);

        //if automatic sms detection fails, user can manually enter code and click Sign In button
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextCode.getText().toString().trim();
                if(code.isEmpty() || code.length()<6){
                    editTextCode.setError("Enter Valid Code");
                    editTextCode.requestFocus();
                    return;
                }
                //send the code for verification
                verifyVerificationCode(code);
            }
        });

    }
    // this method sends verification code to the mobile number(country code concatenated)
    private void sendVerificationCode(String mobile){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+ mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mcallback);

    }
    // callback to detect verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //getting the code from sms
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null) {
                editTextCode.setText(code);
                // send code for verification
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;
        }
        @Override
        public void onCodeAutoRetrievalTimeOut(String verificationId){

        }

    };
    private void verifyVerificationCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        // Signing the user
        signInWithPhoneAuthCredential(credential);

    }
    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential){
        mauth.signInWithCredential(credential).addOnCompleteListener(VerifyPhoneActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            session.setFirebaseUser(FirebaseAuth.getInstance().getCurrentUser());
                            User new_user = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(),"",FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                            FireStoreComm.getInstance().addUser(new_user);
                            Intent intent = new Intent(VerifyPhoneActivity.this,SignIn.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else{
                            // Verification unsuccessful
                            String message = "Something is wrong, Please wait";
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                message = "Invalid Code Entered ";
                            }
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent),message,Snackbar.LENGTH_LONG);
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



}

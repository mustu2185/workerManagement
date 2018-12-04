package com.anahad.projectmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MobileNumberAuth extends AppCompatActivity {
    private EditText mobile;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Session session = Session.getInstance();

        if (user != null){
            session.setFirebaseUser(user);
            Intent intent = new Intent(MobileNumberAuth.this, ProjectListView.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_mobile_auth);
        mobile = (EditText) findViewById(R.id.editTextMobile);
        continueButton = (Button) findViewById(R.id.continueButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = mobile.getText().toString().trim();
                if (number.isEmpty() || number.length() < 10) {
                    mobile.setError("Enter a valid mobile");
                    mobile.requestFocus();
                    return;
                }

                Intent intent = new Intent(MobileNumberAuth.this, VerifyPhoneActivity.class);
                intent.putExtra("mobile", number);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            Intent intent = new Intent(MobileNumberAuth.this, SignIn.class);
            startActivity(intent);
        }
    }
}

package com.anahad.projectmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    EditText username;
    EditText name;
    EditText mobile;
    Button signin;

    FireStoreComm fscomm;

    String _username;
    String _mobile;
    String _name;

    Session session = Session.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        fscomm = FireStoreComm.getInstance();

        username = findViewById(R.id.usernameEdittext);
        name = findViewById(R.id.nameEdittext);
        mobile = findViewById(R.id.mobileEdittext);

        signin = findViewById(R.id.signinButton);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _username = username.getText().toString().trim();
                _name = name.getText().toString().trim();
                _mobile = mobile.getText().toString().trim();

                if (_username.isEmpty())
                {
                    Toast.makeText(SignIn.this, "Enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (_name.isEmpty())
                {
                    Toast.makeText(SignIn.this, "Enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (_mobile.isEmpty() || _mobile.length() != 10)
                {
                    Toast.makeText(SignIn.this, "Enter a valid mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                fscomm.getUser(_username,SignIn.this, new OnCompleteCallbackInterface() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onCompleteWithObject(Object ob) {
                        UserCallback((User) ob);
                    }
                });

            }
        });

    }

    public void UserCallback(User userObj) {

        if (userObj != null)
        {
            if (_mobile.equals(userObj.getMobile()) == false)
            {
                Toast.makeText(SignIn.this, "username already registered with another number", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(SignIn.this, "Sign in successful", Toast.LENGTH_SHORT).show();
        }
        else {
            userObj = new User(_username, _name, _mobile);
            fscomm.addUser(userObj);
            Toast.makeText(SignIn.this, "User signed up", Toast.LENGTH_SHORT).show();
        }
        session.setUser(userObj);
        // note the user and start project activity
    }
}

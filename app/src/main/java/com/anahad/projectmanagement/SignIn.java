package com.anahad.projectmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

public class SignIn extends AppCompatActivity {


    EditText name;

    Button signin;

    FireStoreComm fscomm;

    String _name;

    Session session = Session.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        fscomm = FireStoreComm.getInstance();

        name = findViewById(R.id.nameEdittext);

        signin = findViewById(R.id.signinButton);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _name = name.getText().toString().trim();

                if (_name.isEmpty())
                {
                    Toast.makeText(SignIn.this, "Enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }

                FireStoreComm.getInstance().getUserDocument(session.getFirebaseUser().getUid()).update("name", _name);

                Intent intent = new Intent(SignIn.this,ProjectListView.class);
                startActivity(intent);
            }
        });

    }
}

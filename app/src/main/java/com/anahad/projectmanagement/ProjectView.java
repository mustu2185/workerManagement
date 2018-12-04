package com.anahad.projectmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProjectView extends AppCompatActivity {

    TextView project_name_textview;
    Button users_button;
    Button households_button;
    Button workers_button;

    FireStoreComm fscomm = FireStoreComm.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);

        users_button = findViewById(R.id.project_view_users_button);
        households_button = findViewById(R.id.project_view_households_button);
        workers_button = findViewById(R.id.project_view_workers_button);
        project_name_textview = findViewById(R.id.project_view_project_name);

        Intent intent = getIntent();

        String docid = intent.getStringExtra("project");

        fscomm.getProject(docid, this, new OnCompleteCallbackInterface() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onCompleteWithObject(Object ob) {
                processProject((Project)ob);
            }
        });


    }

    private void processProject(Project ob) {
        if (ob == null)
        {
            Toast.makeText(this, "Something went wrong ...", Toast.LENGTH_SHORT).show();
            finish();
        }

        project_name_textview.setText(ob.getName().trim());

    }
}

package com.anahad.projectmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;

public class ProjectNewView extends AppCompatActivity {


    DocumentReference mDocument;
    EditText projectname_editText;
    EditText projectdesc_editText;
    private String currentUser = Session.getInstance().getUserid();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_new_view);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        projectname_editText = findViewById(R.id.new_project_name_text_view);
        projectdesc_editText = findViewById(R.id.new_project_description_text_view);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_household_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String name = projectname_editText.getText().toString().trim();

        if(name.isEmpty())
        {
            Toast.makeText(this, "Enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        String description = projectdesc_editText.getText().toString().trim();

        if(description.isEmpty())
        {
            Toast.makeText(this, "Enter a description", Toast.LENGTH_SHORT).show();
            return;
        }

        Project project = new Project();

        project.setName(name);
        project.addUser(currentUser);
        project.setDescription(description);

        FireStoreComm.getInstance().addProject(project);
        FireStoreComm.getInstance().addProjectAsMemberInCurrentUser(name);

        String message = "Project added";

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        finish();
    }
}

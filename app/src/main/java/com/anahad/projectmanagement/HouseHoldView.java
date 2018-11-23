package com.anahad.projectmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class HouseHoldView extends AppCompatActivity {

    DocumentReference mDocument;
    EditText username_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_hold_view);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        username_editText = findViewById(R.id.edittext_name_household);

        Intent intent = getIntent();

        mDocument = null;

        String documentPath = intent.getStringExtra("path");
        if (documentPath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Updating ...");
            progressDialog.show();
            //mDocument = FireStoreComm.getInstance().getDocumentByPath(documentPath.trim());
            mDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        DocumentSnapshot snapshot = task.getResult();
                        HouseHold houseHold = snapshot.toObject(HouseHold.class);

                        username_editText.setText(houseHold.getName());
                    }
                    progressDialog.dismiss();
                }
            });
        }

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
        String name = username_editText.getText().toString().trim();

        if(name.isEmpty())
        {
            Toast.makeText(this, "Enter a username", Toast.LENGTH_SHORT).show();
            return;
        }


        String message = "household added";
        if (mDocument != null) {
            mDocument.delete();
            message = "household updated";
        }

        //FireStoreComm.getInstance().addDocument(new HouseHold(name));

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        finish();
    }
}

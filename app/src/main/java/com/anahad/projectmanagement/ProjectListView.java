package com.anahad.projectmanagement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class ProjectListView extends AppCompatActivity {
    private ProjectAdapter adapter;
    private FireStoreComm fscomm = FireStoreComm.getInstance();
    private String user = Session.getInstance().getUserid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_hold_list);

        FloatingActionButton button = findViewById(R.id.button_add_note);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectListView.this, ProjectNewView.class));
            }
        });

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        Query query = fscomm.getProjects().whereArrayContains("members", user);
        FirestoreRecyclerOptions<Project> options = new FirestoreRecyclerOptions.Builder<Project>()
                .setQuery(query,Project.class)
                .build();

        adapter = new ProjectAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListner(new ProjectAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot document, int position) {
                Intent intent = new Intent(ProjectListView.this,ProjectView.class);
                intent.putExtra("project",document.getId());
                Toast.makeText(ProjectListView.this, "Position: " + position + " ID: " + document.getId(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

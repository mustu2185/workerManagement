package com.anahad.projectmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class FireStoreComm {
    private static final FireStoreComm ourInstance = new FireStoreComm();
    private static final String TAG = FireStoreComm.class.getSimpleName();

    private FirebaseFirestore db;
    private CollectionReference users;
    private CollectionReference projects;
    private CollectionReference households;

    public static FireStoreComm getInstance() {
        return ourInstance;
    }

    private FireStoreComm() {
        db = FirebaseFirestore.getInstance();
        users = db.collection("users");
        projects = db.collection("projects");
        households = db.collection("households");
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public CollectionReference getUsers() {
        return users;
    }

    public CollectionReference getProjects() {
        return projects;
    }

    public CollectionReference getHouseholds() {
        return households;
    }

    public DocumentReference getUserDocument(String user) {
        return users.document(user);
    }

    public DocumentReference getHouseholdDocument(String household) {
        return households.document(household);
    }

    public DocumentReference getProjectDocument(String project) {
        return projects.document(project);
    }

    public void addProject(Project project) {
        projects.document(project.getName()).set(project);
    }

    public void addUser(User user)
    {
        users.document(user.getUserid()).set(user);
    }

    public void addHousehold(HouseHold household)
    {
        households.document(household.getName()).set(household);
    }

    public void deleteUser(String user)
    {
        users.document(user).delete();
    }

    public void deleteProject(String project)
    {
        projects.document(project).delete();
    }
    public void deleteHousehold(String household)
    {
        households.document(household).delete();
    }

    public void getUser(String name, Context context, final OnCompleteCallbackInterface callback)
    {
        retrieve(users, name, context, callback, User.class);
    }

    public void getProject(String name, Context context, final OnCompleteCallbackInterface callback)
    {
        retrieve(projects, name, context, callback, Project.class);
    }

    public void getHousehold(String name, Context context, final OnCompleteCallbackInterface callback)
    {
        retrieve(households, name, context, callback, HouseHold.class);
    }

    public void retrieve(CollectionReference collection, String name, Context context, final OnCompleteCallbackInterface callback, final Class<?> _class) {
        ProgressDialog progressDialogTemp = null;
        if (context == null) {
            progressDialogTemp = new ProgressDialog(context);
            progressDialogTemp.setMessage("Processing ...");
            progressDialogTemp.show();
        }
        final ProgressDialog progressDialog = progressDialogTemp;
        Task<DocumentSnapshot> documentSnapshotTask = collection.document(name).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                progressDialog.dismiss();
                callback.onComplete();
                if (task.isSuccessful())
                    callback.onCompleteWithObject(task.getResult().toObject(_class));
                else
                    callback.onCompleteWithObject(null);
            }
        });
    }

    public void addProjectAsMemberInCurrentUser(final String name) {
        users.document(Session.getInstance().firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {

                    task.getResult().toObject(User.class).addProjectAsMember(name);
                }
                else
                {
                    Log.d("Mustafa: ", "addProjectAsMemberInCurrentUser: User update unsuccessful");
                }
            }
        });
    }
}

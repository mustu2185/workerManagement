package com.anahad.projectmanagement;

import com.google.firebase.auth.FirebaseUser;

public class Session {
    private static final Session ourInstance = new Session();

    FirebaseUser firebaseUser;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
        this.userid = firebaseUser.getUid();
    }

    public static Session getInstance() {
        return ourInstance;
    }

    private Session() {
        firebaseUser = null;
        userid = null;
    }

}

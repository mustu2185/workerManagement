package com.anahad.projectmanagement;

import android.support.constraint.solver.widgets.ConstraintAnchor;

import java.util.ArrayList;
import java.util.List;

public class User {
    String name;
    String mobile;
    String username;
    String password;
    String email;
    ArrayList<String> households;
    ArrayList<String> projects;
    String role;

    public User() {
        initialize();

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(String username, String name, String mobile) {
        initialize();
        this.username = username;
        this.name = name;
        this.mobile = mobile;
    }

    private void initialize()
    {
        households = new ArrayList<String>();
        projects = new ArrayList<String>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void addHousehold(String household)
    {
        households.add(household);
    }

    public void removeHousehold(String household)
    {
        households.remove(household);
    }

    public void addProject(String project)
    {
        projects.add(project);
    }

    public void removeProject(String project)
    {
        projects.remove(project);
    }

    public boolean isMemberOfProject(String project)
    {
        return projects.contains(project);
    }

    public boolean isMemberOfHousehold(String household)
    {
        return households.contains(household);
    }

}

package com.anahad.projectmanagement;

import java.util.ArrayList;

public class User {
    private String name;
    private String mobile;
    private String userid;
    private String password;
    private String email;
    private ArrayList<String> households;
    private ArrayList<String> memberInProjects;
    private ArrayList<String> workerInProjects;

    public ArrayList<String> getHouseholds() {
        return households;
    }

    public void setHouseholds(ArrayList<String> households) {
        this.households = households;
    }

    public ArrayList<String> getMemberInProjects() {
        return memberInProjects;
    }

    public void setMemberInProjects(ArrayList<String> memberInProjects) {
        this.memberInProjects = memberInProjects;
    }

    public ArrayList<String> getWorkerInProjects() {
        return workerInProjects;
    }

    public void setWorkerInProjects(ArrayList<String> workerInProjects) {
        this.workerInProjects = workerInProjects;
    }

    public User() {
        initialize();

    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public User(String userid, String name, String mobile) {
        initialize();
        this.userid = userid;
        this.name = name;
        this.mobile = mobile;
    }

    private void initialize()
    {
        households = new ArrayList<String>();
        memberInProjects = new ArrayList<String>();
        workerInProjects = new ArrayList<String>();
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

    public void addProjectAsMember(String project)
    {
        memberInProjects.add(project);
    }

    public void removeProjectAsMember(String project)
    {
        memberInProjects.remove(project);
    }

    public boolean isMemberOfProject(String project)
    {
        return memberInProjects.contains(project);
    }

    public void addProjectAsWorker(String project)
    {
        workerInProjects.add(project);
    }

    public void removeProjectAsWorker(String project)
    {
        workerInProjects.remove(project);
    }

    public boolean isWorkerOfProject(String project)
    {
        return workerInProjects.contains(project);
    }


    public boolean isMemberOfHousehold(String household)
    {
        return households.contains(household);
    }

}

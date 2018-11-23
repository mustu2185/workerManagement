package com.anahad.projectmanagement;

import java.util.ArrayList;

public class Project {
    String name;
    ArrayList<String> households;
    ArrayList<String> members;
    ArrayList<String> nonMembers;
    ArrayList<String> workers;

    public ArrayList<String> getNonMembers() {
        return nonMembers;
    }

    public void setNonMembers(ArrayList<String> nonMembers) {
        this.nonMembers = nonMembers;
    }

    public Project() {
        initialize();
    }

    private void initialize()
    {
        households = new ArrayList<String>();
        members = new ArrayList<String>();
        nonMembers = new ArrayList<String>();
        workers = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getHouseholds() {
        return households;
    }

    public void setHouseholds(ArrayList<String> households) {
        this.households = households;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public ArrayList<String> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<String> workers) {
        this.workers = workers;
    }

    public void addUser(String user)
    {
        members.add(user);
    }

    public void removeUser(String user)
    {
        members.remove(user);
    }

    public boolean hasUser(String user)
    {
        return members.contains(user);
    }

    public void addNonMember(String user)
    {
        nonMembers.add(user);
    }

    public void removeNonMember(String user)
    {
        nonMembers.remove(user);
    }

    public boolean hasNonMember(String user)
    {
        return nonMembers.contains(user);
    }

    public boolean hasHousehold(String household)
    {
        return households.contains(household);
    }

    public void addHousehold(String household)
    {
        households.add(household);
    }

    public void removeHousehold(String household)
    {
        households.remove(household);
    }


}

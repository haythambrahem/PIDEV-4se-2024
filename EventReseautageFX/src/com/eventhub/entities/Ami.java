package com.eventhub.entities;

public class Ami {
    private long idAmi; // primary key
    private User user; // foreign key

    public Ami() {
    }

    public Ami(long idAmi, User user) {
        this.idAmi = idAmi;
        this.user = user;
    }

    public long getIdAmi() {
        return idAmi;
    }

    public void setIdAmi(long idAmi) {
        this.idAmi = idAmi;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
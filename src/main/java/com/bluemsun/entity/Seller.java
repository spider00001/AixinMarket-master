package com.bluemsun.entity;

public class Seller {
    private int sallerId;
    private String sallerName;
    private int campus;

    public Seller(int sallerId, String sallerName, int campus) {
        this.sallerId = sallerId;
        this.sallerName = sallerName;
        this.campus = campus;
    }

    public int getCampus() {
        return campus;
    }

    public void setCampus(int campus) {
        this.campus = campus;
    }

    public int getSallerId() {
        return sallerId;
    }

    public void setSallerId(int sallerId) {
        this.sallerId = sallerId;
    }

    public String getSallerName() {
        return sallerName;
    }

    public void setSallerName(String sallerName) {
        this.sallerName = sallerName;
    }
}

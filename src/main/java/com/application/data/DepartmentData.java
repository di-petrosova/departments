package com.application.data;

public class DepartmentData {
    private int PK;
    private int id;

    private String name;
    private int adress;

    public int getDepPK() {
        return PK;
    }

    public void setDepPK(int depPK) {
        this.PK = depPK;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdress() {
        return adress;
    }

    public void setAddress(int adress) {
        this.adress = adress;
    }
}

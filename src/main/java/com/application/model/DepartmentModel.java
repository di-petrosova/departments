package com.application.model;

import org.hibernate.annotations.Formula;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "departments")

public class DepartmentModel {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "PK", nullable = false, unique = true)
    private int pk;
    @Column(name = "name")
    private String name;
    @Column(name = "adress")
    private int address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "departmentId")
    private List<EmployeeModel> list;

    public DepartmentModel() {

    }

    public DepartmentModel(int id, int pk, String name, int address) {
        this.id = id;
        this.pk = pk;
        this.name = name;
        this.address = address;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

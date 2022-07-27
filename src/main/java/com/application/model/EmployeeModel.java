package com.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "employees")
public class EmployeeModel {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "dateBirth")
    private String dateBirth;
    @Column(name = "age")
    private int age;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "modifiedDate")
    private Date modifiedDate;
    @Column(name = "experience")
    private boolean experience;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private DepartmentModel departmentId;

    @ManyToOne
    @JoinColumn(name = "photo", columnDefinition = "longblob")
    private MediaModel photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isExperience() {
        return experience;
    }

    public void setExperience(boolean experience) {
        this.experience = experience;
    }

    public DepartmentModel getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(DepartmentModel departmentId) {
        this.departmentId = departmentId;
    }

    public MediaModel getPhoto() {
        return photo;
    }

    public void setPhoto(MediaModel photo) {
        this.photo = photo;
    }
}

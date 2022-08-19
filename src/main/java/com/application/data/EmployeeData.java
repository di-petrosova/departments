package com.application.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeData {
    @JsonProperty("firstName")
    private String empFirstName;
    @JsonProperty("lastName")
    private String empLastName;
    @JsonProperty("email")
    private String empEmail;

    public String getEmpFirstName() {
        return empFirstName;
    }

    public void setEmpFirstName(String empFirstName) {
        this.empFirstName = empFirstName;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public void setEmpLastName(String empLastName) {
        this.empLastName = empLastName;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }
}

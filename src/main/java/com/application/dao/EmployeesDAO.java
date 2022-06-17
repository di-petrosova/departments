package com.application.dao;

import javax.servlet.http.HttpServlet;
import java.sql.ResultSet;

public interface EmployeesDAO {
    ResultSet getAllEmployees();
}

package com.application.dao.factory;

import com.application.service.PropertyService;
import com.application.service.impl.DefaultPropertyService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory {
    private static PropertyService propertyService = new DefaultPropertyService();
    public static Connection establishConnection() throws SQLException
    {
        String url = propertyService.getString("URL");
        String user = propertyService.getString("USER");
        String password = propertyService.getString("PASSWORD");
//        String url = propertyService.getStringOrDefault("URL", "jdbc:mysql://localhost/newdep?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
//        String user = propertyService.getStringOrDefault("USER", "root");
//        String password = propertyService.getStringOrDefault("PASSWORD", "password");

        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        return DriverManager.getConnection(url, user, password);
    }
}

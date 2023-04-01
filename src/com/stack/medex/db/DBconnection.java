package com.stack.medex.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    // 1 private static reference var
    private static DBconnection dbconnection;
    private Connection connection;

    private DBconnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/medex",
                "root",
                "12345"
        );
    }

    public static DBconnection getInstance() throws SQLException, ClassNotFoundException {
      /*  if(dbconnection==null){
            dbconnection = new DBconnection();
        }
        return  dbconnection;*/

        return dbconnection ==null ? (dbconnection = new DBconnection()) : dbconnection;
    }

    public Connection getConnection(){
        return connection;
    }
}

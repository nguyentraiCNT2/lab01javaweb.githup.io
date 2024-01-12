package com.example.bai12;

import java.sql.Connection;
import java.sql.*;
public class ConnectionUtils {
    public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:sqlserver://DESKTOP-MOTUJCG\\SQLSERVER:1433;databaseName=CustomerManager";
        String user = "sa";
        String password = "1412";

//        // Đảm bảo driver được nạp vào bộ nhớ
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        Connection conn = DriverManager.getConnection(url, user, password);

        return conn;
    }
}

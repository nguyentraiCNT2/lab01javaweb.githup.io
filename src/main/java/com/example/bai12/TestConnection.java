package com.example.bai12;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        System.out.println("Get connection ... ");

        // Đảm bảo driver được nạp vào bộ nhớ


        // Lấy ra đối tượng Connection kết nối vào database.
        Connection conn = ConnectionUtils.getMySQLConnection();

        System.out.println("Get connection " + conn);
        System.out.println("Done!");
    }
}

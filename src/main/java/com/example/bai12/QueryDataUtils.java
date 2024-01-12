package com.example.bai12;
import java.sql.*;
public class QueryDataUtils {
    public static ResultSet getAll(String sql) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionUtils.getMySQLConnection();
// Tạo đối tượng Statement.
        Statement statement = connection.createStatement();
// Thực thi câu lệnh SQL trả về đối tượng ResultSet.
                ResultSet rs = statement.executeQuery(sql);
        return rs;
    }
    }


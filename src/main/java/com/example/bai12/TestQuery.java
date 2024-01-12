package com.example.bai12;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestQuery {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Đọc dữ liệu
        String sql = "SELECT * FROM Customer";
        ResultSet rs = QueryDataUtils.getAll(sql);

        // Duyệt trên kết quả trả về.
        while (rs.next()) {
            // Di chuyển con trỏ xuống bản ghi kế tiếp.
            int cusId = rs.getInt("CusID");
            String cusUser = rs.getString("CusUser");
            String cusName = rs.getString("CusName");
            String cusPhone = rs.getString("CusPhone");
            String cusAdd = rs.getString("CusAdd");
            String cusEmail = rs.getString("CusEmail");
            String cusFacebook = rs.getString("CusFacebook");
            String cusSkyper = rs.getString("CusSkyper");
            int cusStatus = rs.getInt("CusStatus");

            System.out.println("-----------------------------");
            System.out.printf("%-10s%-20s%-20s%-15s%-30s%-25s%-20s%-20s%-10s\n",
                    "CusID", "CusUser", "CusName", "CusPhone", "CusAdd", "CusEmail", "CusFacebook", "CusSkyper", "CusStatus");
            System.out.printf("%-10d%-20s%-20s%-15s%-30s%-25s%-20s%-20s%-10d\n",
                    cusId, cusUser, cusName, cusPhone, cusAdd, cusEmail, cusFacebook, cusSkyper, cusStatus);
        }

        // Đóng ResultSet
        rs.close();
    }
}

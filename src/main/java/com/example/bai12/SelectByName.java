package com.example.bai12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SelectByName {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Tạo kết nối
        Connection connection = ConnectionUtils.getMySQLConnection();

        // Sử dụng Scanner để nhập tên khách hàng từ bàn phím
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tên khách hàng muốn tìm kiếm: ");
        String inputName = scanner.nextLine();

        // Tạo truy vấn tìm kiếm
        String sql = "SELECT * FROM Customer WHERE CusName LIKE ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        // Gán giá trị cho biến tham số
        pstm.setString(1, "%" + inputName + "%");

        // Thực thi câu lệnh truy vấn
        ResultSet rs = pstm.executeQuery();

        // Duyệt trên kết quả trả về
        while (rs.next()) {
            // Lấy thông tin khách hàng từ ResultSet
            int cusID = rs.getInt("CusID");
            String cusUser = rs.getString("CusUser");
            String cusPass = rs.getString("CusPass");
            String cusName = rs.getString("CusName");
            String cusPhone = rs.getString("CusPhone");
            String cusAdd = rs.getString("CusAdd");
            String cusEmail = rs.getString("CusEmail");
            String cusFacebook = rs.getString("CusFacebook");
            String cusSkyper = rs.getString("CusSkyper");
            int cusStatus = rs.getInt("CusStatus");

            // Hiển thị thông tin khách hàng
            System.out.println("CusID: " + cusID);
            System.out.println("CusUser: " + cusUser);
            System.out.println("CusPass: " + cusPass);
            System.out.println("CusName: " + cusName);
            System.out.println("CusPhone: " + cusPhone);
            System.out.println("CusAdd: " + cusAdd);
            System.out.println("CusEmail: " + cusEmail);
            System.out.println("CusFacebook: " + cusFacebook);
            System.out.println("CusSkyper: " + cusSkyper);
            System.out.println("CusStatus: " + cusStatus);
            System.out.println("----------------------");
        }

        // Đóng kết nối và tài nguyên
        rs.close();
        pstm.close();
        connection.close();
    }
}

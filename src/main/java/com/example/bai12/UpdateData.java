package com.example.bai12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateData {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Tạo kết nối
        Connection connection = ConnectionUtils.getMySQLConnection();

        // Sử dụng Scanner để nhập giá trị từ bàn phím
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập thông tin khách hàng cập nhật:");
        System.out.print("CusID: ");
        int cusid = scanner.nextInt();

        System.out.print("CusUser: ");
        String cusUser = scanner.nextLine();
        scanner.nextLine();
        System.out.print("CusPass: ");
        String cusPass = scanner.nextLine();

        System.out.print("CusName: ");
        String cusName = scanner.nextLine();

        System.out.print("CusPhone: ");
        String cusPhone = scanner.nextLine();

        System.out.print("CusAdd: ");
        String cusAdd = scanner.nextLine();

        System.out.print("CusEmail: ");
        String cusEmail = scanner.nextLine();

        System.out.print("CusFacebook: ");
        String cusFacebook = scanner.nextLine();

        System.out.print("CusSkyper: ");
        String cusSkyper = scanner.nextLine();

        System.out.print("CusStatus: ");
        int cusStatus = scanner.nextInt();

        // Tạo truy vấn cập nhật
        String sql = "UPDATE Customer" +
                " SET CusUser = ?," +
                " CusPass = ?," +
                " CusName = ?," +
                " CusPhone = ?," +
                " CusAdd = ?," +
                " CusEmail = ?," +
                " CusFacebook = ?," +
                " CusSkyper = ?," +
                " CusStatus = ?" +
                " WHERE CusID = ? ";

        // Tạo đối tượng PreparedStatement
        PreparedStatement pstm = connection.prepareStatement(sql);

        // Gán giá trị cho các biến tham số
        pstm.setString(1, cusUser);
        pstm.setString(2, cusPass);
        pstm.setString(3, cusName);
        pstm.setString(4, cusPhone);
        pstm.setString(5, cusAdd);
        pstm.setString(6, cusEmail);
        pstm.setString(7, cusFacebook);
        pstm.setString(8, cusSkyper);
        pstm.setInt(9, cusStatus);
        pstm.setInt(10, cusid); // Đặt giá trị cho tham số WHERE (chú ý: CusID của bản ghi cần cập nhật)

        // Thực thi câu lệnh truy vấn
        int rowCount = pstm.executeUpdate();
        System.out.println("Đã cập nhật bản ghi: " + rowCount);

        // Đóng kết nối và tài nguyên
        pstm.close();
        connection.close();
    }
}

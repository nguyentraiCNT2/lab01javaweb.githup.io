package com.example.bai12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteData {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Tạo kết nối
        Connection connection = ConnectionUtils.getMySQLConnection();

        // Sử dụng Scanner để nhập giá trị từ bàn phím
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập CusID của bản ghi cần xóa: ");
        int cusIdToDelete = scanner.nextInt();

        // Tạo truy vấn xóa
        String sql = "DELETE FROM Customer WHERE CusID = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        // Gán giá trị cho biến tham số
        pstm.setInt(1, cusIdToDelete);

        // Thực thi câu lệnh truy vấn
        int rowCount = pstm.executeUpdate();
        System.out.println("Đã xóa bản ghi (CusID=" + cusIdToDelete + "): " + rowCount);

        // Đóng kết nối và tài nguyên
        pstm.close();
        connection.close();
    }
}

package com.example.bai12;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String url = "jdbc:sqlserver://DESKTOP-MOTUJCG\\SQLSERVER;databaseName=CustomerManager";
            String user = "sa";
            String password = "1412";

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(url, user, password);

                String action = request.getParameter("action");

                if (action == null) {
                    // Hiển thị danh sách khách hàng
                    displayCustomers(out, connection);
                } else if (action.equals("view")) {
                    // Hiển thị thông tin chi tiết của một khách hàng
                    int customerId = Integer.parseInt(request.getParameter("id"));
                    viewCustomer(out, connection, customerId);
                } else if (action.equals("edit")) {
                    // Hiển thị form chỉnh sửa cho một khách hàng
                    int customerId = Integer.parseInt(request.getParameter("id"));
                    editCustomerForm(out, connection, customerId);
                } else if (action.equals("update")) {
                    // Xử lý cập nhật thông tin khách hàng
                    updateCustomer(request, response, connection);
                } else if (action.equals("delete")) {
                    // Xử lý xóa khách hàng
                    int customerId = Integer.parseInt(request.getParameter("id"));
                    deleteCustomer(response, connection, customerId);
                } else if (action.equals("add")) {
                    // Hiển thị form thêm mới
                    addCustomerForm(out, connection);
                } else if (action.equals("insert")) {
                    // Xử lý thêm mới
                    insertCustomer(request, response, connection);
                }

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String url = "jdbc:sqlserver://DESKTOP-MOTUJCG\\SQLSERVER;databaseName=CustomerManager";
            String user = "sa";
            String password = "1412";

            try {
                Connection connection = DriverManager.getConnection(url, user, password);

                String action = request.getParameter("action");

                if ("update".equals(action)) {
                    updateCustomer(request, response, connection);
                } else if ("insert".equals(action)) {
                    insertCustomer(request, response, connection);
                }

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Các phương thức hỗ trợ

    private void displayCustomers(PrintWriter out, Connection connection) throws Exception {
        String sqlQuery = "SELECT * FROM Customer";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Hiển thị dữ liệu trong HTML
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Customer List</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Customer List</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>Customer ID</th><th>Customer User</th><th>Customer Name</th><th>Customer Phone</th><th>Customer Address</th><th>Customer Email</th><th>Customer Facebook</th><th>Customer Skype</th><th>Customer Status</th><th>Action</th></tr>");

            while (resultSet.next()) {
                int cusID = resultSet.getInt("CusID");
                String cusUser = resultSet.getString("CusUser");
                String cusName = resultSet.getString("CusName");
                String cusPhone = resultSet.getString("CusPhone");
                String cusAdd = resultSet.getString("CusAdd");
                String cusEmail = resultSet.getString("CusEmail");
                String cusFacebook = resultSet.getString("CusFacebook");
                String cusSkyper = resultSet.getString("CusSkyper");
                int cusStatus = resultSet.getInt("CusStatus");

                out.println("<tr><td>" + cusID + "</td><td>" + cusUser + "</td><td>" + cusName + "</td><td>" + cusPhone +
                        "</td><td>" + cusAdd + "</td><td>" + cusEmail + "</td><td>" + cusFacebook + "</td><td>" + cusSkyper +
                        "</td><td>" + cusStatus + "</td>" +
                        "<td><a href='CustomerServlet?action=view&id=" + cusID + "'>View</a> | " +
                        "<a href='CustomerServlet?action=edit&id=" + cusID + "'>Edit</a> | " +
                        "<a href='CustomerServlet?action=delete&id=" + cusID + "'>Delete</a></td></tr>");
            }

            out.println("</table>");
            out.println("<p><a href='CustomerServlet?action=add'>Add Customer</a></p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void viewCustomer(PrintWriter out, Connection connection, int customerId) throws Exception {
        String sqlQuery = "SELECT * FROM Customer WHERE CusID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Customer Details</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Customer Details</h1>");

                if (resultSet.next()) {
                    int cusID = resultSet.getInt("CusID");
                    String cusUser = resultSet.getString("CusUser");
                    String cusName = resultSet.getString("CusName");
                    String cusPhone = resultSet.getString("CusPhone");
                    String cusAdd = resultSet.getString("CusAdd");
                    String cusEmail = resultSet.getString("CusEmail");
                    String cusFacebook = resultSet.getString("CusFacebook");
                    String cusSkyper = resultSet.getString("CusSkyper");
                    int cusStatus = resultSet.getInt("CusStatus");

                    out.println("<p><strong>Customer ID:</strong> " + cusID + "</p>");
                    out.println("<p><strong>Customer User:</strong> " + cusUser + "</p>");
                    out.println("<p><strong>Customer Name:</strong> " + cusName + "</p>");
                    out.println("<p><strong>Customer Phone:</strong> " + cusPhone + "</p>");
                    out.println("<p><strong>Customer Address:</strong> " + cusAdd + "</p>");
                    out.println("<p><strong>Customer Email:</strong> " + cusEmail + "</p>");
                    out.println("<p><strong>Customer Facebook:</strong> " + cusFacebook + "</p>");
                    out.println("<p><strong>Customer Skype:</strong> " + cusSkyper + "</p>");
                    out.println("<p><strong>Customer Status:</strong> " + cusStatus + "</p>");
                } else {
                    out.println("<p>Customer not found.</p>");
                }

                out.println("<p><a href='CustomerServlet'>Back to Customer List</a></p>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    private void editCustomerForm(PrintWriter out, Connection connection, int customerId) throws Exception {
        String sqlQuery = "SELECT * FROM Customer WHERE CusID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Edit Customer</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Edit Customer</h1>");

                if (resultSet.next()) {
                    int cusID = resultSet.getInt("CusID");
                    String cusUser = resultSet.getString("CusUser");
                    String cusName = resultSet.getString("CusName");
                    String cusPhone = resultSet.getString("CusPhone");
                    String cusAdd = resultSet.getString("CusAdd");
                    String cusEmail = resultSet.getString("CusEmail");
                    String cusFacebook = resultSet.getString("CusFacebook");
                    String cusSkyper = resultSet.getString("CusSkyper");
                    int cusStatus = resultSet.getInt("CusStatus");

                    // Hiển thị form chỉnh sửa với các giá trị hiện tại của khách hàng
                    out.println("<form action='CustomerServlet?action=update' method='post'>");
                    out.println("<input type='hidden' name='id' value='" + cusID + "'>");
                    out.println("<p><label>Customer User:</label> <input type='text' name='user' value='" + cusUser + "'></p>");
                    out.println("<p><label>Customer Name:</label> <input type='text' name='name' value='" + cusName + "'></p>");
                    out.println("<p><label>Customer Phone:</label> <input type='text' name='phone' value='" + cusPhone + "'></p>");
                    out.println("<p><label>Customer Address:</label> <input type='text' name='address' value='" + cusAdd + "'></p>");
                    out.println("<p><label>Customer Email:</label> <input type='text' name='email' value='" + cusEmail + "'></p>");
                    out.println("<p><label>Customer Facebook:</label> <input type='text' name='facebook' value='" + cusFacebook + "'></p>");
                    out.println("<p><label>Customer Skype:</label> <input type='text' name='skype' value='" + cusSkyper + "'></p>");
                    out.println("<p><label>Customer Status:</label> <input type='text' name='status' value='" + cusStatus + "'></p>");
                    out.println("<p><input type='submit' value='Update'></p>");
                    out.println("</form>");
                } else {
                    out.println("<p>Customer not found.</p>");
                }

                out.println("<p><a href='CustomerServlet'>Back to Customer List</a></p>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    private void addCustomerForm(PrintWriter out, Connection connection) throws Exception {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Add Customer</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Add Customer</h1>");

        // Hiển thị form thêm mới
        out.println("<form action='CustomerServlet?action=insert' method='post'>");
        out.println("<p><label>Customer User:</label> <input type='text' name='user'></p>");
        out.println("<p><label>Customer Name:</label> <input type='text' name='name' value=''></p>");
        out.println("<p><label>Customer Phone:</label> <input type='text' name='phone'></p>");
        out.println("<p><label>Customer Address:</label> <input type='text' name='address'></p>");
        out.println("<p><label>Customer Email:</label> <input type='text' name='email'></p>");
        out.println("<p><label>Customer Facebook:</label> <input type='text' name='facebook'></p>");
        out.println("<p><label>Customer Skype:</label> <input type='text' name='skype'></p>");
        out.println("<p><label>Customer Status:</label> <input type='text' name='status'></p>");
        out.println("<p><input type='submit' value='Add'></p>");
        out.println("</form>");

        out.println("<p><a href='CustomerServlet'>Back to Customer List</a></p>");
        out.println("</body>");
        out.println("</html>");
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        int customerId = Integer.parseInt(request.getParameter("id"));
        String cusUser = request.getParameter("user");
        String cusName = request.getParameter("name");
        String cusPhone = request.getParameter("phone");
        String cusAdd = request.getParameter("address");
        String cusEmail = request.getParameter("email");
        String cusFacebook = request.getParameter("facebook");
        String cusSkyper = request.getParameter("skype");
        int cusStatus = Integer.parseInt(request.getParameter("status"));

        String sqlQuery = "UPDATE Customer SET CusUser=?, CusName=?, CusPhone=?, CusAdd=?, CusEmail=?, CusFacebook=?, CusSkyper=?, CusStatus=? WHERE CusID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, cusUser);
            preparedStatement.setString(2, cusName);
            preparedStatement.setString(3, cusPhone);
            preparedStatement.setString(4, cusAdd);
            preparedStatement.setString(5, cusEmail);
            preparedStatement.setString(6, cusFacebook);
            preparedStatement.setString(7, cusSkyper);
            preparedStatement.setInt(8, cusStatus);
            preparedStatement.setInt(9, customerId);

            preparedStatement.executeUpdate();
        }

        response.sendRedirect("CustomerServlet");
    }

    private void insertCustomer(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        String cusUser = request.getParameter("user");
        String cusName = request.getParameter("name");
        String cusPhone = request.getParameter("phone");
        String cusAdd = request.getParameter("address");
        String cusEmail = request.getParameter("email");
        String cusFacebook = request.getParameter("facebook");
        String cusSkyper = request.getParameter("skype");
        int cusStatus = Integer.parseInt(request.getParameter("status"));

        String sqlQuery = "INSERT INTO Customer (CusUser, CusName, CusPhone, CusAdd, CusEmail, CusFacebook, CusSkyper, CusStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, cusUser);
            preparedStatement.setString(2, cusName);
            preparedStatement.setString(3, cusPhone);
            preparedStatement.setString(4, cusAdd);
            preparedStatement.setString(5, cusEmail);
            preparedStatement.setString(6, cusFacebook);
            preparedStatement.setString(7, cusSkyper);
            preparedStatement.setInt(8, cusStatus);

            preparedStatement.executeUpdate();
        }

        response.sendRedirect("CustomerServlet");
    }

    private void deleteCustomer(HttpServletResponse response, Connection connection, int customerId) throws Exception {
        String sqlQuery = "DELETE FROM Customer WHERE CusID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        }

        response.sendRedirect("CustomerServlet");
    }
}


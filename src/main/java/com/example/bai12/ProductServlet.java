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

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String url = "jdbc:sqlserver://DESKTOP-MOTUJCG\\SQLSERVER;databaseName=productSample";
            String user = "sa";
            String password = "1412";

            try {Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                Connection connection = DriverManager.getConnection(url, user, password);

                String action = request.getParameter("action");

                if (action == null) {
                    // Truy vấn dữ liệu từ bảng "product" và hiển thị
                    displayProducts(out, connection);
                } else if (action.equals("view")) {
                    // Hiển thị thông tin chi tiết của một sản phẩm
                    int productId = Integer.parseInt(request.getParameter("id"));
                    viewProduct(out, connection, productId);
                } else if (action.equals("edit")) {
                    // Hiển thị form chỉnh sửa cho một sản phẩm
                    int productId = Integer.parseInt(request.getParameter("id"));
                    editProductForm(out, connection, productId);
                } else if (action.equals("update")) {
                    // Xử lý cập nhật thông tin sản phẩm
                    updateProduct(request, response, connection);
                } else if (action.equals("delete")) {
                    // Xử lý xóa sản phẩm
                    int productId = Integer.parseInt(request.getParameter("id"));
                    deleteProduct(response, connection, productId);
                } else if (action.equals("add")) {
                    // Hiển thị form thêm mới
                    addProductForm(out, connection);
                } else if (action.equals("insert")) {
                    // Xử lý thêm mới
                    insertProduct(request, response, connection);
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
            String url = "jdbc:sqlserver://DESKTOP-MOTUJCG\\SQLSERVER;databaseName=productSample";
            String user = "sa";
            String password = "1412";

            try {
                Connection connection = DriverManager.getConnection(url, user, password);

                String action = request.getParameter("action");

                if ("update".equals(action)) {
                    updateProduct(request, response, connection);
                } else if ("insert".equals(action)) {
                    insertProduct(request, response, connection);
                }

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Các phương thức hỗ trợ

    private void displayProducts(PrintWriter out, Connection connection) throws Exception {
        String sqlQuery = "SELECT * FROM product";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Hiển thị dữ liệu trong HTML
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Product List</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Product List</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>Product ID</th><th>Product Name</th><th>Product Description</th><th>Product Date</th><th>Product Quantity</th><th>Product Price</th><th>Product Image</th><th>Product Status</th><th>Product Warranty</th><th>Category ID</th><th>Action</th></tr>");

            while (resultSet.next()) {
                int proID = resultSet.getInt("ProID");
                String proName = resultSet.getString("ProName");
                String proDesc = resultSet.getString("ProDesc");
                String proDate = resultSet.getString("ProDate");
                int proQty = resultSet.getInt("ProQty");
                double proPrice = resultSet.getDouble("ProPrice");
                String proImage = resultSet.getString("ProImage");
                int proStatus = resultSet.getInt("ProStatus");
                int proWarranty = resultSet.getInt("ProWarranty");
                int cateID = resultSet.getInt("CateID");

                out.println("<tr><td>" + proID + "</td><td>" + proName + "</td><td>" + proDesc + "</td><td>" + proDate +
                        "</td><td>" + proQty + "</td><td>" + proPrice + "</td><td>" + proImage + "</td><td>" + proStatus +
                        "</td><td>" + proWarranty + "</td><td>" + cateID + "</td>" +
                        "<td><a href='ProductServlet?action=view&id=" + proID + "'>View</a> | " +
                        "<a href='ProductServlet?action=edit&id=" + proID + "'>Edit</a> | " +
                        "<a href='ProductServlet?action=delete&id=" + proID + "'>Delete</a></td></tr>");
            }

            out.println("</table>");
            out.println("<p><a href='ProductServlet?action=add'>Add Product</a></p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void viewProduct(PrintWriter out, Connection connection, int productId) throws Exception {
        String sqlQuery = "SELECT * FROM product WHERE ProID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, productId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Product Details</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Product Details</h1>");

                if (resultSet.next()) {
                    int proID = resultSet.getInt("ProID");
                    String proName = resultSet.getString("ProName");
                    String proDesc = resultSet.getString("ProDesc");
                    String proDate = resultSet.getString("ProDate");
                    int proQty = resultSet.getInt("ProQty");
                    double proPrice = resultSet.getDouble("ProPrice");
                    String proImage = resultSet.getString("ProImage");
                    int proStatus = resultSet.getInt("ProStatus");
                    int proWarranty = resultSet.getInt("ProWarranty");
                    int cateID = resultSet.getInt("CateID");

                    out.println("<p><strong>Product ID:</strong> " + proID + "</p>");
                    out.println("<p><strong>Product Name:</strong> " + proName + "</p>");
                    out.println("<p><strong>Product Description:</strong> " + proDesc + "</p>");
                    out.println("<p><strong>Product Date:</strong> " + proDate + "</p>");
                    out.println("<p><strong>Product Quantity:</strong> " + proQty + "</p>");
                    out.println("<p><strong>Product Price:</strong> " + proPrice + "</p>");
                    out.println("<p><strong>Product Image:</strong> " + proImage + "</p>");
                    out.println("<p><strong>Product Status:</strong> " + proStatus + "</p>");
                    out.println("<p><strong>Product Warranty:</strong> " + proWarranty + "</p>");
                    out.println("<p><strong>Category ID:</strong> " + cateID + "</p>");
                } else {
                    out.println("<p>Product not found.</p>");
                }

                out.println("<p><a href='ProductServlet'>Back to Product List</a></p>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    private void editProductForm(PrintWriter out, Connection connection, int productId) throws Exception {
        String sqlQuery = "SELECT * FROM product WHERE ProID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, productId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Edit Product</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Edit Product</h1>");

                if (resultSet.next()) {
                    int proID = resultSet.getInt("ProID");
                    String proName = resultSet.getString("ProName");
                    String proDesc = resultSet.getString("ProDesc");
                    String proDate = resultSet.getString("ProDate");
                    int proQty = resultSet.getInt("ProQty");
                    double proPrice = resultSet.getDouble("ProPrice");
                    String proImage = resultSet.getString("ProImage");
                    int proStatus = resultSet.getInt("ProStatus");
                    int proWarranty = resultSet.getInt("ProWarranty");
                    int cateID = resultSet.getInt("CateID");

                    // Hiển thị form chỉnh sửa với các giá trị hiện tại của sản phẩm
                    out.println("<form action='ProductServlet?action=update' method='post'>");
                    out.println("<input type='hidden' name='id' value='" + proID + "'>");
                    out.println("<p><label>Product Name:</label> <input type='text' name='name' value='" + proName + "'></p>");
                    out.println("<p><label>Product Description:</label> <input type='text' name='desc' value='" + proDesc + "'></p>");
                    out.println("<p><label>Product Date:</label> <input type='text' name='date' value='" + proDate + "'></p>");
                    out.println("<p><label>Product Quantity:</label> <input type='text' name='qty' value='" + proQty + "'></p>");
                    out.println("<p><label>Product Price:</label> <input type='text' name='price' value='" + proPrice + "'></p>");
                    out.println("<p><label>Product Image:</label> <input type='text' name='image' value='" + proImage + "'></p>");
                    out.println("<p><label>Product Status:</label> <input type='text' name='status' value='" + proStatus + "'></p>");
                    out.println("<p><label>Product Warranty:</label> <input type='text' name='warranty' value='" + proWarranty + "'></p>");
                    out.println("<p><label>Category ID:</label> <input type='text' name='categoryId' value='" + cateID + "'></p>");
                    out.println("<p><input type='submit' value='Update'></p>");
                    out.println("</form>");
                } else {
                    out.println("<p>Product not found.</p>");
                }

                out.println("<p><a href='ProductServlet'>Back to Product List</a></p>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    private void addProductForm(PrintWriter out, Connection connection) throws Exception {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Add Product</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Add Product</h1>");

        // Hiển thị form thêm mới
        out.println("<form action='ProductServlet?action=insert' method='post'>");
        out.println("<p><label>Product Name:</label> <input type='text' name='name'></p>");
        out.println("<p><label>Product Description:</label> <input type='text' name='desc'></p>");
        out.println("<p><label>Product Date:</label> <input type='text' name='date'></p>");
        out.println("<p><label>Product Quantity:</label> <input type='text' name='qty'></p>");
        out.println("<p><label>Product Price:</label> <input type='text' name='price'></p>");
        out.println("<p><label>Product Image:</label> <input type='text' name='image'></p>");
        out.println("<p><label>Product Status:</label> <input type='text' name='status'></p>");
        out.println("<p><label>Product Warranty:</label> <input type='text' name='warranty'></p>");
        out.println("<p><label>Category ID:</label> <input type='text' name='categoryId'></p>");
        out.println("<p><input type='submit' value='Add'></p>");
        out.println("</form>");

        out.println("<p><a href='ProductServlet'>Back to Product List</a></p>");
        out.println("</body>");
        out.println("</html>");
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        // Lấy thông tin từ request
        String proName = request.getParameter("name");
        String proDesc = request.getParameter("desc");
        String proDate = request.getParameter("date");
        int proQty = Integer.parseInt(request.getParameter("qty"));
        double proPrice = Double.parseDouble(request.getParameter("price"));
        String proImage = request.getParameter("image");
        int proStatus = Integer.parseInt(request.getParameter("status"));
        int proWarranty = Integer.parseInt(request.getParameter("warranty"));
        int cateID = Integer.parseInt(request.getParameter("categoryId"));

        // Thực hiện câu lệnh SQL INSERT
        String insertQuery = "INSERT INTO product (ProName, ProDesc, ProDate, ProQty, ProPrice, ProImage, ProStatus, ProWarranty, CateID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, proName);
            preparedStatement.setString(2, proDesc);
            preparedStatement.setString(3, proDate);
            preparedStatement.setInt(4, proQty);
            preparedStatement.setDouble(5, proPrice);
            preparedStatement.setString(6, proImage);
            preparedStatement.setInt(7, proStatus);
            preparedStatement.setInt(8, proWarranty);
            preparedStatement.setInt(9, cateID);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                response.sendRedirect("ProductServlet");
            } else {
                response.getWriter().println("Insert failed.");
            }
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        // Lấy thông tin từ request
        int productId = Integer.parseInt(request.getParameter("id"));
        String proName = request.getParameter("name");
        String proDesc = request.getParameter("desc");
        String proDate = request.getParameter("date");
        int proQty = Integer.parseInt(request.getParameter("qty"));
        double proPrice = Double.parseDouble(request.getParameter("price"));
        String proImage = request.getParameter("image");
        int proStatus = Integer.parseInt(request.getParameter("status"));
        int proWarranty = Integer.parseInt(request.getParameter("warranty"));
        int cateID = Integer.parseInt(request.getParameter("categoryId"));

        // Thực hiện câu lệnh SQL UPDATE
        String updateQuery = "UPDATE product SET ProName=?, ProDesc=?, ProDate=?, ProQty=?, ProPrice=?, ProImage=?, ProStatus=?, ProWarranty=?, CateID=? WHERE ProID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, proName);
            preparedStatement.setString(2, proDesc);
            preparedStatement.setString(3, proDate);
            preparedStatement.setInt(4, proQty);
            preparedStatement.setDouble(5, proPrice);
            preparedStatement.setString(6, proImage);
            preparedStatement.setInt(7, proStatus);
            preparedStatement.setInt(8, proWarranty);
            preparedStatement.setInt(9, cateID);
            preparedStatement.setInt(10, productId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                response.sendRedirect("ProductServlet");
            } else {
                response.getWriter().println("Update failed.");
            }
        }
    }

    private void deleteProduct(HttpServletResponse response, Connection connection, int productId) throws Exception {
        // Thực hiện câu lệnh SQL DELETE
        String deleteQuery = "DELETE FROM product WHERE ProID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, productId);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                response.sendRedirect("ProductServlet");
            } else {
                response.getWriter().println("Delete failed.");
            }
        }
    }
}


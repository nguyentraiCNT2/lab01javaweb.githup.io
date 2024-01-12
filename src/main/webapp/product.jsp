<%--
  Created by IntelliJ IDEA.
  User: ninhn
  Date: 1/12/2024
  Time: 2:25 PM
  To change this template use File | Settings | File Templates.
--%><%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
</head>
<body>
<h1>Product List</h1>
<table border="1">
    <tr>
        <th>Product ID</th>
        <th>Product Name</th>
        <th>Product Description</th>
        <th>Product Date</th>
        <th>Product Quantity</th>
        <th>Product Price</th>
        <th>Product Image</th>
        <th>Product Status</th>
        <th>Product Warranty</th>
        <th>Category ID</th>
        <th>Action</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.proID}</td>
            <td>${product.proName}</td>
            <td>${product.proDesc}</td>
            <td>${product.proDate}</td>
            <td>${product.proQty}</td>
            <td>${product.proPrice}</td>
            <td>${product.proImage}</td>
            <td>${product.proStatus}</td>
            <td>${product.proWarranty}</td>
            <td>${product.cateID}</td>
            <td>
                <a href='ProductServlet?action=view&id=${product.proID}'>View</a> |
                <a href='ProductServlet?action=edit&id=${product.proID}'>Edit</a> |
                <a href='ProductServlet?action=delete&id=${product.proID}'>Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br/>
<a href="ProductServlet?action=add">Add Product</a>
</body>
</html>

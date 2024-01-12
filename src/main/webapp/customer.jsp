<%--
  Created by IntelliJ IDEA.
  User: ninhn
  Date: 1/12/2024
  Time: 4:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Management</title>
</head>
<body>

<h1>Customer List</h1>

<table border="1">
    <tr>
        <th>Customer ID</th>
        <th>Username</th>
        <th>Name</th>
        <th>Phone</th>
        <th>Address</th>
        <th>Email</th>
        <th>Facebook</th>
        <th>Skype</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <%-- Loop through the list of customers and display each row --%>
    <c:forEach var="customer" items="${customerList}">
        <tr>
            <td>${customer.cusID}</td>
            <td>${customer.cusUser}</td>
            <td>${customer.cusName}</td>
            <td>${customer.cusPhone}</td>
            <td>${customer.cusAdd}</td>
            <td>${customer.cusEmail}</td>
            <td>${customer.cusFacebook}</td>
            <td>${customer.cusSkyper}</td>
            <td>${customer.cusStatus}</td>
            <td>
                <a href="CustomerServlet?action=view&id=${customer.cusID}">View</a> |
                <a href="CustomerServlet?action=edit&id=${customer.cusID}">Edit</a> |
                <a href="CustomerServlet?action=delete&id=${customer.cusID}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<p><a href="CustomerServlet?action=add">Add Customer</a></p>

</body>
</html>

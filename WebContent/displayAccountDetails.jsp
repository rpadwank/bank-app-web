<%@page import="com.capgemini.bankapp.model.BankAccount"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Account List</h3>

	<%-- <p style="color: red;">${errorString}</p> --%>
	<%BankAccount account = (BankAccount)request.getAttribute("accountDetails"); %>
	<table border="1">
		<tr>
			<th>Account Id</th>
			<th>Name</th>
			<th>Type</th>
			<th>Balance</th>
		</tr>
		<%-- <c:forEach items="${accountsList}" var="account"> --%>
			<tr>
				<td><% out.print(account.getAccountId()); %></td>
				<td><% out.print(account.getAccountHolderName()); %></td>
				<td><% out.print(account.getAccountType()); %></td>
				<td><% out.print(account.getAccountBalance()); %></td>
				<%-- <td>${accountDetails.accountHolderName}</td>
				<td>${accountDetails.accountType}</td>
				<td>${accountDetails.accountBalance}</td>
			</c:forEach> --%>
			
		</table>
</body>
</html>
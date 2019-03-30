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


	<table border="1">
		<tr>
			<th>Account Id</th>
			<th>Name</th>
			<th>Type</th>
			<th>Balance</th>
		</tr>
		<c:forEach items="${accountsList}" var="account">
			<tr>
				<td>${account.accountId}</td>
				<td>${account.accountHolderName}</td>
				<td>${account.accountType}</td>
				<td>${account.accountBalance}</td>
		</c:forEach>
	</table>
</body>
</html>
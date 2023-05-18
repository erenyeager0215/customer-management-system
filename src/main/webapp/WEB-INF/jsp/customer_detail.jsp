<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="dto.Customer" %>
<%@ page import="java.util.*" %>
<% List<Customer> specific_customer_data = (List<Customer>)request.getAttribute("specific_customer_data");%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<title>顧客情報編集画面</title>
</head>
<body>
	<div class="mx-auto" style="width: 300px;">
		<h1 class="mb-3" style="text-align: center">顧客情報編集画面</h1>
		<form action="<%= request.getContextPath() %>/CustomerDetailServlet?id=<%= specific_customer_data.get(0).getId() %>" method="post">
		  <div class="mb-3">
		    <label for="userName" class="form-label">顧客名</label>
		    <input type="text" class="form-control" id="customerName" name="customer_name" value="<%=specific_customer_data.get(0).getName() %>">
		  </div>
		  <div class="mb-3">
		    <label for="address" class="form-label">住所</label>
		    <input type="text" class="form-control" id="adderss" name="customer_address" value="<%=specific_customer_data.get(0).getAddress() %>">
		  </div>
		  <div class="mb-3">
		    <label for="tel" class="form-label">電話番号</label>
		    <input type="text" class="form-control" id="tel" name="customer_tel" value="<%=specific_customer_data.get(0).getTel_number()  %>">
		  </div>
		  <button type="submit" class="btn btn-primary">更新</button>
		</form>
	</div>
</body>
</html>
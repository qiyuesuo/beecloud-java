<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>JD WAP Return Url</title>
</head>
<body>
	<%
		String token=(String)request.getParameter("token");
		String tradeNum=(String)request.getParameter("tradeNum").trim();
		
		if(token != null) {
			out.println("<h3>京东移动网页支付成功，商户应自行实现成功逻辑！</h3>");
			out.println("trade no:" + tradeNum);
			//handle othre return parameter as you wish
			return;
		} else {
			out.println("<h3>京东移动网页支付未成功，商户应自行实现失败逻辑！</h3>");
		}
	%>
</body>
</html>
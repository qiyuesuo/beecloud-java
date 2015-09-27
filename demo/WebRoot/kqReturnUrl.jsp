<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>KUAIQIAN Return Url</title>
</head>
<body>
	<%
		String orderId=(String)request.getParameter("orderId").trim();
		String orderAmount=(String)request.getParameter("orderAmount").trim();
		String dealId=(String)request.getParameter("dealId").trim();
		String orderTime=(String)request.getParameter("orderTime").trim();
		String payResult=(String)request.getParameter("payResult").trim();
		
		if(payResult.equals("10")) {
			out.println("<h3>快钱支付成功，商户应自行实现成功逻辑！</h3>");
			out.println("order no:" + orderId);
			//handle othre return parameter as you wish
			return;
		} else {
			out.println("<h3>快钱支付未成功，商户应自行实现失败逻辑！</h3>");
		}
	%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>JD Return Url</title>
</head>
<body>
	<%
		String tradeNum=(String)request.getParameter("tradeNum").trim();
		String tradeAmount=(String)request.getParameter("tradeAmount").trim();
		String tradeStatus=(String)request.getParameter("tradeStatus").trim();
		
		
		if(tradeStatus.equals("0")) {
			out.println("京东支付成功，商户应自行实现成功逻辑！");
			out.println("trade no:" + tradeNum);
			//handle othre return parameter as you wish
			return;
		}
	%>
</body>
</html>
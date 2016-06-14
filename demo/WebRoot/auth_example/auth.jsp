<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.bean.*"%>
<%@ page import="cn.beecloud.*"%>
<%
    /*
	     功能：鉴权操作
	     版本：1.0
	     日期：2016-06-13
	     说明：
	     以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	     该代码仅供学习和研究使用，只是提供一个参考。

     //***********页面功能说明***********
    	 该页面可以在本机电脑测试。
     //********************************
     */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; UTF-8">
    <title>transfer</title>
</head>
<body>

<%
	String name = "冯晓波";
	String idNo = "320504192306171022";
	String cardNo = "6114335124826228";
	String mobile = "13761231321";
	BCAuth auth = new BCAuth(name, idNo, cardNo);
	auth.setMobile(mobile);

	try {
		auth = BCPay.startBCAuth(auth);
		out.println("鉴权成功！");
		out.println(auth.getAuthMsg());
		out.println(auth.getCardId());
		out.println(auth.isAuthResult());

	} catch (BCException e) {
			out.println(e.getMessage());
	}
%>
<%@page import="cn.beecloud.bean.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="cn.beecloud.BCEumeration.PAY_CHANNEL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Arrays"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<link href="demo.css" rel="stylesheet" type="text/css"/>
<title>Batch</title>
<script type="text/javascript">
	function confirm(url) {
		window.location.href=url;
	}
</script>
</head>
<body>
<%
	String[] ids= request.getParameterValues("id");
	if(ids == null) {
		out.println("请选择预退款记录!");
		return;
	}
	System.out.println(ids);
	String channel = request.getParameter("channel");
	String isYeeWap = request.getParameter("isYeeWap");
	System.out.println("channel" + channel);
	System.out.println("isYeeWap " + isYeeWap);
	Object agree = request.getParameter("agree");
	if (isYeeWap.equals("1")) {
		BeeCloud.registerApp("230b89e6-d7ff-46bb-b0b6-032f8de7c5d0", "191418f6-c0f5-4943-8171-d07bfeff46b0");
	}
	if (agree !=null) {
		BCPayResult result = BCPay.startBatchRefund(Arrays.asList(ids), channel, true);
		if (isYeeWap.equals("1")) {
			BeeCloud.registerApp("c37d661d-7e61-49ea-96a5-68c34e83db3b", "c37d661d-7e61-49ea-96a5-68c34e83db3b");
		}
		if (result.getType().ordinal() == 0) {
			Thread.sleep(5000);
			out.println("<div>");
			for (BCPayResult br : result.getResultList()) {
				String line = br.getObjectId() + " : ";
				System.out.println("get type:" + br.getType());
				if(br.getErrMsg().equals("OK")) {
					line += "处理成功";
				} else {
					line += br.getErrMsg();
				}
				out.println(line + "<br/>");
			}
			if (channel.equals("ALI"))
				out.println("</div><br/><div style=\"clear: both;\"><input onclick=\"confirm('" + result.getUrl() + "')\" name=\"confirm\" type=\"submit\" class=\"button\" value=\"确认\"></div>");
			else 
				out.println("</div><br/><div style=\"clear: both;\"><input onclick='javascript:window.close();' class=\"button\" value=\"确认\"></div>");
				
		}
		else {
			//handle the error message as you wish！
			out.println(result.getErrMsg());
			out.println(result.getErrDetail());
		}
	} else {
		System.out.println(channel);
		BCPayResult result = BCPay.startBatchRefund(Arrays.asList(ids), channel, false);
		if (isYeeWap.equals("1")) {
			BeeCloud.registerApp("c37d661d-7e61-49ea-96a5-68c34e83db3b", "c37d661d-7e61-49ea-96a5-68c34e83db3b");
		}
		if (result.getType().ordinal() == 0) {
			out.println("<h3>批量驳回成功!</h3>");
			out.println("<br><br><div style=\"clear: both;\"><input onclick='javascript:window.close();' class=\"button\" value=\"确认\"></div>");
		}
		else {
			//handle the error message as you wish！
			out.println(result.getErrMsg());
			out.println(result.getErrDetail());
		}
	}
	
	
%>
</body>
</html>

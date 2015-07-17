<%@page import="cn.beecloud.BCEumeration.PAY_CHANNEL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Start Refund</title>
<script type="text/javascript">
</script>
</head>
<body>
<%
	String bill_no = request.getParameter("bill_no");
	String channelString = request.getParameter("channel");
	Integer refund_fee = Integer.parseInt(request.getParameter("total_fee"));
	PAY_CHANNEL channel = channelString.equals("WX")?PAY_CHANNEL.WX:channelString.equals("ALI")?PAY_CHANNEL.ALI:PAY_CHANNEL.UN;
	String refund_no = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
	BCPayResult result = BCPay.startBCRefund(channel, refund_no, bill_no, refund_fee, null);
	if (result.getType().ordinal() == 0 ) {
		if (channel.equals(PAY_CHANNEL.WX)) {
			out.println(result.getSucessMsg());
		} else if (channel.equals(PAY_CHANNEL.ALI)) {
			response.sendRedirect(result.getUrl());
		} else if (channel.equals(PAY_CHANNEL.UN)) {
			out.println(result.getSucessMsg());
		}
	} else {
		out.println(result.getErrMsg());
		out.println(result.getErr_detail());
	}
%>
</body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.*" %>
<%@ page import="cn.beecloud.BCEumeration.*" %>
<%@ page import="cn.beecloud.bean.*" %>
<%@ page import="org.apache.log4j.Logger" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; UTF-8">
    <title>Refund Update</title>
    <script type="text/javascript">
    </script>
</head>
<body>
<%
	Logger log = Logger.getLogger("refundUpdate.jsp");

    String channelString = request.getParameter("channel");
    String refund_no = request.getParameter("refund_no");
    PAY_CHANNEL channel;
    try {
    	channel = PAY_CHANNEL.valueOf(channelString.split("_")[0]);
    } catch (Exception ex) {
    	channel = null;
        log.error(ex.getMessage(), ex);
    }
   	if (channel.equals(PAY_CHANNEL.YEE_WAP)) {
       BeeCloud.registerApp("230b89e6-d7ff-46bb-b0b6-032f8de7c5d0", "191418f6-c0f5-4943-8171-d07bfeff46b0");
    }
    try {
	    String result = BCPay.startRefundUpdate(channel, refund_no);
	    if (channel.equals(PAY_CHANNEL.YEE_WAP)) {
	        BeeCloud.registerApp("c37d661d-7e61-49ea-96a5-68c34e83db3b", "c37d661d-7e61-49ea-96a5-68c34e83db3b");
	    }
        out.println(result);
    } catch(BCException ex) {
    	out.println(ex.getMessage());
    	log.info(ex.getMessage());
    }
%>
</body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.*" %>
<%@ page import="cn.beecloud.BCEumeration.*" %>
<%@ page import="cn.beecloud.bean.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%
   /*
	   功能：退款状态更新
	   版本：1.0
	   日期：2015-11-21
	   说明：退款状态更新处理页面， 用于查询最新的退款状态，使用于微信、易宝、百度、快钱四大渠道
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
    try {
	    String result = BCPay.startRefundUpdate(channel, refund_no);
        out.println(result);
    } catch(BCException ex) {
    	out.println(ex.getMessage());
    	log.info(ex.getMessage());
    }
%>
</body>
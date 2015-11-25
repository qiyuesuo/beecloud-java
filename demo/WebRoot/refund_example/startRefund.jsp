<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.BCEumeration.PAY_CHANNEL" %>
<%@ page import="cn.beecloud.*" %>
<%@ page import="cn.beecloud.bean.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@page import="org.apache.log4j.*" %>

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
    Logger log = Logger.getLogger("startRefund.jsp");
	String refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
	Map<String, Object> optional = new HashMap<String, Object>();
    optional.put("test", "test");
    
    String isYeeWap = request.getParameter("isYeeWap");
    String billNo = request.getParameter("bill_no");
    String channelString = request.getParameter("channel");
    String prefund = request.getParameter("prefund");
    Integer refundFee = Integer.parseInt(request.getParameter("total_fee"));
    
    PAY_CHANNEL channel = null;
    if (channelString != null && !channelString.equals("")) {
    	try {
        	channel = PAY_CHANNEL.valueOf(channelString.split("_")[0]);
    	} catch (Exception ex) {
    		channel = null;
            log.error(ex.getMessage(), ex);
    	}
    }
    
    BCRefund param = new BCRefund(billNo, refundNo, 1);
    param.setOptional(optional);
    param.setNeedApproval(prefund.equals("true")?true:false);
    if (isYeeWap.equals("1")) {
        BeeCloud.registerApp("230b89e6-d7ff-46bb-b0b6-032f8de7c5d0", "191418f6-c0f5-4943-8171-d07bfeff46b0");
    }
    try {
        BCRefund refund = BCPay.startBCRefund(param);
        if (refund.getAliRefundUrl() != null) {
            response.sendRedirect(refund.getAliRefundUrl());
        } else {
        	if (refund.isNeedApproval() != null && refund.isNeedApproval()) {
        		out.println("预退款成功！");
        		out.println(refund.getObjectId());
        	} else {
            	out.println("退款成功！WX、易宝、百度、快钱渠道还需要定期查询退款结果！");
            	out.println(refund.getObjectId());
        	}
        }
    } catch (BCException e) {
    	out.println(e.getMessage());
        e.printStackTrace();
    }
    log.info("after start refund!");
    if (isYeeWap.equals("1")) {
        BeeCloud.registerApp("c37d661d-7e61-49ea-96a5-68c34e83db3b", "c37d661d-7e61-49ea-96a5-68c34e83db3b");
    }
%>
</body>
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
<%
   /*
	   功能：退款、预退款
	   版本：1.0
	   日期：2015-11-21
	   说明：退款处理页面， 用于对已支付的订单发起退款或者预退款，预退款并不真正发起退款，需待审核同意后，方会真正发起
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

    System.out.println("oooo:" + channelString);
    
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
%>
</body>
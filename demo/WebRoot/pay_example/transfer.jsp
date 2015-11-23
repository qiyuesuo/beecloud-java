<%@ page import="cn.beecloud.BCPay" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.BCUtil" %>
<%@ page import="cn.beecloud.bean.*" %>
<%@ page import="cn.beecloud.BCEumeration.*"%>

<%@ page import="cn.beecloud.bean.BCException" %>
<%@ page import="cn.beecloud.bean.TransferData" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Properties" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ include file="loadProperty.jsp" %>

<%
    /*
	     功能：商户打款
	     版本：1.0
	     日期：2015-11-21
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
	Logger log = Logger.getLogger("transfer.jsp");
	Properties prop = loadProperty();
	String sendName = prop.getProperty("sendName").toString();
	String wishing = prop.getProperty("wishing").toString();
	String activityName = prop.getProperty("activityName").toString();
	String openId = prop.getProperty("openId").toString();
	String aliUserId = prop.getProperty("aliUserId");
	String aliUserName = prop.getProperty("aliUserName");
	String redpackTransferNo = "8888888888";
	String aliTransferNo = BCUtil.generateRandomUUIDPure();
	RedpackInfo redpackInfo = new RedpackInfo();
	redpackInfo.setActivityName(activityName);
	System.out.println(activityName);
	redpackInfo.setSendName(sendName);
	redpackInfo.setWishing(wishing);
	TransferParameter param;
	
	
	String type = request.getParameter("transferType");
	TRANSFER_CHANNEL channel;
	try {
	    channel = TRANSFER_CHANNEL.valueOf(type);
	} catch (Exception e) {
	    channel = null;
	    log.error(e.getMessage(), e);
	}
	
	switch (channel) {
		case WX_REDPACK:
			param = new TransferParameter();
			param.setChannel(TRANSFER_CHANNEL.WX_REDPACK);
			param.setChannelUserId(openId);
			param.setTransferNo(redpackTransferNo);
			param.setTotalFee(200);
			param.setRedpackInfo(redpackInfo);
			param.setDescription("发红包");
	        try {
	            String result = BCPay.startTransfer(param);
	            out.println("微信红包发送成功！");
	        } catch (BCException e) {
	            log.error(e.getMessage(), e);
	            out.println(e.getMessage());
	        }
	        break;
		case WX_TRANSFER:
			param = new TransferParameter();
			param.setChannel(TRANSFER_CHANNEL.WX_TRANSFER);
			param.setChannelUserId(openId);
			param.setTransferNo(redpackTransferNo);
			param.setTotalFee(200);
			param.setDescription("微信单笔打款！");
			try {
	            String result = BCPay.startTransfer(param);
	            out.println("微信单笔打款成功！");
		    } catch (BCException e) {
		            log.error(e.getMessage(), e);
		            out.println(e.getMessage());
	        }
	        break;
		case ALI_TRANSFER:
			param = new TransferParameter();
			param.setChannel(TRANSFER_CHANNEL.ALI_TRANSFER);
			param.setChannelUserId(aliUserId);
			param.setChannelUserName(aliUserName);
			param.setTotalFee(1);
			param.setDescription("支付宝单笔打款！");
			param.setAccountName("苏州比可网络科技有限公司");
			param.setTransferNo(aliTransferNo);
			try {
	            String url = BCPay.startTransfer(param);
	            response.sendRedirect(url);
		    } catch (BCException e) {
		            log.error(e.getMessage(), e);
		            out.println(e.getMessage());
	        }
	        break;
	    default:
	        break;
	}
%>
<%@page import="cn.beecloud.bean.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="cn.beecloud.BCEumeration.PAY_CHANNEL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<link href="demo.css" rel="stylesheet" type="text/css"/>
<title>redirect</title>
<script type="text/javascript">
</script>
</head>
<body>
<%
	String querytype = request.getParameter("channel");
	
	BCQueryResult bcQueryResult;
	
	

	if (querytype.equals("aliQuery")) {
		BCQueryParameter param = new BCQueryParameter();
		param.setChannel(PAY_CHANNEL.ALI);
		
		bcQueryResult = BCPay.startQueryPrefundByConditon(param, "refund__");
		if (bcQueryResult.getType().ordinal() == 0) {
			pageContext.setAttribute("refunds", bcQueryResult.getBcRefundList());
			pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			pageContext.setAttribute("channel", "ALI");
		} else {
			out.println(bcQueryResult.getErrMsg());
			out.println(bcQueryResult.getErrDetail());
		}
	
	} else if (querytype.equals("wechatQuery")) {
		BCQueryParameter param = new BCQueryParameter();
		param.setChannel(PAY_CHANNEL.WX);
		
		bcQueryResult = BCPay.startQueryPrefundByConditon(param, "refund__");
		if (bcQueryResult.getType().ordinal() == 0) {
			pageContext.setAttribute("refunds", bcQueryResult.getBcRefundList());
			pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			pageContext.setAttribute("channel", "WX");
		} else {
			out.println(bcQueryResult.getErrMsg());
			out.println(bcQueryResult.getErrDetail());
		}
	} else if (querytype.equals("unionQuery")) {
		BCQueryParameter param = new BCQueryParameter();
		param.setChannel(PAY_CHANNEL.UN);
		
		bcQueryResult = BCPay.startQueryPrefundByConditon(param, "refund__");
		if (bcQueryResult.getType().ordinal() == 0) {
			pageContext.setAttribute("refunds", bcQueryResult.getBcRefundList());
			pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			pageContext.setAttribute("channel", "UN");
		} else {
			out.println(bcQueryResult.getErrMsg());
			out.println(bcQueryResult.getErrDetail());
		}
	} else if (querytype.equals("yeeQuery")) {
		BCQueryParameter param = new BCQueryParameter();
		param.setChannel(PAY_CHANNEL.YEE_WEB);
		
		bcQueryResult = BCPay.startQueryPrefundByConditon(param, "refund__");
		if (bcQueryResult.getType().ordinal() == 0) {
			pageContext.setAttribute("refunds", bcQueryResult.getBcRefundList());
			pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			pageContext.setAttribute("channel", "YEE");
		} else {
			out.println(bcQueryResult.getErrMsg());
			out.println(bcQueryResult.getErrDetail());
		}
	} else if (querytype.equals("yeeWapQuery")) {
		BCQueryParameter param = new BCQueryParameter();
		param.setChannel(PAY_CHANNEL.YEE_WAP);
		
		BeeCloud.registerApp("230b89e6-d7ff-46bb-b0b6-032f8de7c5d0", "191418f6-c0f5-4943-8171-d07bfeff46b0");
		bcQueryResult = BCPay.startQueryPrefundByConditon(param, "refund__");
		BeeCloud.registerApp("c37d661d-7e61-49ea-96a5-68c34e83db3b", "c37d661d-7e61-49ea-96a5-68c34e83db3b");
		
		if (bcQueryResult.getType().ordinal() == 0) {
			pageContext.setAttribute("refunds", bcQueryResult.getBcRefundList());
			pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			pageContext.setAttribute("channel", "YEE");
			pageContext.setAttribute("isYeeWap", "1");
		} else {
			out.println(bcQueryResult.getErrMsg());
			out.println(bcQueryResult.getErrDetail());
		}
	} else if (querytype.equals("jdQuery")) {
		BCQueryParameter param = new BCQueryParameter();
		param.setChannel(PAY_CHANNEL.JD);
		
		bcQueryResult = BCPay.startQueryPrefundByConditon(param, "refund__");
		if (bcQueryResult.getType().ordinal() == 0) {
			pageContext.setAttribute("refunds", bcQueryResult.getBcRefundList());
			pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			pageContext.setAttribute("channel", "JD");
		} else {
			out.println(bcQueryResult.getErrMsg());
			out.println(bcQueryResult.getErrDetail());
		}
	} else if (querytype.equals("kqQuery")) {
		BCQueryParameter param = new BCQueryParameter();
		param.setChannel(PAY_CHANNEL.KUAIQIAN);
		
		bcQueryResult = BCPay.startQueryPrefundByConditon(param, "refund__");
		if (bcQueryResult.getType().ordinal() == 0) {
			pageContext.setAttribute("refunds", bcQueryResult.getBcRefundList());
			pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			pageContext.setAttribute("channel", "KUAIQIAN");
		} else {
			out.println(bcQueryResult.getErrMsg());
			out.println(bcQueryResult.getErrDetail());
		}
	} else if (querytype.equals("bdQuery")) {
		BCQueryParameter param = new BCQueryParameter();
		param.setChannel(PAY_CHANNEL.BD);
		
		bcQueryResult = BCPay.startQueryPrefundByConditon(param, "refund__");
		if (bcQueryResult.getType().ordinal() == 0) {
			pageContext.setAttribute("refunds", bcQueryResult.getBcRefundList());
			pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			pageContext.setAttribute("channel", "BD");
		} else {
			out.println(bcQueryResult.getErrMsg());
			out.println(bcQueryResult.getErrDetail());
		}
	}else if (querytype.equals("noChannelQuery")) {
		BCQueryParameter param = new BCQueryParameter();
		param.setLimit(50);
		
		bcQueryResult = BCPay.startQueryBill(param);
		if (bcQueryResult.getType().ordinal() == 0) {
			pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
			pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
			pageContext.setAttribute("nochannel", true);
			pageContext.setAttribute("channel", null);
		} else {
			out.println(bcQueryResult.getErrMsg());
			out.println(bcQueryResult.getErrDetail());
		}
	}
%>


<c:if test="${refundSize != null and refundSize !=0}">
	<form name="prefund" action="batchPrefund.jsp" method="post">
		<table border="3" class="table"><tr><td></td><th>订单号</th><th>退款单号</th><th>标题</th><th>订单金额</th><th>退款金额</th><th>渠道</th><th>子渠道</th><th>是否结束</th><th>是否退款</th><th>退款创建时间</th><c:if test="${isWeChat != null}"><th>退款状态查询</th></c:if></tr>
			<c:forEach var="refund" items="${refunds}" varStatus="index"> 
				<tr align="center" ><td><input type="checkbox" name="id" value="${refund.objectId}"/></td><td>${refund.billNo}</td><td>${refund.refundNo}</td><td>${refund.title}</td><td>${refund.totalFee}</td><td>${refund.refundFee}</td><td>${refund.channel}</td><td>${refund.subChannel}</td><td>${refund.finished}</td><td>${refund.refunded}</td><td>${refund.dateTime}</td>
				</tr>
			</c:forEach> 
		</table>
		<br/>
		<div style="clear: both;">
				<input name="agree" type="submit" class="button" value="批量同意">
				<input name="deny" type="submit" class="button" value="批量驳回">
		</div>
		<input type="hidden" name="channel" value="${channel}"/> 
		<input type="hidden" name="isYeeWap" value="${isYeeWap}"/>
	</form>
</c:if>



<%@page import="cn.beecloud.BCEumeration.PAY_CHANNEL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<link href="demo.css" rel="stylesheet" type="text/css"/>
<title>redirect</title>
<script type="text/javascript">
	function queryStatus(refund_no) {
		window.location.href="queryWeChatRefundStatus.jsp?refund_no=" + refund_no;
	}
	
	function startRefund(bill_no, total_fee, channel) {
		window.location.href="startRefund.jsp?bill_no=" + bill_no + "&total_fee=" + total_fee + "&channel=" + channel;
	}
</script>
</head>
<body>
<%
	String querytype = request.getParameter("querytype");
	
	Object queryRefund = request.getParameter("queryRefund");
	
	BCQueryResult bcQueryResult;
	
	if(queryRefund != null) {
		if (querytype.equals("aliQuery")) {
			bcQueryResult = BCPay.startQueryRefund(PAY_CHANNEL.ALI, null, null, null, null, null, null);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErr_detail());
			}
		} else if (querytype.equals("wechatQuery")) {
			bcQueryResult = BCPay.startQueryRefund(PAY_CHANNEL.WX, null, null, null, null, null, null);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
				pageContext.setAttribute("isWeChat", true);
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErr_detail());
			}
		} else if (querytype.equals("unionQuery")) {
			bcQueryResult = BCPay.startQueryRefund(PAY_CHANNEL.UN, null, null, null, null, null, null);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
				pageContext.setAttribute("refundSize", bcQueryResult.getBcRefundList().size());
			}else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErr_detail());
			}
		}
	}else {
		if (querytype.equals("aliQuery")) {
			bcQueryResult = BCPay.startQueryBill(PAY_CHANNEL.ALI_WEB, null, null, null, null, null);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
				pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
				pageContext.setAttribute("channel", "ALI");
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErr_detail());
			}
		
		} else if (querytype.equals("wechatQuery")) {
			bcQueryResult = BCPay.startQueryBill(PAY_CHANNEL.WX, null, null, null, null, null);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
				pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
				pageContext.setAttribute("channel", "WX");
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErr_detail());
			}
		} else if (querytype.equals("unionQuery")) {
			bcQueryResult = BCPay.startQueryBill(PAY_CHANNEL.UN, null, null, null, null, null);
			if (bcQueryResult.getType().ordinal() == 0) {
				pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
				pageContext.setAttribute("billSize", bcQueryResult.getBcOrders().size());
				pageContext.setAttribute("channel", "UN");
			} else {
				out.println(bcQueryResult.getErrMsg());
				out.println(bcQueryResult.getErr_detail());
			}
		}
	}
%>
<c:if test="${billSize != null and billSize !=0}">
	<table border="3" class="table"><tr><th>订单号</th><th>总金额</th><th>标题</th><th>渠道</th><th>已付款</th><th>创建时间</th><th>发起退款</th></tr>
		<c:forEach var="bill" items="${bills}" varStatus="index"> 
			<tr><td>${bill.bill_no}</td><td>${bill.total_fee}</td><td>${bill.title}</td><td>${bill.channel}</td><td>${bill.spay_result}</td><td>${bill.dateTime}</td>
				<c:if test="${bill.spay_result == true}">
					<td align="center" >
						<input class="button" type="button" onclick="startRefund('${bill.bill_no}', ${bill.total_fee}, '${channel}')" value="退款"/>
					</td>
				</c:if>
			</tr>
		</c:forEach> 
	</table>
</c:if>
<c:if test="${refundSize != null and refundSize !=0}">
	<table border="3" class="table"><tr><th>订单号</th><th>退款单号</th><th>订单金额</th><th>退款金额</th><th>渠道</th><th>是否结束</th><th>是否退款</th><th>退款创建时间</th><c:if test="${isWeChat != null}"><th>退款状态查询</th></c:if></tr>
		<c:forEach var="refund" items="${refundList}" varStatus="index"> 
			<tr align="center" ><td>${refund.bill_no}</td><td>${refund.refund_no}</td><td>${refund.total_fee}</td><td>${refund.refund_fee}</td><td>${refund.channel}</td><td>${refund.finished}</td><td>${refund.refunded}</td><td>${refund.dateTime}</td>
			<c:if test="${isWeChat != null}">
			<td>
			<input class="button" type="button" onclick="queryStatus('${refund.refund_no}')" value="查询"/>
			</td>
			</c:if>
			</tr>
		</c:forEach> 
	</table>
</c:if>
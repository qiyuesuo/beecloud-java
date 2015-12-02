<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.bean.*"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="cn.beecloud.BCEumeration.*" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	/*
	功能：预退款查询
	版本：1.0
	日期：2015-11-21
	说明： 预退款查询展示页面，用于展示预退款记录， 同时可以对预退款发起批量审核，包括批量同意和批量否决
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
    <link href="../css/demo.css" rel="stylesheet" type="text/css"/>
    <title>prefund query</title>
    <script type="text/javascript">
    </script>
</head>
<body>
    <%
	String querytype = request.getParameter("channel");
	BCQueryParameter param = new BCQueryParameter();
	boolean isWechat = false;
	boolean isYeeWap = false;
	PAY_CHANNEL channel;
        if (querytype != null && querytype != "") {
            try {
                channel = PAY_CHANNEL.valueOf(querytype);
                param.setChannel(channel);
                isWechat = channel.toString().contains("WX");
                isYeeWap = channel.equals(PAY_CHANNEL.YEE_WAP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        param.setNeedApproval(true);
        try {
            int count = BCPay.startQueryRefundCount(param);
            pageContext.setAttribute("count", count);
        } catch (BCException e) {
            out.println(e.getMessage());
        }
        try {
            List<BCRefund> bcRefunds = BCPay.startQueryRefund(param);
            pageContext.setAttribute("refunds", bcRefunds);
            if (param.getChannel() != null) {
            	pageContext.setAttribute("channel", param.getChannel().toString().split("_")[0]);
            }
        } catch (BCException e) {
            out.println(e.getMessage());
        }
%>


<c:if test="${refunds != null and refunds.size() !=0}">
<form name="prefund" action="../refund_example/batchPrefund.jsp" method="post">
    <table border="3" class="table">
        <tr>
            <td></td>
            <th>订单号</th>
            <th>退款单号</th>
            <th>标题</th>
            <th>订单金额</th>
            <th>退款金额</th>
            <th>渠道</th>
            <th>是否结束</th>
            <th>是否退款</th>
            <th>退款创建时间</th>
            <%--<c:if test="${isWeChat != null}">--%>
                <%--<th>退款状态查询</th>--%>
            <%--</c:if></tr>--%>
        <c:forEach var="refund" items="${refunds}" varStatus="index">
            <tr align="center">
                <td><input type="checkbox" name="id" value="${refund.objectId}"/></td>
                <td>${refund.billNo}</td>
                <td>${refund.refundNo}</td>
                <td>${refund.title}</td>
                <td>${refund.totalFee}</td>
                <td>${refund.refundFee}</td>
                <td>${refund.channel}</td>
                <td>${refund.finished}</td>
                <td>${refund.refunded}</td>
                <td>${refund.dateTime}</td>
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



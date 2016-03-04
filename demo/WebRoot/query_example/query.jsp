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
	功能：批量查询
	版本：1.0
	日期：2015-11-25
	说明： 订单、退款查询展示页面，用于核查订单、退款状态,展示订单、退款详细信息， 同时可以对订单发起退款、预退款；查询退款状态
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
    <title>redirect</title>
    <script type="text/javascript">
        function queryStatus(channel, refund_no, isYeeWap) {
            window.location.href = "../refund_example/refundUpdate.jsp?refund_no=" + refund_no + "&channel=" + channel + "&isYeeWap=" + isYeeWap;
            ;
        }

        function startRefund(bill_no, total_fee, channel, prefund, isYeeWap) {
            window.location.href = "../refund_example/startRefund.jsp?bill_no=" + bill_no + "&total_fee=" + total_fee + "&channel=" + channel + "&prefund=" + prefund + "&isYeeWap=" + isYeeWap;

        }
    </script>
</head>
<body>
<%
    String querytype = request.getParameter("querytype");

    Object queryRefund = request.getParameter("queryRefund");

    Object queryPrefund = request.getParameter("queryPrefund");

    if (queryPrefund != null) {
        response.sendRedirect("prefundQuery.jsp?channel=" + querytype);
    }

    if (queryRefund != null) {
        BCQueryParameter param = new BCQueryParameter();
        if (querytype != null && querytype != "") {
            try {
                PAY_CHANNEL channel = PAY_CHANNEL.valueOf(querytype);
                param.setChannel(channel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        param.setNeedDetail(true);
        try {
            int count = BCPay.startQueryRefundCount(param);
            pageContext.setAttribute("count", count);
        } catch (BCException e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }

        try {
            List<BCRefund> bcRefunds = BCPay.startQueryRefund(param);
            pageContext.setAttribute("refundList", bcRefunds);
            pageContext.setAttribute("refundSize", bcRefunds.size());
        } catch (BCException e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    } else {
        BCQueryParameter param = new BCQueryParameter();
        if (querytype != null && querytype != "") {
            try {
                PAY_CHANNEL channel = PAY_CHANNEL.valueOf(querytype);
                param.setChannel(channel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        param.setNeedDetail(true);
        try {
            int count = BCPay.startQueryBillCount(param);
            pageContext.setAttribute("count", count);
        } catch (BCException e) {
            out.println(e.getMessage());
        }

        try {
            List<BCOrder> bcOrders = BCPay.startQueryBill(param);
            pageContext.setAttribute("bills", bcOrders);
            pageContext.setAttribute("billSize", bcOrders.size());
        } catch (BCException e) {
            out.println(e.getMessage());
        }
    }
%>
<c:if test="${billSize != null and billSize !=0}">
    <table border="3" class="table">
        <tr>
            <th>订单号</th>
            <th>总金额</th>
            <th>标题</th>
            <th>渠道交易号</th>
            <th>渠道</th>
            <th>已付款</th>
            <th>已撤销</th>
            <th>已退款</th>
            <th>附加数据</th>
            <th>渠道详细信息</th>
            <th>创建时间</th>
            <th>发起退款</th>
            <th>发起预退款</th>
        </tr>
        <c:forEach var="bill" items="${bills}" varStatus="index">
            <tr>
                <td>${bill.billNo}</td>
                <td>${bill.totalFee}</td>
                <td>${bill.title}</td>
                <td>${bill.channelTradeNo}</td>
                <td>${bill.channel}</td>
                <td>${bill.result}</td>
                <td>${bill.revertResult}</td>
                <td>${bill.refundResult}</td>
                <td>${bill.optionalString}</td>
                <td>${bill.messageDetail}</td>
                <td>${bill.dateTime}</td>

                <td align="center">
                    <c:if test="${bill.result == true && bill.refundResult == false && nochannel == null}">
                        <input class="button" type="button"
                               onclick="startRefund('${bill.billNo}', ${bill.totalFee}, '${bill.channel}', false, ${isYeeWap eq '1' ? '1':'0'})"
                               value="退款"/>
                    </c:if>
                    <c:if test="${bill.result == true && bill.refundResult == false && nochannel != null}">
                        <input class="button" type="button"
                               onclick="startRefund('${bill.billNo}', ${bill.totalFee}, '${channel}')" value="无渠道退款"/>
                    </c:if>
                </td>


                <td align="center">
                    <c:if test="${bill.result == true && bill.refundResult == false && nochannel == null}">
                    <input class="button" type="button"
                           onclick="startRefund('${bill.billNo}', ${bill.totalFee}, '${bill.channel}', true, ${isYeeWap eq '1' ? '1':'0'})"
                           value="预退款"/>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="20"><strong>符合条件记录总条数:</strong><font color="green">${count}</font></td>
        </tr>
    </table>
</c:if>
<c:if test="${refundSize != null and refundSize !=0}">
    <table border="3" class="table">
        <tr>
            <th>订单号</th>
            <th>退款单号</th>
            <th>标题</th>
            <th>订单金额</th>
            <th>退款金额</th>
            <th>渠道</th>
            <th>是否结束</th>
            <th>是否退款</th>
            <th>附加数据</th>
            <th>渠道详细信息</th>
            <th>退款创建时间</th>
            <th>退款查询</th>
        </tr>
        <c:forEach var="refund" items="${refundList}" varStatus="index">
            <tr align="center">
                <td>${refund.billNo}</td>
                <td>${refund.refundNo}</td>
                <td>${refund.title}</td>
                <td>${refund.totalFee}</td>
                <td>${refund.refundFee}</td>
                <td>${refund.channel}</td>
                <td>${refund.finished}</td>
                <td>${refund.refunded}</td>
                <td>${refund.optionalString}</td>
                <td>${refund.messageDetail}</td>
                <td>${refund.dateTime}</td>

                    <td>
                        <c:if test="${fn:containsIgnoreCase(refund.channel,'WX')
                        || fn:containsIgnoreCase(refund.channel,'YEE')
                        || fn:containsIgnoreCase(refund.channel,'BD')
                        || fn:containsIgnoreCase(refund.channel,'KUAIQIAN')}">
                        <input class="button" type="button"
                               onclick="queryStatus('${refund.channel}','${refund.refundNo}', ${isYeeWap eq '1'?'1':'0'})"
                               value="查询"/>
                        </c:if>
                    </td>

            </tr>
        </c:forEach>
        <tr>
            <td colspan="20"><strong>符合条件记录总条数:</strong><font color="green">${count}</font></td>
        </tr>
    </table>
</c:if>
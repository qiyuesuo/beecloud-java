<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="cn.beecloud.bean.BCOrder" %>
<%@ page import="cn.beecloud.bean.BCException" %>
<%@ page import="cn.beecloud.bean.BCRefund" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
   /*
	功能：单笔查询
	版本：1.0
	日期：2015-11-25
	说明： 单笔订单、退款记录展示页面，用于展示根据id查询返回的订单、退款记录， 同时可以对订单发起退款、预退款；查询退款状态
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
    <title>Query By Id</title>
    <script type="text/javascript">
        function queryStatus(channel, refund_no, isYeeWap) {
            window.location.href = "../refund_example/refundUpdate.jsp?refund_no=" + refund_no + "&channel=" + channel + "&isYeeWap=" + isYeeWap;
        }
        function startRefund(bill_no, total_fee, channel, prefund, isYeeWap) {
            window.location.href = "../refund_example/startRefund.jsp?bill_no=" + bill_no + "&total_fee=" + total_fee + "&channel=" + channel + "&prefund=" + prefund + "&isYeeWap=" + isYeeWap;
        }
    </script>
</head>
<body>
<%
    Object queryRefund = request.getParameter("queryRefund");

    String id = request.getParameter("id");
    if (id.equals("")) {
        out.println("请输入ID!");
        return;
    }
	/*
	* bill Id或者refund Id可根据需求自行传入，本处数据作为样例数据，仅供参考
	*/
    if (queryRefund != null) {
        try {
            BCRefund result = BCPay.startQueryRefundById(id);
            pageContext.setAttribute("refund", result);
        } catch (BCException e) {
            out.println(e.getMessage());
        }
    } else {
        try {
            BCOrder result = BCPay.startQueryBillById(id);
            pageContext.setAttribute("bill", result);
        } catch (BCException e) {
            out.println(e.getMessage());
        }
    }
%>

<c:if test="${refund != null}">
    <table border="3" class="table">
        <tr>
            <th>订单号</th>
            <th>退款单号</th>
            <th>标题</th>
            <th>订单金额</th>
            <th>退款金额</th>
            <th>渠道</th>
            <th>附加数据</th>
            <th>是否结束</th>
            <th>是否退款</th>
            <th>渠道详细信息</th>
            <th>退款创建时间</th>
            <c:if test="${fn:containsIgnoreCase(refund.channel,'WX') || fn:containsIgnoreCase(refund.channel,'YEE') || fn:containsIgnoreCase(refund.channel,'BD') || fn:containsIgnoreCase(refund.channel,'KUAIQIAN')}">
                <th>退款状态查询</th>
            </c:if></tr>
        <tr align="center">
            <td>${refund.billNo}</td>
            <td>${refund.refundNo}</td>
            <td>${refund.title}</td>
            <td>${refund.totalFee}</td>
            <td>${refund.refundFee}</td>
            <td>${refund.channel}</td>
            <td>${refund.optionalString}</td>
            <td>${refund.finished}</td>
            <td>${refund.refunded}</td>
            <td>${refund.messageDetail}</td>
            <td>${refund.dateTime}</td>
            <c:if test="${fn:containsIgnoreCase(refund.channel,'WX') || 
	            fn:containsIgnoreCase(refund.channel,'YEE') || 
	            fn:containsIgnoreCase(refund.channel,'BD') || 
	            fn:containsIgnoreCase(refund.channel,'KUAIQIAN')}">
                <td>
                <input class="button" type="button" onclick="queryStatus('${refund.channel}','${refund.refundNo}', ${isYeeWap eq '1'?'1':'0'})"
                       value="查询"/>
                </td>
            </c:if>

        </tr>
    </table>
</c:if>
<c:if test="${bill != null}">
    <table border="3" class="table">
        <tr>
            <th>订单号</th>
            <th>标题</th>
            <th>订单金额</th>
            <th>渠道</th>
            <th>渠道交易号</th>
            <th>附加数据</th>
            <th>是否支付成功</th>
            <th>已退款</th>
            <th>渠道详细信息</th>
            <th>订单创建时间</th>
            <th>发起退款</th>
            <th>发起预退款</th>
        </tr>
        <tr align="center">
            <td>${bill.billNo}</td>
            <td>${bill.title}</td>
            <td>${bill.totalFee}</td>
            <td>${bill.channel}</td>
            <td>${bill.channelTradeNo}</td>
            <td>${bill.optionalString}</td>
            <td>${bill.result}</td>
            <td>${bill.refundResult}</td>
            <td>${bill.messageDetail}</td>
            <td>${bill.dateTime}</td>

            <td align="center">
                <c:if test="${bill.result == true}">
                <input class="button" type="button"
                       onclick="startRefund('${bill.billNo}', ${bill.totalFee}, '${bill.channel}', false, ${isYeeWap eq '1' ? '1':'0'})" value="退款"/>
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
    </table>
</c:if>
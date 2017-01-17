<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="cn.beecloud.bean.BCOrder" %>
<%@ page import="cn.beecloud.bean.BCException" %>
<%@ page import="cn.beecloud.bean.BCRefund" %>
<%@ page import="cn.beecloud.bean.BCGateWayBanks" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
   /*
	功能：查询京东网关列表
	版本：1.0
	日期：2017-01-16
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
    <title>Query backlist</title>
    <script type="text/javascript">
      /*  function queryStatus(channel, refund_no, isYeeWap) {
            window.location.href = "../refund_example/refundUpdate.jsp?refund_no=" + refund_no + "&channel=" + channel + "&isYeeWap=" + isYeeWap;
        }
        function startRefund(bill_no, total_fee, channel, prefund, isYeeWap) {
            window.location.href = "../refund_example/startRefund.jsp?bill_no=" + bill_no + "&total_fee=" + total_fee + "&channel=" + channel + "&prefund=" + prefund + "&isYeeWap=" + isYeeWap;
        }*/
    </script>
</head>
<body>
    <%
    BeeCloud.registerApp("beacfdf5-badd-4a11-9b23-9ef3801732d1", "17bc2268-9964-4c33-9f7c-37cd561a8c5c", "17bc2268-9964-4c33-9f7c-37cd561a8c5c", "e14ae2db-608c-4f8b-b863-c8c18953eef2");
%>
<%
    String  cardType="1";
    String payType="B2C";
    if(null!=request.getParameter("cardType")&&!"".equals(request.getParameter("cardType"))){
        cardType=request.getParameter("cardType");
    }
    if(null!=request.getParameter("payType")&&!"".equals(request.getParameter("payType"))){
        payType=request.getParameter("payType");
    }
    BCGateWayBanks banks=new BCGateWayBanks();
    banks.setCardType(cardType);
    banks.setPayType(payType);
    try {
        List<String> result = BCPay.getGateWayBanks(banks);
       // System.out.print(result);
        pageContext.setAttribute("list", result);
    } catch (BCException e) {
        out.println(e.getMessage());
    }

%>

<c:if test="${list != null}">
    <c:forEach items="${list}" var="item" >
          ${item}<br>
    </c:forEach>
</c:if>

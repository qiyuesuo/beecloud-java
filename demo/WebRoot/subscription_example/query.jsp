<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.bean.*"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    /*
     功能：订阅查询
     版本：1.0
     日期：2016-07-27
     说明： 订阅、Plan查询页面
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
    <title>Subscription Query</title>
</head>
<body>
<%
    String action = request.getParameter("action");

    if (action.equals("subscription_query")) {
        BCSubscriptionQueryParameter param = new BCSubscriptionQueryParameter();
//        param.setEndTime(new Date());
        try {
            Object result = BCSubscriptionPay.fetchSubsciptionByCondition(param);
            if (result instanceof List) {
                List<BCSubscription> subscriptions = (List<BCSubscription>)result;
                pageContext.setAttribute("subscriptionList", subscriptions);
                pageContext.setAttribute("subscriptionSize", subscriptions.size());
            } else {
                out.print(result);
            }
        } catch (BCException ex) {
            ex.printStackTrace();
            out.println(ex.getMessage());
        }
    }
    if (action.equals("plan_query")) {
        BCPlanQueryParameter param = new BCPlanQueryParameter();
        param.setEndTime(new Date());
        param.setNameWithSubstring("订阅");
        try {
            Object result = BCSubscriptionPay.fetchPlanByCondition(param);
            if (result instanceof List) {
                List<BCPlan> plans = (List<BCPlan>)result;
                pageContext.setAttribute("planList", plans);
                pageContext.setAttribute("planSize", plans.size());
            } else {
                out.print(result);
            }
        } catch (BCException ex) {
            ex.printStackTrace();
            out.println(ex.getMessage());
        }
    }
    if (action.equals("subscription_banks")) {
        try {
            SubscriptionBanks banks = BCSubscriptionPay.fetchSubscrptionBanks();
            out.println("banks:" + banks.getBankList().toString());
            out.println("<br/><br/>");
            out.println("common_banks:" + banks.getCommonBankList().toString());
        } catch (BCException ex) {
            ex.printStackTrace();
            out.println(ex.getMessage());
        }
    }


%>
<c:if test="${subscriptionSize != null and subscriptionSize !=0}">
    <table border="3" class="table">
        <tr>
            <th>Buyer ID</th>
            <th>Plan ID</th>
            <th>Card ID</th>
            <th>数额</th>
            <th>试用截止时间</th>
            <th>手机号码</th>
            <th>身份证号码</th>
            <th>姓名</th>
            <th>Coupon ID</th>
            <th>Valid</th>
            <th>订阅状态</th>
            <th>账号类型</th>
            <th>类型</th>
            <th>到期是否取消</th>
            <th>创建时间</th>
            <th>更新时间</th>
            <th>可选内容</th>
            <th>last4</th>
        </tr>
        <c:forEach var="subscription" items="${subscriptionList}" varStatus="index">
            <tr>
                <td>${subscription.buyerId}</td>
                <td>${subscription.planId}</td>
                <td>${subscription.cardId}</td>
                <td>${subscription.amount}</td>
                <td>${subscription.trialEnd}</td>
                <td>${subscription.mobile}</td>
                <td>${subscription.idNo}</td>
                <td>${subscription.idName}</td>
                <td>${subscription.couponId}</td>
                <td>${subscription.valid}</td>
                <td>${subscription.status}</td>
                <td>${subscription.accountType}</td>
                <td>${subscription.type}</td>
                <td>${subscription.cancelAtPeriodEnd}</td>
                <td>${subscription.createDate}</td>
                <td>${subscription.updateDate}</td>
                <td>${subscription.optionalString}</td>
                <td>${subscription.last4}</td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="20"><strong>符合条件记录总条数:</strong><font color="green">${subscriptionSize}</font></td>
        </tr>
    </table>
</c:if>
<c:if test="${planSize != null and planSize !=0}">
    <table border="3" class="table">
        <tr>
            <th>name</th>
            <th>fee</th>
            <th>interval</th>
            <th>intervalCount</th>
            <th>类型</th>
            <th>currency</th>
            <th>trailDays</th>
            <th>id</th>
            <th>创建时间</th>
            <th>更新时间</th>
            <th>optional</th>
        </tr>
        <c:forEach var="plan" items="${planList}" varStatus="index">
            <tr align="center">
                <td>${plan.name}</td>
                <td>${plan.fee}</td>
                <td>${plan.interval}</td>
                <td>${plan.intervalCount}</td>
                <td>${plan.type}</td>
                <td>${plan.currency}</td>
                <td>${plan.trailDays}</td>
                <td>${plan.id}</td>
                <td>${plan.createDate}</td>
                <td>${plan.updateDate}</td>
                <td>${plan.optionalString}</td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="20"><strong>符合条件记录总条数:</strong><font color="green">${planSize}</font></td>
        </tr>
    </table>
</c:if>
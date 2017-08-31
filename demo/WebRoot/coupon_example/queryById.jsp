<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="cn.beecloud.bean.BCOrder" %>
<%@ page import="cn.beecloud.bean.BCCouponTemplate" %>
<%@ page import="cn.beecloud.bean.BCCoupon" %>
<%@ page import="cn.beecloud.bean.BCException" %>
<%@ page import="cn.beecloud.bean.BCRefund" %>
<%@ page import="cn.beecloud.BCCouponManage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
   
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; UTF-8">
    <link href="../css/demo.css" rel="stylesheet" type="text/css"/>
    <title>Query By Id</title>
</head>
<body>
<%
	String couponId = request.getParameter("couponId");

	try {
	    BCCoupon bcCoupon = BCCouponManage.startQueryCouponById("dd7b827a-c452-41c2-987c-8d130fc5af20");
	    pageContext.setAttribute("bcCoupon", bcCoupon);
	} catch (BCException e) {
		out.println(e.getMessage());
	}
%>

<c:if test="${bcCoupon != null}">
    <table border="3" class="table">
        <tr>
            <th>用户id</th>
            <th>状态</th>
            <th>分发时间</th>
            <th>更新时间</th>
            <th>有效期开始时间</th>
            <th>有效期结束时间</th>
            <th>使用时间</th>
        </tr>
        <tr align="center">
            <td>${bcCoupon.userId}</td>
            <td>
            	<c:if test="${bcCoupon.status == 0}">
            		未使用
            	</c:if>
            	<c:if test="${bcCoupon.status == 1}">
            		已使用
            	</c:if>
            </td>
            <td>${bcCoupon.createdAt}</td>
            <td>${bcCoupon.updatedAt}</td>
            <td>${bcCoupon.startTime}</td>
            <td>${bcCoupon.endTime}</td>
            <td>${bcCoupon.useTime}</td>
        </tr>
    </table>
</c:if>

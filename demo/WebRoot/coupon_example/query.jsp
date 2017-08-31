<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.bean.*"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="cn.beecloud.BCEumeration.*" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
   
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; UTF-8">
    <link href="../css/demo.css" rel="stylesheet" type="text/css"/>
    <title>redirect</title>
</head>
<body>
<%
	try {
	    BCQueryCouponParam queryCouponParam = new BCQueryCouponParam();
	    queryCouponParam.setStatus(0);
	    List<BCCoupon> bcCoupons = BCCouponManage.startQueryCoupons(queryCouponParam);
	    pageContext.setAttribute("bcCoupons", bcCoupons);
	    pageContext.setAttribute("bcCouponsSize", bcCoupons.size()); 
	} catch (BCException e) {
		out.println(e.getMessage());
	}
%>
<c:if test="${bcCouponsSize != null and bcCouponsSize !=0}">
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
        <c:forEach var="bcCoupon" items="${bcCoupons}" varStatus="index">
            <tr>
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
        </c:forEach>
        <tr>
            <td colspan="20"><strong>符合条件记录总条数:</strong><font color="green">${bcCouponsSize}</font></td>
        </tr>
    </table>
</c:if>

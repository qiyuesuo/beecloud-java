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
	try {
		BCCouponTemplate couponTemplate = BCCouponManage.startQueryCouponTemplateById("c8eee5a6-e8c9-410e-a7e1-23583011a42d");
	    pageContext.setAttribute("couponTemplate", couponTemplate);
	} catch (BCException e) {
	    out.println(e.getMessage());
	}
%>

<c:if test="${couponTemplate != null}">
    <table border="3" class="table">
        <tr>
            <th>名称</th>
            <th>类型</th>
            <th>卡券总数</th>
            <th>每个人最多可以领取的卡券数量</th>
            <th>分发卡券数量</th>
            <th>使用卡券数量</th>
            <th>卡券开始时间</th>
            <th>卡券结束时间</th>
            <th>状态</th>	
            <th>卡券所属商家账户</th>
        </tr>
        <tr align="center">
            <td>${couponTemplate.name}</td>
            <td>
            	<c:choose>
            		<c:when test="${couponTemplate.type == 0}">
            			满减
	            	</c:when>
	            	<c:otherwise>
	            		折扣
	            	</c:otherwise>
            	</c:choose>
            </td>
            <td>
            	<c:choose>
            		<c:when test="${couponTemplate.totalCount == 0}">
            			不限制
	            	</c:when>
	            	<c:otherwise>
	            		${couponTemplate.totalCount}
	            	</c:otherwise>
            	</c:choose>
            </td>
            <td>
            	<c:choose>
            		<c:when test="${couponTemplate.maxCountPerUser == 0}">
            			不限制
	            	</c:when>
	            	<c:otherwise>
	            		${couponTemplate.maxCountPerUser}
	            	</c:otherwise>
            	</c:choose>
            </td>
            <td>${couponTemplate.deliverCount}</td>
            <td>${couponTemplate.useCount}</td>
            <td>${couponTemplate.startTime}</td>
            <td>${couponTemplate.endTime}</td>
            <td>
            	<c:if test="${couponTemplate.status == 0}">
            		未开启
            	</c:if>
            	<c:if test="${couponTemplate.status == 1}">
            		正常使用
            	</c:if>
            	<c:if test="${couponTemplate.status == -1}">
            		停止使用
            	</c:if>
            </td>
            <td>${couponTemplate.mchAccount}</td>
        </tr>
    </table>
</c:if>

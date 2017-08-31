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
	    BCQueryCouponTemplateParam queryCouponTemplateParam = new BCQueryCouponTemplateParam();
	    queryCouponTemplateParam.setCreatedBefore(1501516800000L);
	    List<BCCouponTemplate> couponTemplates = BCCouponManage.startQueryCouponTemplates(queryCouponTemplateParam);
	    pageContext.setAttribute("couponTemplates", couponTemplates);
	    pageContext.setAttribute("couponTemplatesSize", couponTemplates.size()); 
	} catch (BCException e) {
		out.println(e.getMessage());
	}
%>
<c:if test="${couponTemplatesSize != null and couponTemplatesSize !=0}">
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
        <c:forEach var="couponTemplate" items="${couponTemplates}" varStatus="index">
            <tr>
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
        </c:forEach>
        <tr>
            <td colspan="20"><strong>符合条件记录总条数:</strong><font color="green">${couponTemplatesSize}</font></td>
        </tr>
    </table>
</c:if>

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
	String templateId = "c8eee5a6-e8c9-410e-a7e1-23583011a42d";
	String userId = "aa2@bc.com";
	String couponId = "";
	try {
	    BCCoupon bcCoupon = BCCouponManage.startCouponDistribute(templateId, userId);
	    couponId = bcCoupon.getId();
	    System.out.println(couponId);
	} catch (BCException e) {
		out.println(e.getMessage());
	}
%>


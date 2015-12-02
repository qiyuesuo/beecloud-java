<%@ page import="cn.beecloud.bean.*" %>
<%@ page import="cn.beecloud.BCEumeration.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Arrays" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
   /*
	   功能：批量审核
	   版本：1.0
	   日期：2015-11-21
	   说明：批量审核处理页面， 用于对选择的预退款发起批量同意或者批量否决操作
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
    <title>Batch</title>
    <script type="text/javascript">
        function confirm(url) {
            window.location.href = url;
        }
    </script>
</head>
<body>
<%
	Logger log = Logger.getLogger("batchPrefund.jsp");

    String[] ids = request.getParameterValues("id");
    if (ids == null) {
        out.println("请选择预退款记录!");
        return;
    }
    BCBatchRefund batchRefund = new BCBatchRefund();
    PAY_CHANNEL channel;
    try {
        channel = PAY_CHANNEL.valueOf(request.getParameter("channel").toString());
    } catch (Exception e) {
        channel = null;
        log.error(e.getMessage(), e);
    }
    Object agree = request.getParameter("agree");
    batchRefund.setIds(Arrays.asList(ids));
    batchRefund.setChannel(channel);
    batchRefund.setAgree(agree != null?true:false);
    
    String isYeeWap = request.getParameter("isYeeWap");
    
    if (agree != null) {
    	try {
        	BCBatchRefund result = BCPay.startBatchRefund(batchRefund);
        	out.println("<div>");
            for (String key : result.getIdResult().keySet()) {
                String info = result.getIdResult().get(key);
                out.println(key + ":" + info + "<br/>");
            }
            if (channel.equals(PAY_CHANNEL.ALI))
                out.println("</div><br/><div style=\"clear: both;\"><input onclick=\"confirm('" + result.getAliRefundUrl() + "')\" name=\"confirm\" type=\"submit\" class=\"button\" value=\"确认\"></div>");
            else
                out.println("</div><br/><div style=\"clear: both;\"><input onclick='javascript:window.close();' class=\"button\" value=\"确认\"></div>");
    	} catch(BCException ex) {
    		ex.printStackTrace();
            out.println(ex.getMessage());
    	}
    } else {
    	try {
	    	BCBatchRefund result = BCPay.startBatchRefund(batchRefund);
	        out.println("<h3>批量驳回成功!</h3>");
            out.println("<br><br><div style=\"clear: both;\"><input onclick='javascript:window.close();' class=\"button\" value=\"确认\"></div>");
    	} catch(BCException ex) {
    		ex.printStackTrace();
            out.println(ex.getMessage());
    	}
    }
%>
</body>
</html>

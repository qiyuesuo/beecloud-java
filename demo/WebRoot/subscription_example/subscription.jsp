<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.bean.*"%>
<%@ page import="cn.beecloud.*"%>

<%
   /*
	   功能：订阅
	   版本：1.0
	   日期：20156-07-27
	   说明： 发起订阅页面， 用于发起比可网络订阅支付的请求。
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
    <title>subscription</title>
</head>
<body>

<%
    String action = request.getParameter("action");

    if (action.equals("sms")) {
        try {
            BCSubscriptionSMSResult  smsResult = BCSubscriptionPay.sendSMS("13861331391");
            out.println(smsResult.getSmsId());
            out.println(smsResult.getCode());
            session.setAttribute("smsId", smsResult.getSmsId());
            session.setAttribute("smsCode", smsResult.getCode());
        } catch (BCException ex){
            out.print(ex.getMessage());
        }
    }
    if (action.equals("subscription")) {
        try {
            BCSubscription subscription = new BCSubscription();
            subscription.setPlanId("4a009b37-c36a-49d3-b011-d13d43535b96");
            subscription.setBuyerId("rui test buyer id");
            subscription.setSmsId((String)session.getAttribute("smsId"));
            subscription.setSmsCode((String)session.getAttribute("smsCode"));
            subscription.setMobile("13861331391");
            subscription.setBankName("交通银行");
            subscription.setCardNo("6222600140019886466");
            subscription.setIdName("冯睿");
            subscription.setIdNo("320503198306271012");
            BCSubscription result = BCSubscriptionPay.startSubscription(subscription);
            out.println(result.getCardId());
            out.println(result.getValid());
            out.println(result.getStatus());

        } catch (BCException ex){
            out.print(ex.getMessage());
        }
    }
%>
</div>
</body>
</html>
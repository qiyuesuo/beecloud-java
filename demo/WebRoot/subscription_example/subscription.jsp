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
            String smsId = BCSubscriptionPay.sendSMS("13461351392");
            out.println(smsId);
            session.setAttribute("smsId", smsId);
        } catch (BCException ex){
            out.print(ex.getMessage());
        }
    }
    if (action.equals("subscription")) {
        try {
            /**
             * 以下是通过银行5要素订阅的demo
             */
            BCSubscription subscription = new BCSubscription();
            subscription.setPlanId("3e55bcfa-3805-46a3-bb2a-ba63247f8f18");
            subscription.setBuyerId("demo buyer id");
            subscription.setSmsId("378d65c2-b46c-4f7e-861c-0dca80b01149");//发送验证码接口返回的id
            subscription.setSmsCode("0415");//收到的短信验证码
            subscription.setMobile("13841335392");
            subscription.setBankName("交通银行");
            subscription.setCardNo("6242600110019686443");
            subscription.setIdName("冯小刚");
            subscription.setIdNo("320703198706271022");
            BCSubscription result = BCSubscriptionPay.startSubscription(subscription);
            out.println(result.getCardId());
            out.println(result.getValid());
            out.println(result.getStatus());

            /**
             * 以下是直接通过cardId订阅的demo
             */
//            subscription.setCardId("d6282212-1e9b-4f45-8977-8482a497d55a");
//            subscription.setPlanId("83b22a78-b76c-4740-3350-25e5h0a69571");
//            subscription.setBuyerId("demo buyer id with cardId");
//            BCSubscription result = BCSubscriptionPay.startSubscription(subscription);
//            out.print(result.getStatus());

        } catch (BCException ex){
            out.print(ex.getMessage());
        }
    }
    if (action.equals("subscription_cancel")) {
        String id;
        BCSubscription subscription = new BCSubscription();
        subscription.setObjectId("a4543422-48ef-b31c-9b4f-c0eb9fc8c002");
        subscription.setCancelAtPeriodEnd(true);
        try {
            id = BCSubscriptionPay.cancelSubscription(subscription);
            out.print(id);
        } catch (BCException e) {
            e.printStackTrace();
        }
    }
%>
</div>
</body>
</html>
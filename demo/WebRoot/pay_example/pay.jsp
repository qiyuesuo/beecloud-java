<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cn.beecloud.bean.*"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="cn.beecloud.BCEumeration.*" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.Properties" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.lang.Thread" %>
<%@ include file="loadProperty.jsp" %>
<%
   /*
	   功能：用户支付
	   版本：1.0
	   日期：2015-11-21
	   说明： 支付处理页面， 用于发起比可网络支付系统的请求，包括支付宝、微信、银联、易宝、京东、百度、快钱等渠道以及境外支付渠道PAYPAL
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
    <title>pay</title>
</head>
<body>

<%

    Logger log = Logger.getLogger("pay.jsp");

    //模拟商户的交易编号、标题、金额、附加数据
    String billNo = BCUtil.generateRandomUUIDPure();
    String title = "demo测试";
    Map<String, Object> optional = new HashMap<String, Object>();
    optional.put("rui", "feng");

    String type = request.getParameter("paytype");
    PAY_CHANNEL channel;
    try {
        channel = PAY_CHANNEL.valueOf(type);
    } catch (Exception e) {
        channel = null;
        log.error(e.getMessage(), e);
    }
    boolean success = false;

    BCOrder bcOrder = new BCOrder(channel, 1, billNo, title);
    BCInternationlOrder internationalOrder = new BCInternationlOrder();
    bcOrder.setBillTimeout(360);
    bcOrder.setOptional(optional);

    //以下是WX_JSAPI（公众号内支付）用到的返回参数，需要在页面的js用到
    String jsapiAppid = "";
    String timeStamp = "";
    String nonceStr = "";
    String jsapipackage = "";
    String signType = "";
    String paySign = "";
    
    //以下是每个渠道的return url
    String aliReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/return_url_example/aliReturnUrl.jsp";
    String unReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/return_url_example/unReturnUrl.jsp";
    String yeeWapReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/return_url_example/yeeWapReturnUrl.jsp";
    String yeeWebReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/return_url_example/yeeWebReturnUrl.jsp";
    String jdWapReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/return_url_example/jdWapReturnUrl.jsp";
    String jdWebReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/return_url_example/jdWebReturnUrl.jsp";
    String kqReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/return_url_example/kqReturnUrl.jsp";
    String bdReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/return_url_example/bdReturnUrl.jsp";
	String paypalReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/return_url_example/paypalReturnUrl.jsp";
	String bcGatewayReturnUrl = "http://localhost:8081/return_url_example/bcGatewayReturnUrl.jsp";
    String bcExpressReturnUrl = "http://localhost:8081/return_url_example/bcExpressReturnUrl.jsp";
    String cpReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/return_url_example/cpReturnUrl.jsp";


    switch (channel) {

        case ALI_WEB:
        case ALI_WAP:
            bcOrder.setReturnUrl(aliReturnUrl);
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                System.out.println(bcOrder.getHtml());
                out.println(bcOrder.getHtml());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;
        case ALI_QRCODE:
            bcOrder.setQrPayMode(QR_PAY_MODE.MODE_FRONT);
            bcOrder.setReturnUrl(aliReturnUrl);
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                out.println(bcOrder.getHtml());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;
        case BC_GATEWAY:
            try {
                bcOrder.setReturnUrl(bcGatewayReturnUrl);
                bcOrder.setGatewayBank(GATEWAY_BANK.CMB);
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                out.println(bcOrder.getHtml());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;
        case BC_EXPRESS:
            try {
                bcOrder.setReturnUrl(bcExpressReturnUrl);
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                response.sendRedirect(bcOrder.getUrl());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;
        case WX:
            break;
        case WX_APP:
            break;
        case WX_NATIVE:
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                success = true;
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;

        case WX_JSAPI:
            //微信 公众号id（读取配置文件conf.properties）及微信 redirec_uri
            Properties prop = loadProperty();
            String wxJSAPIAppId = prop.get("wxJSAPIAppId").toString();
            String wxJSAPISecret = prop.get("wxJSAPISecret").toString();
            String wxJSAPIRedirectUrl = "http://javademo.beecloud.cn/demo/pay_example/pay.jsp?paytype=WX_JSAPI";
            String encodedWSJSAPIRedirectUrl = URLEncoder.encode(wxJSAPIRedirectUrl);
            if (request.getParameter("code") == null || request.getParameter("code").toString().equals("")) {
                String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wxJSAPIAppId + "&redirect_uri=" + encodedWSJSAPIRedirectUrl + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
                log.info("wx jsapi redirct url:" + redirectUrl);
                response.sendRedirect(redirectUrl);
            } else {
                String code = request.getParameter("code");
                String result = sendGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxJSAPIAppId + "&secret=" + wxJSAPISecret + "&code=" + code + "&grant_type=authorization_code");
                log.info("result:" + result);
                JSONObject resultObject = JSONObject.fromObject(result);
                if (resultObject.containsKey("errcode")) {
                    out.println("获取access_token出错！错误信息为：" + resultObject.get("errmsg").toString());
                } else {
                    String openId = resultObject.get("openid").toString();
                    bcOrder.setOpenId(openId);
                    try {
                        bcOrder = BCPay.startBCPay(bcOrder);
                        out.println(bcOrder.getObjectId());
                        System.out.print(bcOrder.getObjectId());

                        Map<String, String> map = bcOrder.getWxJSAPIMap();
                        jsapiAppid = map.get("appId").toString();
                        timeStamp = map.get("timeStamp").toString();
                        nonceStr = map.get("nonceStr").toString();
                        jsapipackage = map.get("package").toString();
                        signType = map.get("signType").toString();
                        paySign = map.get("paySign").toString();
                    } catch (BCException e) {
                        log.error(e.getMessage(), e);
                        out.println(e.getMessage());
                    }
                }
            }

            break;

        case UN_WEB:
        case UN_WAP:
            bcOrder.setReturnUrl(unReturnUrl);
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                out.println(bcOrder.getHtml());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;

        case CP_WEB:
            bcOrder.setReturnUrl(cpReturnUrl);
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                out.println(bcOrder.getHtml());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;


        case YEE_WAP:
            bcOrder.setReturnUrl(yeeWapReturnUrl);
            Object identityId = session.getAttribute("identityId");
            if (identityId == null) {
                identityId = UUID.randomUUID().toString().replace("-","");
                session.setAttribute("identityId", identityId);
            }
            bcOrder.setIdentityId(identityId.toString());
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                response.sendRedirect(bcOrder.getUrl());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;
        case YEE_WEB:
            bcOrder.setReturnUrl(yeeWebReturnUrl);
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                response.sendRedirect(bcOrder.getUrl());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;
        case YEE_NOBANKCARD:
            //易宝点卡支付参数样例
            String cardNo = "15078120125091678";
            String cardPwd = "121684730734269992";
            String frqid = "SZX";
            bcOrder.setTotalFee(10);
            bcOrder.setCardNo(cardNo);
            bcOrder.setCardPwd(cardPwd);
            bcOrder.setFrqid(frqid);
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                System.out.print(bcOrder.getObjectId());
                out.println(bcOrder.getObjectId());
                out.println("点卡支付成功！");
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;
        case JD_WAP:
            bcOrder.setReturnUrl(jdWapReturnUrl);
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                out.println(bcOrder.getHtml());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;
        case JD_WEB:
            bcOrder.setReturnUrl(jdWebReturnUrl);
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                out.println(bcOrder.getHtml());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;
        case KUAIQIAN_WEB:
        	bcOrder.setReturnUrl(kqReturnUrl);
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                out.println(bcOrder.getHtml());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;

        case KUAIQIAN_WAP:
            bcOrder.setReturnUrl(kqReturnUrl);
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                out.println(bcOrder.getHtml());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;
        case BD_WEB:
            bcOrder.setReturnUrl(bdReturnUrl);
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                response.sendRedirect(bcOrder.getUrl());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;

        case BD_WAP:
            bcOrder.setReturnUrl(bdReturnUrl);
            try {
                bcOrder = BCPay.startBCPay(bcOrder);
                out.println(bcOrder.getObjectId());
                Thread.sleep(3000);
                response.sendRedirect(bcOrder.getUrl());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;
		
        case PAYPAL_PAYPAL:
        	internationalOrder.setChannel(PAY_CHANNEL.PAYPAL_PAYPAL);
        	internationalOrder.setBillNo(billNo);
        	internationalOrder.setCurrency(PAYPAL_CURRENCY.USD);
        	internationalOrder.setTitle("paypal test");
        	internationalOrder.setTotalFee(1);
        	internationalOrder.setReturnUrl(paypalReturnUrl);
        	 try {
        		 internationalOrder = BCPay.startBCInternatioalPay(internationalOrder);
        		 out.println(internationalOrder.getObjectId());
        		 Thread.sleep(3000);
        		 response.sendRedirect(internationalOrder.getUrl());
             } catch (BCException e) {
                 log.error(e.getMessage(), e);
                 out.println(e.getMessage());
             }
             break;
        
        case PAYPAL_CREDITCARD:
            /*
             * 请传入用户信用卡信息，包括:cardNo、expireMonth、expireYear、cvv、firstName、lastName
             * 以及cardType(visa/mastercard/discover/amex)
             */
        	CreditCardInfo creditCardInfo = new CreditCardInfo();
        	creditCardInfo.setCardNo("5183182005528540");
        	creditCardInfo.setExpireMonth(11);
        	creditCardInfo.setExpireYear(19);
        	creditCardInfo.setCvv(350);
        	creditCardInfo.setFirstName("SAN");
        	creditCardInfo.setLastName("ZHANG");
        	creditCardInfo.setCardType(CARD_TYPE.mastercard);
        	internationalOrder.setBillNo(billNo);
        	internationalOrder.setChannel(PAY_CHANNEL.PAYPAL_CREDITCARD);
        	internationalOrder.setCreditCardInfo(creditCardInfo);
        	internationalOrder.setCurrency(PAYPAL_CURRENCY.USD);
        	internationalOrder.setTitle("paypal credit card test");
        	internationalOrder.setTotalFee(1);
        	try {
       			internationalOrder = BCPay.startBCInternatioalPay(internationalOrder);
       			out.println("PAYPAL_CREDITCARD 支付成功！");
       			out.println(internationalOrder.getObjectId());
       			out.println(internationalOrder.getCreditCardId());
       			request.getSession().setAttribute("creditCardId", internationalOrder.getCreditCardId());
            } catch (BCException e) {
                log.error(e.getMessage(), e);
                out.println(e.getMessage());
            }
            break;
            
        case PAYPAL_SAVED_CREDITCARD:
            Object creditCardId = request.getSession().getAttribute("creditCardId");
            if (creditCardId == null) {
                out.println("信用卡ID信息不存在，请先通过信用卡支付获取ID再进行快捷支付！");
            } else {
	        	internationalOrder.setBillNo(billNo);
	        	internationalOrder.setChannel(PAY_CHANNEL.PAYPAL_SAVED_CREDITCARD);
	        	internationalOrder.setCurrency(PAYPAL_CURRENCY.USD);
	        	internationalOrder.setTitle("PAYPAL_SAVED_CREDITCARD test");
	        	internationalOrder.setTotalFee(1);
	        	internationalOrder.setBillNo(request.getSession().getAttribute("creditCardId").toString());
	        	try {
	       			internationalOrder = BCPay.startBCInternatioalPay(internationalOrder);
	       			out.println(internationalOrder.getObjectId());
	       			out.println("PAYPAL_SAVED_CREDITCARD 支付成功！");
	            } catch (BCException e) {
	                log.error(e.getMessage(), e);
	                out.println(e.getMessage());
	            }
            }
            break;

        case WX_SCAN:
            break;
        case ALI:
            break;
        case ALI_APP:
            break;
        case ALI_SCAN:
            break;
        case ALI_OFFLINE_QRCODE:
            break;
        case UN:
            break;
        case UN_APP:
            break;
        case YEE:
            break;
        case JD:
            break;
        case KUAIQIAN:
            break;
        case BD:
            break;
        case BD_APP:
            break;
        case PAYPAL:
            break;
        case PAYPAL_SANDBOX:
            break;
        case PAYPAL_LIVE:
            break;
        default:
            break;
    }
%>
<%!
    String sendGet(String url) throws Exception {
        String result = "";
        BufferedReader in = null;
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
        // 设置通用的请求属性
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }
%>
<div align="center" id="qrcode">
</div>
</body>
<script src="../js/qrcode.js"></script>
<script type="text/javascript">
    function makeqrcode() {
        var qr = qrcode(10, 'L');
        qr.addData(codeUrl);
        qr.make();
        var wording = document.createElement('p');
        wording.innerHTML = "扫我，扫我";
        var code = document.createElement('DIV');
        code.innerHTML = qr.createImgTag();
        var element = document.getElementById("qrcode");
        element.appendChild(wording);
        element.appendChild(code);
    }
    var type = '<%=type%>';
    var codeUrl;
    var success = '<%=success%>'
    if (type == 'WX_NATIVE') {
        codeUrl = '<%=bcOrder.getCodeUrl()%>';
    }

    if (type == 'WX_NATIVE' || 'true' == success) {
        makeqrcode();
    }

</script>

<script type="text/javascript">
    callpay();
    function jsApiCall() {
        var data = {
            //以下参数的值由BCPayByChannel方法返回来的数据填入即可
            "appId": "<%=jsapiAppid%>",
            "timeStamp": "<%=timeStamp%>",
            "nonceStr": "<%=nonceStr%>",
            "package": "<%=jsapipackage%>",
            "signType": "<%=signType%>",
            "paySign": "<%=paySign%>"
        };
        alert(JSON.stringify(data));
        WeixinJSBridge.invoke(
                'getBrandWCPayRequest',
                data,
                function (res) {
                    alert(res.err_msg);
                    alert(JSON.stringify(res));
                    WeixinJSBridge.log(res.err_msg);
                }
        );
    }

    function callpay() {
        if (typeof WeixinJSBridge == "undefined") {
            if (document.addEventListener) {
                document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
            } else if (document.attachEvent) {
                document.attachEvent('WeixinJSBridgeReady', jsApiCall);
                document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
            }
        } else {
            jsApiCall();
        }
    }

</script>
</html>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Random"%>
<%@page import="com.sun.org.apache.xalan.internal.xsltc.compiler.sym"%>
<%@page import="cn.beecloud.BeeCloud"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="cn.beecloud.BCEumeration.PAY_CHANNEL"%>
<%@ page import="cn.beecloud.BCEumeration.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="java.util.Map" %>

<%
	/* *
	 功能：商户结算跳转至指定支付方式页面
	 版本：3.3
	 日期：2015-03-20
	 说明：
	 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

	 //***********页面功能说明***********
	 该页面可以在本机电脑测试。
	 //********************************
	 * */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>redirect</title>
</head>
<body>
	<%
		//以下代码用session获得交易信息，可由商户根据自己的项目决定实现方式
		//return_url示例（商户根据自身系统指定）
		String return_url = "http://localhost:8080/PC-Web-Pay-Demo/return_url.jsp";
		String front_url = "http://localhost:8080/PC-Web-Pay-Demo/front_url.jsp";
		String seller_email = "admin@beecloud.cn";
		
		//模拟商户的交易编号
		String bill_no = BCUtil.generateRandomUUIDPure();
		String subject = "water";
		String total_fee = "1";
		String body = "test";
		String show_url = (String) session.getAttribute("show_url");
		String anti_phishing_key = (String) session
				.getAttribute("anti_phishing_key");
		String exter_invoke_ip = (String) session
				.getAttribute("exter_invoke_ip");
		
		Map optional = new HashMap();
		optional.put("test", "test");

		String type = request.getParameter("paytype");

		BeeCloud.registerApp("0950c062-5e41-44e3-8f52-f89d8cf2b6eb", "a5571c5a-591e-4fb9-bd92-0283782af00d");
		//BCPayResult的type字段有OK和非OK两种，当type字段是OK时（对应值为0），bcPayResult包含支付所需的内容如html或者code_url或者支付成功信息,
		//当type的字段为非OK的时候，，bcPayResult包含通用错误和具体的错误信息。商户系统可以任意显示，打印或者记录日志。
		BCPayResult bcPayResult = new BCPayResult();
		
		if (type.equals("alipay")) {
			
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.ALI_WEB, 1, bill_no, "买水", null, return_url, "openid0000000000001", null, null);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErr_detail());
			}
		} else if (type.equals("alipayQr")) {
			
            bcPayResult = BCPay.startBCPay(PAY_CHANNEL.ALI_QRCODE, 1, bill_no, "买水", null, return_url, null, null, QR_PAY_MODE.MODE_BRIEF_FRONT);
            if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErr_detail());
			}
            
		} else if (type.equals("alipayWAP")) {
			
            bcPayResult = BCPay.startBCPay(PAY_CHANNEL.ALI_WAP, 1, bill_no, "买水", null, null, null, null, null);
            if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErr_detail());
			}
            
		} else if (type.equals("wechatQr")) {
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.WX_NATIVE, 1, bill_no, "买水", optional, null, null, null, null);
			if (bcPayResult.getType().ordinal() == 0) {
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErr_detail());
			}
		} else if (type.equals("wechatJSAPI")) {
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.WX_JSAPI, 1, bill_no, "买水", null, null, "o3kKrjlUsMnv__cK5DYZMl0JoAkY", null, null);
			System.out.println(bcPayResult.getType());
			if (bcPayResult.getType().ordinal() == 0) {
				Map<String, Object> map = bcPayResult.getWxJSAPIMap();
				JSONObject jsonObject = JSONObject.fromObject(map);
				String payArg = "<script type=\"text/javascript\" src=\"wxjsapi.js\"></script><script>callpay(" + jsonObject + ");</script>";
				out.println(payArg);
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErr_detail());
			}
		}
		
		else if (type.equals("unionpay")) {
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.UN_WEB, 1, bill_no, "买水", null, front_url, null, null, null);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErr_detail());
			}
		}
	%>
<div align="center" id="qrcode">
</div>
</body>
<script src="./qrcode.js"></script>
<script type="text/javascript">
    var type = '<%=type%>';
    var isCodeUrl = <%=bcPayResult.getType().ordinal()%>;
    var codeUrl = '<%=bcPayResult.getCode_url()%>';
            function makeqrcode() {
                var qr = qrcode(10, 'M');
                qr.addData(codeUrl);
                qr.make();
                var wording=document.createElement('p');
                wording.innerHTML = "扫我，扫我";
                var code=document.createElement('DIV');
                code.innerHTML = qr.createImgTag();
                var element=document.getElementById("qrcode");
                element.appendChild(wording);
                element.appendChild(code);
            }
    if (type == 'wechatQr' && isCodeUrl == 0) {
        makeqrcode();
    }
    
</script>
</html>
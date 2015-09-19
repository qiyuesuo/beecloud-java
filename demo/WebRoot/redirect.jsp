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
<%@ page import="java.util.*" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="java.util.Map" %>
<%@ page import="cn.beecloud.bean.TransferData"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="org.apache.log4j.*"%>
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
	
		Logger log = Logger.getLogger(this.getClass());
		//以下代码用session获得交易信息，可由商户根据自己的项目决定实现方式
		//return_url示例（商户根据自身系统指定）
		String yeeWebReturnUrl = "http://localhost:8080/PC-Pay-Demo/yeeWebReturnUrl.jsp";
		String jdReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/jdReturnUrl.jsp";
		String kqReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/kqReturnUrl.jsp";
		String aliReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/aliReturnUrl.jsp";
		String unFrontUrl = "http://localhost:8080/PC-Web-Pay-Demo/unFrontUrl.jsp";
		String sellerEmail = "admin@beecloud.cn";
		
		String wxJSAPAppId = "wx419f04c4a731303d";
		String wxJSAPIRedirectUrl = "http://apitest.beecloud.cn/demo/wxJSAPIRedirectUrl.jsp";
		String encodedWSJSAPIRedirectUrl = URLEncoder.encode(wxJSAPIRedirectUrl);
		
		//模拟商户的交易编号
		String billNo = BCUtil.generateRandomUUIDPure();
		String subject = "测试";
		String totalFee = "1";
		String body = "test";
		String showUrl = (String) session.getAttribute("showUrl");
		
		Map optional = new HashMap();
		optional.put("rui", "睿");

		String type = request.getParameter("paytype");

		BeeCloud.registerApp("c37d661d-7e61-49ea-96a5-68c34e83db3b", "c37d661d-7e61-49ea-96a5-68c34e83db3b");
		//BCPayResult的type字段有OK和非OK两种，当type字段是OK时（对应值为0），bcPayResult包含支付所需的内容如html或者code_url或者支付成功信息,
		//当type的字段为非OK的时候，，bcPayResult包含通用错误和具体的错误信息。商户系统可以任意显示，打印或者记录日志。
		BCPayResult bcPayResult = new BCPayResult();
		
		if (type.equals("alipay")) {
			
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.ALI_WEB, 1, billNo, "买水", optional, aliReturnUrl, null, null, null, 121);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("alipayQr")) {
			
            bcPayResult = BCPay.startBCPay(PAY_CHANNEL.ALI_QRCODE, 1, billNo, "买水", null, aliReturnUrl, null, null, QR_PAY_MODE.MODE_BRIEF_FRONT, 65);
            if (bcPayResult.getType().ordinal() == 0) {
            	out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
            
		} else if (type.equals("alipayWAP")) {
			
            bcPayResult = BCPay.startBCPay(PAY_CHANNEL.ALI_WAP, 1, billNo, "买水", null, aliReturnUrl, null, null, null, 121);
            if (bcPayResult.getType().ordinal() == 0) {
            	out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
            
		} else if (type.equals("wechatQr")) {
			//response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect");
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.WX_NATIVE, 1, billNo, "买水", null, null, null, null, null, 121);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("wechatJSAPI")) {
			String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wxJSAPAppId+ "&redirect_uri=" + encodedWSJSAPIRedirectUrl + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
			log.info("wx jsapi redirct url:" + redirectUrl);
			response.sendRedirect(redirectUrl);
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.WX_JSAPI, 1, billNo, "买水", null, null, "o3kKrjlUsMnv__cK5DYZMl0JoAkY", null, null, 121);
			System.out.println(bcPayResult.getType());
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				Map<String, Object> map = bcPayResult.getWxJSAPIMap();
				JSONObject jsonObject = JSONObject.fromObject(map);
				String payArg = "<script type=\"text/javascript\" src=\"wxjsapi.js\"></script><script>callpay(" + jsonObject + ");</script>";
				out.println(payArg);
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("unionpay")) {
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.UN_WEB, 1, billNo, "买矿泉水", optional, unFrontUrl, null, null, null, 121);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("alitransfer")) {
			List<TransferData> list = new ArrayList<TransferData>();
			TransferData data1 = new TransferData("transfertest11223", "13584809743", "袁某某", 1, "赏赐");
			TransferData data2 = new TransferData("transfertest11224", "13584809742", "张某某", 1, "赏赐");
			list.add(data1);
			list.add(data2);
			
			
			bcPayResult = BCPay.startTransfer(PAY_CHANNEL.ALI, billNo, "苏州比可网络科技有限公司", list);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				response.sendRedirect(bcPayResult.getUrl());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("yeeWap")) {
			BeeCloud.registerApp("230b89e6-d7ff-46bb-b0b6-032f8de7c5d0", "191418f6-c0f5-4943-8171-d07bfeff46b0");
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.YEE_WAP, 2, billNo, "买矿泉水", optional, null, null, null, null, 121);
			BeeCloud.registerApp("c37d661d-7e61-49ea-96a5-68c34e83db3b", "c37d661d-7e61-49ea-96a5-68c34e83db3b");
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				response.sendRedirect(bcPayResult.getUrl());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("yeeWeb")) {
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.YEE_WEB, 1, billNo, "买矿泉水", optional, yeeWebReturnUrl, null, null, null, 180);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				response.sendRedirect(bcPayResult.getUrl());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("jdWap")) {
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.JD_WAP, 2, billNo, "买矿泉水", optional, jdReturnUrl, null, null, null, null);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("jdWeb")) {
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.JD_WEB, 2, billNo, "买矿泉水", optional, jdReturnUrl, null, null, null, null);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("kqWeb")) {
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.KUAIQIAN_WEB, 1, billNo, "买矿泉水", optional, kqReturnUrl, null, null, null, null);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("kqWap")) {
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.KUAIQIAN_WAP, 1, billNo, "买矿泉水", optional, kqReturnUrl, null, null, null, null);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				out.println(bcPayResult.getHtml());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("bdWeb")) {
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.BD_WEB, 1, billNo, "买矿泉水", optional, kqReturnUrl, null, null, null, 121);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				response.sendRedirect(bcPayResult.getUrl());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
			}
		} else if (type.equals("bdWap")) {
			bcPayResult = BCPay.startBCPay(PAY_CHANNEL.BD_WAP, 1, billNo, "买矿泉水", optional, kqReturnUrl, null, null, null, 121);
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Thread.sleep(5000);
				response.sendRedirect(bcPayResult.getUrl());
			}
			else {
				//handle the error message as you wish！
				out.println(bcPayResult.getErrMsg());
				out.println(bcPayResult.getErrDetail());
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
    var codeUrl;
    if (type == 'wechatQr') {
    	codeUrl = '<%=bcPayResult.getCodeUrl()%>';
    } else if (type == 'aliOfflineQr')
     	codeUrl = '<%=bcPayResult.getAliQrCode()%>';
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
    if ((type == 'wechatQr' || type == "aliOfflineQr") && isCodeUrl == 0) {
        makeqrcode();
    }
    
</script>
</html>
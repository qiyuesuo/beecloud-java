<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.net.URL" %>
<%@page import="java.net.HttpURLConnection" %>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="org.apache.log4j.*" %>
<%@ page import="cn.beecloud.*"%>
<%@ page import="cn.beecloud.BCEumeration.PAY_CHANNEL"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>WX JSAPI Redirect Url/title>
</head>
<body>
<%

	Logger log = Logger.getLogger(this.getClass());

	String billNo = BCUtil.generateRandomUUIDPure();
	String subject = "测试";
	String totalFee = "1";
	String body = "test";
	String showUrl = (String) session.getAttribute("showUrl");
	
	Map optional = new HashMap();
	optional.put("rui", "睿");
	
	String type = request.getParameter("paytype");
	
	BeeCloud.registerApp("c37d661d-7e61-49ea-96a5-68c34e83db3b", "c37d661d-7e61-49ea-96a5-68c34e83db3b");
	

	String appid = request.getParameter("appid");
 	String secret = "53e3943476118a3dff21fb95848de6d7";
	String code = request.getParameter("code");
	if (code != null) {
		String result = sendGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=CODE&grant_type=authorization_code");
		log.info("result:" + result);
		JSONObject resultObject = JSONObject.fromObject(result);
		if (resultObject.containsKey("errcode")) {
			out.println("获取access_token出错！错误信息为：" + resultObject.get("errmsg").toString());
		} else {
			String openId = resultObject.get("openid").toString();
			log.info("openid:" + openId);
			
			BCPayResult bcPayResult = BCPay.startBCPay(PAY_CHANNEL.WX_JSAPI, 1, billNo, "买水", optional, null, openId, null, null, 121);
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
		}
	} else {
		out.println("code为空，code获取失败！");
	}
%>

<%! 
	String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		URL realUrl = new URL(url);
	    // 打开和URL之间的连接
	    HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
	    // 设置通用的请求属性
	    conn.setRequestProperty("Content-Type", "application/json");
	    conn.setReadTimeout(5000);
	    conn.setConnectTimeout(5000);
	    conn.setRequestMethod("GET");
	//    conn.setDoOutput(true);
	//    conn.setDoInput(true);
	    // 获取URLConnection对象对应的输出流
	//    out = new PrintWriter(conn.getOutputStream());
	//    // flush输出流的缓冲
	//    out.flush();
	//    // 定义BufferedReader输入流来读取URL的响应
	    in = new BufferedReader(
	            new InputStreamReader(conn.getInputStream()));
	    String line;
	    while ((line = in.readLine()) != null) {
	        result += line;
	    }
	}
%>
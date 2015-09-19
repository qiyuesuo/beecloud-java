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
<title>WX JSAPI Redirect Url</title>
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
	

	String appid = "wx419f04c4a731303d";
	log.info("appid:" + appid);
 	String secret = "21e4b4593ddd200dd77c751f4b964963";
	String code = request.getParameter("code");
	log.info("code:" + code);
	
	String jsapiString = "";
	String jsapiAppid ="";
	String timeStamp ="";
	String nonceStr = "";
	String jsapipackage = "";
	String signType = "";
	String paySign = "";
	
	if (code != null) {
		String result = sendGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code");
		log.info("result:" + result);
		JSONObject resultObject = JSONObject.fromObject(result);
		if (resultObject.containsKey("errcode")) {
			out.println("获取access_token出错！错误信息为：" + resultObject.get("errmsg").toString());
		} else {
			String openId = resultObject.get("openid").toString();
			log.info("openid:" + openId);
			
			
			BCPayResult bcPayResult = BCPay.startBCPay(PAY_CHANNEL.WX_JSAPI, 1, billNo, "买水", optional, null, "o3kKrjmMFV4wmSSwSNircKD9EXqc", null, null, 121);
			log.info("bcPayResult" + bcPayResult.getType());
			System.out.println("bcPayResult:" + bcPayResult.getType());
			if (bcPayResult.getType().ordinal() == 0) {
				out.println(bcPayResult.getObjectId());
				Map<String, Object> map = bcPayResult.getWxJSAPIMap();
				
				jsapiAppid =map.get("appId").toString();
				timeStamp =map.get("timeStamp").toString();
				nonceStr = map.get("nonceStr").toString();
				jsapipackage = map.get("package").toString();
				signType = map.get("signType").toString();
				paySign = map.get("paySign").toString();
				
				
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
	String sendGet(String url) throws Exception{
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
	    in = new BufferedReader(
	            new InputStreamReader(conn.getInputStream()));
	    String line;
	    while ((line = in.readLine()) != null) {
	        result += line;
	    }
	    return result;
	}
%>
</body>
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
	        function(res){
	        	alert(res.err_msg);
                alert(JSON.stringify(res));
	                WeixinJSBridge.log(res.err_msg);
	        }
	    );
	}

	function callpay() {
		if (typeof WeixinJSBridge == "undefined"){
		    if( document.addEventListener ){
		        document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
		    } else if (document.attachEvent){
		        document.attachEvent('WeixinJSBridgeReady', jsApiCall); 
		        document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
		    }
		} else {
		    jsApiCall();
		}
	}
	
	
	
</script>
</html>
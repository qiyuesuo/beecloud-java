<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStream" %>
<%@page import="com.sun.org.apache.xpath.internal.operations.Bool"%>
<%@page import="java.io.UnsupportedEncodingException" %>
<%@page import="org.apache.commons.codec.digest.DigestUtils" %>
<%@ page import="java.util.*"%>
<%@ page import="cn.beecloud.*"%>
<%@ page import="org.apache.log4j.*"%>
<%
	/* *
	 功能：BeeCloud服务器异步通知页面
	 日期：2015-03-20
	 说明：
	 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 该代码仅供学习和研究使用，只是提供一个参考。

	 //***********页面功能说明***********
	 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
	 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
	 如果没有收到该页面返回的 success 信息，BeeCloud会在36小时内按一定的时间策略重发通知
	 //********************************
	 * */
%>


<%!
	Logger log = Logger.getLogger(this.getClass());
	boolean verify(String sign, String text, String key, String input_charset) {
	    text = text + key;
	    String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
	    log.info("mysign:" + mysign);
	    
	    long timeDifference = System.currentTimeMillis() - Long.valueOf(key);
	    log.info("timeDifference:" +  timeDifference);
	    if (mysign.equals(sign) && timeDifference <= 300000) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	boolean verifySign(String sign, String timestamp) {
		log.info("sign:"+ sign);
		log.info("timestamp:" +timestamp);
		return verify(sign, BCCache.getAppID() + BCCache.getAppSecret(),
	            timestamp, "UTF-8");
	}

	
	
	byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
%>

<%
	BeeCloud.registerApp("c5d1cba1-5e3f-4ba0-941d-9b0a371fe719", "39a7a518-9ac8-4a9e-87bc-7885f33cf18c");
	StringBuffer json = new StringBuffer();
	String line = null;
	
	try {
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			json.append(line);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	JSONObject jsonObj = JSONObject.fromObject(json.toString());

	String sign = jsonObj.getString("sign");

	String timestamp = jsonObj.getString("timestamp");

	boolean status = verifySign(sign, timestamp);
	
	if (status) {//验证成功
		out.println("success"); //请不要修改或删除
		//进行业务处理
	} else {//验证失败
		out.println("fail");
	}
%>

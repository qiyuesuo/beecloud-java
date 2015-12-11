<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Enumeration" %>
<%
  /* 
         功能：易宝移动网页支付完成后同步跳转至本页面
         版本：1.0
         日期：2015-07-20
         说明：
         以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
   */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; UTF-8">
    <title>Yee WAP Return Url</title>
</head>
<body>
<%	
	//遍历所有字段代码样例
	Enumeration<String> enu = request.getParameterNames();
	while(enu.hasMoreElements()) {
		String key = enu.nextElement();
		System.out.println("key:" + key + ";value:" + request.getParameter(key));
	}
	
    String data = request.getParameter("data");
    String encryptkey = request.getParameter("encryptkey");

    out.println("<h3>易宝移动网易支付成功，商户应自行实现成功逻辑！</h3>");

    /**
               以下代码需要商户自行传入私钥"merchantPrivateKey"实现

     String yeepayAESKey = RSA.decrypt(encryptkey, merchantPrivateKey);
     String decryptData = AES.decryptFromBase64(data, yeepayAESKey);
     Map<String, Object> decryptDataMap = JSON.parseObject(decryptData,TreeMap.class);

     out.println(decryptDataMap.get("orderid"));
     out.println(decryptDataMap.get("yborderid"));
     */


%>
</body>
</html>
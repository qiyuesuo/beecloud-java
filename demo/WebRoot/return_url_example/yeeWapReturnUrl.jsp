<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; UTF-8">
    <title>Yee WAP Return Url</title>
</head>
<body>
<%
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Enumeration" %>
<%
  /* 
         功能：易宝网页支付完成后同步跳转至本页面
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
    <title>Yee Web Return Url</title>
</head>
<body>
<%! String formatString(String text) {
    return (text == null) ? "" : text.trim();
}
%>
<%	
	//遍历所有字段代码样例
	Enumeration<String> enu = request.getParameterNames();
	while(enu.hasMoreElements()) {
		String key = enu.nextElement();
		System.out.println("key:" + key + ";value:" + request.getParameter(key));
	}
	
    String p1_MerId = formatString(request.getParameter("p1_MerId"));
    String r0_Cmd = formatString(request.getParameter("r0_Cmd"));
    String r1_Code = formatString(request.getParameter("r1_Code"));
    String r2_TrxId = formatString(request.getParameter("r2_TrxId"));
    String r3_Amt = formatString(request.getParameter("r3_Amt"));
    String r4_Cur = formatString(request.getParameter("r4_Cur"));
    String r5_Pid = formatString(request.getParameter("r5_Pid"));
    String r6_Order = formatString(request.getParameter("r6_Order"));
    String r7_Uid = formatString(request.getParameter("r7_Uid"));
    String r8_MP = formatString(request.getParameter("r8_MP"));
    String r9_BType = formatString(request.getParameter("r9_BType"));
    String rb_BankId = formatString(request.getParameter("rb_BankId"));
    String ro_BankOrderId = formatString(request.getParameter("ro_BankOrderId"));
    String rp_PayDate = formatString(request.getParameter("rp_PayDate"));
    String rq_CardNo = formatString(request.getParameter("rq_CardNo"));
    String ru_Trxtime = formatString(request.getParameter("ru_Trxtime"));
    String rq_SourceFee = formatString(request.getParameter("rq_SourceFee"));
    String rq_TargetFee = formatString(request.getParameter("rq_TargetFee"));
    String hmac = formatString(request.getParameter("hmac"));

    if (r9_BType.equals("1")) {
        out.println("<h3>易宝PC网页支付成功，商户应自行实现成功逻辑！</h3>");
        out.println("order no:" + r6_Order);
        //handle othre return parameter as you wish
        return;
    }
%>
</body>
</html>
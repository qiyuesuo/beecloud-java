package cn.beecloud;

/**
 * User: joseph
 * Date: 14/11/30
 */
public class TestConstant {
    public static String KTestAppID = "c37d661d-7e61-49ea-96a5-68c34e83db3b";
    public static String kTestAppSecret = "c37d661d-7e61-49ea-96a5-68c34e83db3b";
    public static String kTestTable = "java_sdk_test";
    public static String outRefundNo = "2015062611111";
    public static String BILL_NO_WITH_SPECIAL_CHARACTER = "billno123!@#";
	public static String subject = "water";
	public static String totalFee = "1";
	public static String body = "test";
	public static String ASSERT_MESSAGE = "不一致";
	public static String TITLE_WITH_CHARACTER＿GREATER_THAN_32 = "超过16个中文字符的标题是不被接受的";
	public static String REFUN_NO_SERIAL_NUMBER_GREATER_THAN_24 = "1234567890123456789012345";
	public static String REFUN_NO_SERIAL_NUMBER_LESSER_THAN_3 = "01";
	
	
	public static String yeeWebReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/yeeWebReturnUrl.jsp";
	public static String yeeWapReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/yeeWapReturnUrl.jsp";
	public static String jdWebReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/jdWebReturnUrl.jsp";
	public static String jdWapReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/jdWapReturnUrl.jsp";
	public static String kqReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/kqReturnUrl.jsp";
	public static String aliReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/aliReturnUrl.jsp";
	public static String unReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/unReturnUrl.jsp";
	public static String bdReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/bdReturnUrl.jsp";

	public static Integer billTimeOut = 300;
	
	public static enum CHANNEL_TYPE {
        WX_APP,
        WX_NATIVE,
        WX_JSAPI,
        ALI_APP,
        ALI_WEB,
        ALI_QRCODE,
        ALI_OFFLINE_QRCODE,
        UN_APP,
        UN_WEB,
        KUAIQIAN_WAP,
        KUAIQIAN_WEB,
        PAYPAL,
        YEE_WAP,
        YEE_WEB,
        JD_WEB,
        JD_WAP
    }
	
	public static enum REQUEST_TYPE {
        GET,
        POST
    }
	
}

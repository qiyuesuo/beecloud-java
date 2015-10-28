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
    public static String TEST_SERVER_URL_V1 = "http://apihz.beecloud.cn:8080/1/";
    public static String TEST_APP_ALI_ID = "2088711322600312";
    public static String TEST_APP_ALI_KEY = "xvy48u7n0zvwzm1zcthk6tbjcps5sghb";
    public static String ALI_PAY_NOTIFY = "pay/callback/Ali";
    public static String[] TEST_SERVER_URL_ARRAY = {"http://123.57.71.81:8080/1/", "http://115.28.40.236:8080/1/",
        "http://123.57.71.81:8080/1/","http://121.41.120.98:8080/1/"};
    public static int READ_TIMEOUT = 10000;
    public static String BILL_NO_WITH_SPECIAL_CHARACTER = "billno123!@#";
    public static String TEST_APP_WXMP_ID = "wxf1aa465362b4c8f1";
    public static String TEST_MECH_ID = "1240421502";
    
    
	public static String subject = "water";
	public static String totalFee = "1";
	public static String body = "test";
	public static String ASSERT_MESSAGE = "不一致";
	
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

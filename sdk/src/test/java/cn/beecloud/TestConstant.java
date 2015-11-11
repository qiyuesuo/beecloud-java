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
	public static String REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24 = "1234567890123456789012345";
	public static String REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3 = "01";
	public static String REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER = "特殊字符";
	public static String ALI_TRANSFER_ACCOUNT_NAME = "苏州比可网络科技有限公司";
	public static String ALI_TRANSFER_BATCH_NO_WITH_SPECIAL_CHARACTER = "batchNo流水号001";
	public static String ALI_TRANSFER_RECEIVER_ACCOUNT_1 = "13584809649";
	public static String ALI_TRANSFER_RECEIVER_ACCOUNT_2 = "13584804872";
	public static String ALI_TRANSFER_RECEIVER_NAME_1 = "袁某某";
	public static String ALI_TRANSFER_RECEIVER_NAME_2 = "张某某";
	public static String TRANSFER_NOTE = "下发";
	public static String INVALID_TRANSFER_ID = "包含中文aaabbccdd";
	public static String INVALID_OBJECT_ID = "aaabbbccc!!!@@@###";
	public static String VALID_REFUND_OBJECT_ID = "9ed1cec8-6f94-4666-b680-fbe0265d0867";
	public static String NOT_EXIST_BILL_NO = "aaabbbcccdddeee111222333";
	
	
	
	public static String yeeWebReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/yeeWebReturnUrl.jsp";
	public static String yeeWapReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/yeeWapReturnUrl.jsp";
	public static String jdWebReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/jdWebReturnUrl.jsp";
	public static String jdWapReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/jdWapReturnUrl.jsp";
	public static String kqReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/kqReturnUrl.jsp";
	public static String aliReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/aliReturnUrl.jsp";
	public static String unReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/unReturnUrl.jsp";
	public static String bdReturnUrl = "http://localhost:8080/PC-Web-Pay-Demo/bdReturnUrl.jsp";

	public static Integer billTimeOut = 300;
	
	public static String MOCK_CODE_URL = "weixin://weixinmockurl001";
	public static String MOCK_OBJECT_ID = "abcdefg1234556890";
	public static String MOCK_PAY_URL = "http://www.163.com";
	public static String MOCK_PAY_HTML = "<form></form><script>form.submit()</script>";
	
	public final static String BILL_NO_FORMAT_INVALID =
			"billNo 是一个长度介于8至32字符的数字字母字符串！";
	
	public final static String BATCH_NO_FORMAT_INVALID =
			"batchNo 是一个长度在11到32个字符的数字字母字符串！";
	
	public final static String PAY_PARAM_EMPTY =
			"支付参数不能为空！";
	
	public final static String REFUND_PARAM_EMPTY = 
			"退款参数不能为空！";
	
	public final static String QUERY_PARAM_EMPTY = 
			"查询参数不能为空！";
	
	public final static String BILL_NO_EMPTY =
			"billNo 必填！";
	
	public final static String BATCH_NO_EMPTY =
			"batchNo 必填！";
	
	public final static String TRANSFER_DATA_EMPTY =
			"transferData 必填！";

	public final static String TRANSFER_ID_EMPTY =
			"transferId 不能为空！";
	
	public final static String RECEIVER_ACCOUNT_EMPTY =
			"receiverAccount 不能为空！";
	
	public final static String RECEIVER_NAME_EMPTY =
			"receiverName 不能为空！";
	
	public final static String TRANSFER_FEE_EMPTY =
			"transferFee 不能为空！";
	
	public final static String TRANSFER_FEE_INVALID = 
			"transferFee 必须大于0！";
	
	public final static String TRANSFER_NOTE_EMPTY =
			"transferNote 不能为空！";
	
	public final static String ACCOUNT_NAME_EMPTY =
			"accountName 必填！";
	
	public final static String TITLE_EMPTY =
			"title 必填！";
	
	public final static String TOTAL_FEE_EMPTY =
			"totalFee 必填！";
	
	public final static String REFUND_FEE_EMPTY =
			"refundFee 必填！";
	
	public final static String REFUND_FEE_INVALID =
			"refundFee 必须大于零！";
	
	public final static String QR_PAY_MODE_EMPTY =
			"qrPayMode 必填！";
	
	public final static String RETURN_URL_EMPTY = 
			"returnUrl 必填！";
	
	public final static String REFUND_NO_EMPTY =
			"refundNo 必填！";
	
	public final static String CHANNEL_EMPTY =
			"channel 必填！";
	
	public final static String YEE_NOBANCARD_FACTOR_EMPTY =
			"cardNo, cardPwd, frqid 必填！";
	
	public final static String REFUND_NO_FORMAT_INVALID =
			"refundNo 是格式为当前日期加3-24位数字字母（不能为000）流水号的字符串！ ";
	
	public final static String TITLE_FORMAT_INVALID =
			"title 是一个长度不超过32字节的字符串！";
	
	public final static String LIMIT_FORMAT_INVALID =
			"limit 的最大长度为50！";
	
	public final static String OPENID_EMPTY =
			"openid 必填！";
	
	public final static String CHANNEL_INVALID_FOR_REFUND =
			"退款只支持WX, UN, ALI !";
	
	public final static String TRANSFER_ID_FORMAT_EMPTY = 
			"transferId 是一个长度不超过32字符的数字字母字符串！";
	
	public final static String TRANSFER_LIST_SIZE_INVALID = 
			"transferData 长度不能超过1000！";
	
	public final static String CHANNEL_SUPPORT_INVALID =
			"批量打款仅支持ALI";
	
	public static String ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN = 
			"BCException未抛出！";
	
	public final static String BILL_TIME_OUT_ZERO =
			"billTimeout不能为0！";
	
	public static enum CHANNEL_TYPE {
		WX,
        WX_APP,
        WX_NATIVE,
        WX_JSAPI,
        ALI,
        ALI_APP,
        ALI_WEB,
        ALI_QRCODE,
        ALI_OFFLINE_QRCODE,
        UN,
        UN_APP,
        UN_WEB,
        KUAIQIAN,
        KUAIQIAN_WAP,
        KUAIQIAN_WEB,
        PAYPAL,
        YEE,
        YEE_WAP,
        YEE_WEB,
        JD,
        JD_WEB,
        JD_WAP,
        BD,
        BD_WEB,
        BD_WAP,
        BD_APP
    }
	
	public static enum REQUEST_TYPE {
        GET,
        POST
    }
	
}

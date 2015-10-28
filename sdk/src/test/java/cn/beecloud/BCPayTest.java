package cn.beecloud;



import static junit.framework.Assert.assertEquals;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.QR_PAY_MODE;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.*;

public class BCPayTest {
	
	protected Client client;
	private String billNo;
	private String subject;
	private String refundNo;
	private Map<String, Object> payOptional = new HashMap<String, Object>();
	private Map<String, Object> refundOptional = new HashMap<String, Object>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}  

	@Before
	public void setUp() throws Exception {
		BeeCloud.registerApp(TestConstant.KTestAppID, TestConstant.kTestAppSecret);
		TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{tm}, null);
        ClientConfig configuration = new ClientConfig();
        configuration = configuration.property(ClientProperties.CONNECT_TIMEOUT, TestConstant.READ_TIMEOUT);
        configuration = configuration.property(ClientProperties.READ_TIMEOUT, TestConstant.READ_TIMEOUT);
        client = ClientBuilder.newBuilder().sslContext(sslContext).withConfig(configuration).build();
        client.register(JacksonFeature.class);
        TestConstant.TEST_SERVER_URL_V1 = TestUtil.randomServerUrl();
	}

	@Test
	public void testAliWebPay() {  
		billNo = BCUtil.generateRandomUUIDPure();
		System.out.println("billNO:" + billNo);
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "ALI_WEB unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_WEB, 1, billNo, subject);
		payOptional.put("aliWebPay", "aliWebPay");
		param.setReturnUrl(TestConstant.aliReturnUrl);
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.ALI_WEB);
		
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		refundOptional.put("aliWebRefund", "aliWebRefund");
		refundParam.setChannel(PAY_CHANNEL.ALI);
		
		testRefund(refundParam, PAY_CHANNEL.ALI);
		
		testQueryBill();
	}
	
	@Test
	public void testAliQrcodePay() {  
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "ALI_QRCODE unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_QRCODE, 1, billNo, subject);
		optional.put("aliQrCodePay", "aliQrCodePay");
		param.setReturnUrl(TestConstant.aliReturnUrl);
		param.setOptional(optional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.ALI_QRCODE);
	}
	
	@Test
	public void testWXNative() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		System.out.println("billNO:" + billNo);
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "WX_NATIVE unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.WX_NATIVE, 1, billNo, subject);
		optional.put("wxNativePay", "wxNativePay");
		param.setOptional(optional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.WX_NATIVE);
		
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		optional.clear();
		optional.put("wxNativeRefund", "wxNativeRefund");
		refundParam.setChannel(PAY_CHANNEL.WX);
		
		testRefund(refundParam, PAY_CHANNEL.WX);
	
	}
	
	@Test
	public void testQueryRefund() {
		BCRefundQueryParameter param = new BCRefundQueryParameter();
		BCQueryResult bcQueryResult = BCPay.startQueryRefund(param);
		System.out.println("test1");
	}
	
	@Test
	public void testQueryRefundStatus() {
		BCQueryStatusResult result = BCPay.startRefundUpdate(PAY_CHANNEL.WX, "201507149424f344");
		System.out.println("test1");
	}
	
	@Test
	public void testTransfer() {
		List<TransferData> list = new ArrayList<TransferData>();
		TransferData data1 = new TransferData("transfertest11221", "13584809743", "袁某某", 1, "赏赐");
		TransferData data2 = new TransferData("transfertest11222", "13584809742", "张某某", 1, "赏赐");
		list.add(data1);
		list.add(data2);
		
		
		BCPayResult result = BCPay.startTransfer(PAY_CHANNEL.ALI, "transfertest1122transfertest2233", "13861331391", list);
		System.out.println("test transfer!");
	}
	
	@Test
	public void testQueryBillById() {
		BCQueryResult result = BCPay.startQueryBillById("21c295fe-0f74-4697-b403-983ec61230ab");
		System.out.println(result.getOrder());
		System.out.println("test query by id!" + result);
	}
	
	@Test
	public void testQueryRefundById() {
		BCQueryResult result = BCPay.startQueryRefundById("89ef90dd-9670-4104-a4fd-117a129b9c65");
		System.out.println(result.getRefund());
		System.out.println("test refund by id!" + result);
	}
	
	@SuppressWarnings("deprecation")
	private void testPay(BCPayParameter param, PAY_CHANNEL channel) {
		BCPayResult result = BCPay.startBCPay(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		
		param.setChannel(null);
		result = BCPay.startBCPay(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		param.setChannel(channel);
		
		
		param.setTotalFee(null);
		result = BCPay.startBCPay(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		param.setTotalFee(1);
		
		param.setBillNo(null);
		result = BCPay.startBCPay(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		param.setBillNo(billNo);
		
		param.setTitle(null);
		result = BCPay.startBCPay(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		param.setTitle(subject);
		
		param.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
		result = BCPay.startBCPay(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		param.setBillNo(billNo);
		
		param.setBillNo(billNo.substring(0, 7));
		System.out.println("xiaoyu 8:" + param.getBillNo());
		result = BCPay.startBCPay(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		param.setBillNo(billNo);
		
		param.setBillNo(billNo + "A");
		result = BCPay.startBCPay(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		param.setBillNo(billNo);
		
		if(param.getBillTimeout() != null) {
			param.setBillTimeout(0);
			result = BCPay.startBCPay(param);
			assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
			param.setBillTimeout(TestConstant.billTimeOut);
		}
		
		if (channel.equals(PAY_CHANNEL.ALI_WEB) || channel.equals(PAY_CHANNEL.ALI_QRCODE) || channel.equals(PAY_CHANNEL.UN_WEB)
				|| channel.equals(PAY_CHANNEL.JD_WAP) || channel.equals(PAY_CHANNEL.JD_WEB)) {
			String returnUrl = param.getReturnUrl();
			param.setReturnUrl(null);
			result = BCPay.startBCPay(param);
			assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
			param.setReturnUrl(returnUrl);
		}
		
		if (channel.equals(PAY_CHANNEL.WX_JSAPI)) {
			String openId = param.getOpenId();
			param.setOpenId(null);
			result = BCPay.startBCPay(param);
			assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
			param.setOpenId(openId);
		}
		
		if (channel.equals(PAY_CHANNEL.ALI_QRCODE)) {
			QR_PAY_MODE qrPayMode = param.getQrPayMode();
			param.setQrPayMode(null);
			result = BCPay.startBCPay(param);
			assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
			param.setQrPayMode(qrPayMode);
		}
		
		if (channel.equals(PAY_CHANNEL.YEE_NOBANKCARD)) {
			String cardNo = param.getCardNo();
			param.setCardNo(null);
			result = BCPay.startBCPay(param);
			assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
			param.setCardNo(cardNo);
			
			String cardpwd = param.getCardPwd();
			param.setCardPwd(null);
			result = BCPay.startBCPay(param);
			assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
			param.setCardPwd(cardpwd);
			
			String frqid = param.getFrqid();
			param.setFrqid(null);
			result = BCPay.startBCPay(param);
			assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
			param.setFrqid(frqid);
		}
	}
	
	@SuppressWarnings("deprecation")
	private void testRefund(BCRefundParameter refundParam, PAY_CHANNEL ali) {
		System.out.println("refundNo:" + refundNo);

		refundParam.setBillNo(null);
		BCPayResult result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setBillNo(billNo);
		
		refundParam.setRefundFee(null);
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setRefundFee(1);
		
		refundParam.setRefundNo(null);
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setRefundNo(refundNo);
		
		refundParam.setBillNo(billNo.substring(0, 7));
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setBillNo(billNo);
		
		refundParam.setBillNo(billNo + "A");
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setBillNo(billNo);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_WEEK, -1);
		String date = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		refundParam.setRefundNo(date + BCUtil.generateNumberWith3to24digitals());
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setRefundNo(refundNo);
		
		date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		refundParam.setRefundNo(date + "000");
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setRefundNo(refundNo);
		
		result = ValidationUtil.validateBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
	}
	
	@SuppressWarnings("deprecation")
	private void testQueryBill() {
		BCQueryParameter param = new BCQueryParameter();
		BCQueryResult result = BCPay.startQueryBill(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()<=10);
		
		param.setChannel(PAY_CHANNEL.ALI);
		result = BCPay.startQueryBill(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()<=10);
		param.setChannel(null);
		
		param.setBillNo(billNo);
		result = BCPay.startQueryBill(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()<=10);
		param.setBillNo(null);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, +1);
		param.setStartTime(cal.getTime());
		result = BCPay.startQueryBill(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()<=10);
		param.setStartTime(null);
	}
	
	public static BCRefundParameter refundParamEqualsReport(BCRefundParameter param) {
	  EasyMock.reportMatcher(new RefundParamEquals());
	  return param;
	}
	
	
}

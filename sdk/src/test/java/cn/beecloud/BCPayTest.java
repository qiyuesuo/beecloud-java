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
	private String batchNo;
	private String transferId1;
	private String transferId2;
	private Map<String, Object> payOptional = new HashMap<String, Object>();
	private Map<String, Object> refundOptional = new HashMap<String, Object>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}  

	@Before
	public void setUp() throws Exception {
		BeeCloud.registerApp(TestConstant.KTestAppID, TestConstant.kTestAppSecret);
	}

	@Test
	public void testAliWeb() {  
		billNo = BCUtil.generateRandomUUIDPure();
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
		
		testQueryBill(PAY_CHANNEL.ALI);
	}
	
	@Test
	public void testAliQrcode() {  
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "ALI_QRCODE unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_QRCODE, 1, billNo, subject);
		payOptional.put("aliQrCodePay", "aliQrCodePay");
		param.setReturnUrl(TestConstant.aliReturnUrl);
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		param.setQrPayMode(QR_PAY_MODE.MODE_BRIEF_FRONT);
		
		testPay(param, PAY_CHANNEL.ALI_QRCODE);
	}
	
	@Test
	public void testAliWap() {  
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "ALI_WAP unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_WAP, 1, billNo, subject);
		payOptional.put("aliWapPay", "aliWapPay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.ALI_WAP);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAliTransfer() {  
		batchNo = BCUtil.generateRandomUUIDPure();
		transferId1 = BCUtil.generateRandomUUIDPure();
		transferId2 = BCUtil.generateRandomUUIDPure();
		List<TransferData> list = new ArrayList<TransferData>();
		
		BCPayResult result = BCPay.startTransfer(null, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, null, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, null, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, null);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
	
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, TestConstant.ALI_TRANSFER_BATCH_NO_WITH_SPECIAL_CHARACTER, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo + "A", TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo.substring(0, 10), TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		
		TransferData data1 = new TransferData(null, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, TestConstant.TRANSFER_NOTE);
		list.add(data1);
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		list.remove(data1);
		
		data1 = new TransferData(transferId1, null, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, TestConstant.TRANSFER_NOTE);
		list.add(data1);
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		list.remove(data1);
		
		data1 = new TransferData(transferId1, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, null, 1, TestConstant.TRANSFER_NOTE);
		list.add(data1);
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		list.remove(data1);
		
		data1 = new TransferData(transferId1, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, null, TestConstant.TRANSFER_NOTE);
		list.add(data1);
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		list.remove(data1);
		
		data1 = new TransferData(transferId1, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, null);
		list.add(data1);
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		list.remove(data1);
		
		data1 = new TransferData("", TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, TestConstant.TRANSFER_NOTE);
		list.add(data1);
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		list.remove(data1);
		
		data1 = new TransferData(transferId1 + "A", TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, TestConstant.TRANSFER_NOTE);
		list.add(data1);
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		list.remove(data1);
		
		data1 = new TransferData(TestConstant.INVALID_TRANSFER_ID, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, TestConstant.TRANSFER_NOTE);
		list.add(data1);
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		list.remove(data1);
		
		data1 = new TransferData(transferId1, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, -1, TestConstant.TRANSFER_NOTE);
		list.add(data1);
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		list.remove(data1);
		
		data1 = new TransferData(transferId1, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 0, TestConstant.TRANSFER_NOTE);
		list.add(data1);
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		list.remove(data1);
		
		data1 = new TransferData(transferId1, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, TestConstant.TRANSFER_NOTE);
		TransferData data2 = new TransferData(transferId2, TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_2, TestConstant.ALI_TRANSFER_RECEIVER_NAME_2, 1, TestConstant.TRANSFER_NOTE);
		list.add(data1);
		list.add(data2);
		result = BCPay.startTransfer(PAY_CHANNEL.ALI, batchNo, TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
	}
	
	@Test
	public void testWXNative() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "WX_NATIVE unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.WX_NATIVE, 1, billNo, subject);
		payOptional.put("wxNativePay", "wxNativePay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.WX_NATIVE);
		
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		refundOptional.put("wxNativeRefund", "wxNativeRefund");
		refundParam.setChannel(PAY_CHANNEL.WX);
		
		testRefund(refundParam, PAY_CHANNEL.WX);
	}
	
	@Test
	public void testWXJSAPI() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "WX_NATIVE unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.WX_NATIVE, 1, billNo, subject);
		payOptional.put("wxNativePay", "wxNativePay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.WX_NATIVE);
		
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		refundOptional.put("wxNativeRefund", "wxNativeRefund");
		refundParam.setChannel(PAY_CHANNEL.WX);
		
		testRefund(refundParam, PAY_CHANNEL.WX);
	}
	
	@Test
	public void testUNWeb() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "UN_WEB unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.UN_WEB, 1, billNo, subject);
		payOptional.put("unWebPay", "unWebPay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.UN_WEB);
		
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		refundOptional.put("unWebRefund", "unWebRefund");
		refundParam.setChannel(PAY_CHANNEL.UN);
		
		testRefund(refundParam, PAY_CHANNEL.UN);
	}
	
	@Test
	public void testYeeWeb() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "YEE_WEB unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.YEE_WEB, 1, billNo, subject);
		payOptional.put("yeeWebPay", "yeeWebPay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.YEE_WEB);
		
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		refundOptional.put("yeeWebRefund", "yeeWebRefund");
		refundParam.setChannel(PAY_CHANNEL.YEE);
		
		testRefund(refundParam, PAY_CHANNEL.YEE);
	}
	
	@Test
	public void testYeeWap() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "YEE_WAP unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.YEE_WAP, 1, billNo, subject);
		payOptional.put("yeeWapPay", "yeeWapPay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.YEE_WAP);
		
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		refundOptional.put("yeeWebRefund", "yeeWebRefund");
		refundParam.setChannel(PAY_CHANNEL.YEE);
		
		testRefund(refundParam, PAY_CHANNEL.YEE);
	}
	
	@Test
	public void testYeeNoBankCard() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "YEE_NOBANKCARD unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.YEE_NOBANKCARD, 1, billNo, subject);
		payOptional.put("yeeNobankCardPay", "yeeNobankCardPay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.YEE_NOBANKCARD);
	}
	
	@Test
	public void testJDWeb() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "JD_WEB unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.JD_WEB, 1, billNo, subject);
		payOptional.put("jdWebPay", "jdWebPay");
		param.setReturnUrl(TestConstant.jdWebReturnUrl);
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.JD_WEB);
		
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		refundOptional.put("yeeWebRefund", "yeeWebRefund");
		refundParam.setChannel(PAY_CHANNEL.JD);
		
		testRefund(refundParam, PAY_CHANNEL.JD);
	}
	
	@Test
	public void testJDWap() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "JD_WAP unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.JD_WAP, 1, billNo, subject);
		payOptional.put("jdWapPay", "jdWapPay");
		param.setReturnUrl(TestConstant.jdWapReturnUrl);
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.JD_WAP);
	}
	
	@Test
	public void testKQWeb() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "KUAIQIAN_WEB unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.KUAIQIAN_WEB, 1, billNo, subject);
		payOptional.put("kqWebPay", "kqWebPay");
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.KUAIQIAN_WEB);
		
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		refundOptional.put("kqWebRefund", "kqWebRefund");
		refundParam.setChannel(PAY_CHANNEL.KUAIQIAN);
		
		testRefund(refundParam, PAY_CHANNEL.KUAIQIAN);
	}
	
	@Test
	public void testKQWap() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "KUAIQIAN_WAP unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.KUAIQIAN_WAP, 1, billNo, subject);
		payOptional.put("kqWapPay", "kqWapPay");
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.KUAIQIAN_WAP);
	}
	
	@Test
	public void testBDWeb() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "BD_WEB unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.BD_WEB, 1, billNo, subject);
		payOptional.put("bdWebPay", "bdWebPay");
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.BD_WEB);
		
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		refundOptional.put("bdWebRefund", "bdWebRefund");
		refundParam.setChannel(PAY_CHANNEL.BD);
		
		testRefund(refundParam, PAY_CHANNEL.BD);
	}
	
	@Test
	public void testBDWap() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "BD_WAP unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.BD_WAP, 1, billNo, subject);
		payOptional.put("bdWapPay", "bdWapPay");
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.BD_WAP);
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
		System.out.println("billNO:" + billNo);
		
		BCPayResult result = BCPay.startBCPay(null);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		
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
		
		param.setTitle(TestConstant.TITLE_WITH_CHARACTER＿GREATER_THAN_32);
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
		result = BCPay.startBCPay(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
	}
	
	@SuppressWarnings("deprecation")
	private void testRefund(BCRefundParameter refundParam, PAY_CHANNEL ali) {
		System.out.println("refundNo:" + refundNo);
		
		BCPayResult result = BCPay.startBCRefund(null);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		
		refundParam.setBillNo(null);
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setBillNo(billNo);
		
		refundParam.setRefundFee(null);
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setRefundFee(1);
		
		refundParam.setRefundFee(0);
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
		
		refundParam.setRefundNo(date + TestConstant.REFUN_NO_SERIAL_NUMBER_LESSER_THAN_3);
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setRefundNo(refundNo);
		
		refundParam.setRefundNo(date + TestConstant.REFUN_NO_SERIAL_NUMBER_GREATER_THAN_24);
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setRefundNo(refundNo);
		
		refundParam.setRefundNo(date + TestConstant.REFUN_NO_SERIAL_NUMBER_GREATER_THAN_24);
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setRefundNo(refundNo);
		
		result = ValidationUtil.validateBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.NO_SUCH_BILL.name(), result.getResultMsg());
	}
	
	@SuppressWarnings("deprecation")
	private void testQueryBill(PAY_CHANNEL channel) {
		BCQueryParameter param = new BCQueryParameter();
		BCQueryResult result = BCPay.startQueryBill(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()<=10);
		
		result = BCPay.startQueryBill(null);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		
		param.setChannel(channel);
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
}

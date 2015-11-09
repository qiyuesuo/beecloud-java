package cn.beecloud;

import static junit.framework.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.QR_PAY_MODE;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.TestConstant.CHANNEL_TYPE;
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
		BCOrder param = new BCOrder(PAY_CHANNEL.ALI_WEB, 1, billNo, subject);
		payOptional.put("aliWebPay", "aliWebPay");
		param.setReturnUrl(TestConstant.aliReturnUrl);
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.ALI_WEB);
		
		testQueryBillById(param);
		
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		refundOptional.put("aliWebRefund", "aliWebRefund");
		refundParam.setChannel(PAY_CHANNEL.ALI);
		
		testRefund(refundParam, PAY_CHANNEL.ALI);
		testRefundUpdate(PAY_CHANNEL.ALI);
		testQueryRefundById();
		testQueryBill(PAY_CHANNEL.ALI_WEB);
		testQueryBillCount(PAY_CHANNEL.ALI_WEB);
		testQueryRefund(PAY_CHANNEL.ALI_WEB);
		testQueryBillCount(PAY_CHANNEL.ALI_WEB);
	}
	
	@Test
	public void testAliQrcode() {  
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "ALI_QRCODE unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.ALI_QRCODE, 1, billNo, subject);
		payOptional.put("aliQrCodePay", "aliQrCodePay");
		param.setReturnUrl(TestConstant.aliReturnUrl);
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		param.setQrPayMode(QR_PAY_MODE.MODE_BRIEF_FRONT);
		
		testPay(param, PAY_CHANNEL.ALI_QRCODE);
		testQueryBillById(param);
		testQueryRefundById();
		testQueryBill(PAY_CHANNEL.ALI_QRCODE);
		testQueryBillCount(PAY_CHANNEL.ALI_QRCODE);
		testQueryRefund(PAY_CHANNEL.ALI_QRCODE);
		testQueryBillCount(PAY_CHANNEL.ALI_QRCODE);
	}
	
	@Test
	public void testAliWap() {  
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "ALI_WAP unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.ALI_WAP, 1, billNo, subject);
		payOptional.put("aliWapPay", "aliWapPay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.ALI_WAP);
		testQueryBillById(param);
		testQueryRefundById();
		testQueryBill(PAY_CHANNEL.ALI_WAP);
		testQueryBillCount(PAY_CHANNEL.ALI_WAP);
		testQueryRefund(PAY_CHANNEL.ALI_WAP);
		testQueryBillCount(PAY_CHANNEL.ALI_WAP);
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
		BCOrder param = new BCOrder(PAY_CHANNEL.WX_NATIVE, 1, billNo, subject);
		payOptional.put("wxNativePay", "wxNativePay");
		param.setOptional(payOptional);
		param.setBillTimeout(TestConstant.billTimeOut);
		testPay(param, PAY_CHANNEL.WX_NATIVE);
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		refundOptional.put("wxNativeRefund", "wxNativeRefund");
		refundParam.setChannel(PAY_CHANNEL.WX);
		testRefund(refundParam, PAY_CHANNEL.WX);
		testRefundUpdate(PAY_CHANNEL.WX);
		testQueryRefundById();
		testQueryBill(PAY_CHANNEL.WX_NATIVE);
		testQueryBillCount(PAY_CHANNEL.WX_NATIVE);
		testQueryRefund(PAY_CHANNEL.WX_NATIVE);
		testQueryBillCount(PAY_CHANNEL.WX_NATIVE);
	}
	
	@Test
	public void testWXJSAPI() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "WX_NATIVE unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.WX_NATIVE, 1, billNo, subject);
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
		BCOrder param = new BCOrder(PAY_CHANNEL.UN_WEB, 1, billNo, subject);
		payOptional.put("unWebPay", "unWebPay");
		param.setOptional(payOptional);
		param.setReturnUrl(TestConstant.unReturnUrl);
		param.setBillTimeout(TestConstant.billTimeOut);
		
		testPay(param, PAY_CHANNEL.UN_WEB);
		
		BCRefundParameter refundParam = new BCRefundParameter(billNo, refundNo, 1);
		refundOptional.put("unWebRefund", "unWebRefund");
		refundParam.setChannel(PAY_CHANNEL.UN);
		
		testRefund(refundParam, PAY_CHANNEL.UN);
		testRefundUpdate(PAY_CHANNEL.UN);
		testQueryRefundById();
		testQueryBill(PAY_CHANNEL.UN_WEB);
		testQueryBillCount(PAY_CHANNEL.UN_WEB);
		testQueryRefund(PAY_CHANNEL.UN_WEB);
		testQueryBillCount(PAY_CHANNEL.UN_WEB);
	}
	
	@Test
	public void testYeeWeb() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "YEE_WEB unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.YEE_WEB, 1, billNo, subject);
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
		BCOrder param = new BCOrder(PAY_CHANNEL.YEE_WAP, 1, billNo, subject);
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
		BCOrder param = new BCOrder(PAY_CHANNEL.YEE_NOBANKCARD, 1, billNo, subject);
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
		BCOrder param = new BCOrder(PAY_CHANNEL.JD_WEB, 1, billNo, subject);
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
		BCOrder param = new BCOrder(PAY_CHANNEL.JD_WAP, 1, billNo, subject);
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
		BCOrder param = new BCOrder(PAY_CHANNEL.KUAIQIAN_WEB, 1, billNo, subject);
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
		BCOrder param = new BCOrder(PAY_CHANNEL.KUAIQIAN_WAP, 1, billNo, subject);
		payOptional.put("kqWapPay", "kqWapPay");
		param.setOptional(payOptional);
		
		testPay(param, PAY_CHANNEL.KUAIQIAN_WAP);
	}
	
	@Test
	public void testBDWeb() {
		
		billNo = BCUtil.generateRandomUUIDPure();
		refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + BCUtil.generateNumberWith3to24digitals();
		subject = "BD_WEB unit test";
		BCOrder param = new BCOrder(PAY_CHANNEL.BD_WEB, 1, billNo, subject);
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
		BCOrder param = new BCOrder(PAY_CHANNEL.BD_WAP, 1, billNo, subject);
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
	
	@SuppressWarnings("deprecation")
	private void testPay(BCOrder param, PAY_CHANNEL channel) {
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
		
		param.setTitle("正好十六个汉字的标题哈哈哈哈哈哈");
		result = BCPay.startBCPay(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
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
		
		refundParam.setRefundNo(date + TestConstant.REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3);
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setRefundNo(refundNo);
		
		refundParam.setRefundNo(date + TestConstant.REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24);
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setRefundNo(refundNo);
		
		refundParam.setRefundNo(date + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
		result = BCPay.startBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		refundParam.setRefundNo(refundNo);
		
		result = ValidationUtil.validateBCRefund(refundParam);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		
		result = BCPay.startBCRefund(refundParam);
		System.out.println(result.getErrDetail());
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
		
		param.setBillNo(TestConstant.NOT_EXIST_BILL_NO);
		result = BCPay.startQueryBill(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()==0);
		
		param.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
		result = BCPay.startQueryBill(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null);
		
		param.setBillNo(billNo.substring(0, 7));
		result = BCPay.startQueryBill(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null);
		
		param.setBillNo(billNo + "A");
		result = BCPay.startQueryBill(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null);
		param.setBillNo(null);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, +1);
		param.setStartTime(cal.getTime());
		result = BCPay.startQueryBill(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()<=10);
		
		cal.add(Calendar.MONTH, -1);
		param.setEndTime(cal.getTime());
		result = BCPay.startQueryBill(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null);
		
		param.setStartTime(null);
		param.setEndTime(null);
		
		param.setSkip(2);
		result = BCPay.startQueryBill(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size()<=10);
		
		param.setSkip(-1);
		result = BCPay.startQueryBill(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null);
		
		param.setSkip(0);
		result = BCPay.startQueryBill(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.OK.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders().size() <=10 );
		param.setSkip(null);
		
		param.setLimit(8);
		result = BCPay.startQueryBill(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null );
	
		param.setLimit(52);
		result = BCPay.startQueryBill(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcOrders() == null );
		param.setLimit(null);
	}
	
	@SuppressWarnings("deprecation")
	private void testQueryBillCount(PAY_CHANNEL channel) {
		BCQueryParameter param = new BCQueryParameter();
		
		BCQueryResult result = BCPay.startQueryBillCount(null);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		
		result = BCPay.startQueryBillCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() >= 0);
		
		param.setBillNo(TestConstant.NOT_EXIST_BILL_NO);
		result = BCPay.startQueryBillCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == 0);
		
		param.setChannel(channel);
		result = BCPay.startQueryBillCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() >= 0);
		param.setChannel(null);
		
		param.setBillNo(billNo);
		result = BCPay.startQueryBillCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() >= 0);
		
		param.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
		result = BCPay.startQueryBillCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
		
		param.setBillNo(billNo.substring(0, 7));
		result = BCPay.startQueryBillCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
		
		param.setBillNo(billNo + "A");
		result = BCPay.startQueryBillCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
		param.setBillNo(null);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, +1);
		param.setStartTime(cal.getTime());
		result = BCPay.startQueryBillCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == 0);
		
		cal.add(Calendar.MONTH, -1);
		param.setEndTime(cal.getTime());
		result = BCPay.startQueryBillCount(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
		
		param.setStartTime(null);
		param.setEndTime(null);
	}
	
	@SuppressWarnings("deprecation")
	private void testQueryRefund(PAY_CHANNEL channel) {
		BCRefundQueryParameter param = new BCRefundQueryParameter();
		BCQueryResult result = BCPay.startQueryRefund(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size()<=10);
		
		result = BCPay.startQueryRefund(null);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		
		param.setChannel(channel);
		result = BCPay.startQueryRefund(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size()<=10);
		param.setChannel(null);
		
		param.setBillNo(billNo);
		result = BCPay.startQueryRefund(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size()<=10);
		
		param.setBillNo(TestConstant.NOT_EXIST_BILL_NO);
		result = BCPay.startQueryRefund(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size()==0);
		
		param.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
		result = BCPay.startQueryRefund(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null);
		
		param.setBillNo(billNo.substring(0, 7));
		result = BCPay.startQueryRefund(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null);
		
		param.setBillNo(billNo + "A");
		result = BCPay.startQueryRefund(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null);
		param.setBillNo(null);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, +1);
		param.setStartTime(cal.getTime());
		result = BCPay.startQueryRefund(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size()<=10);
		
		cal.add(Calendar.MONTH, -1);
		param.setEndTime(cal.getTime());
		result = BCPay.startQueryRefund(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null);
		
		param.setStartTime(null);
		param.setEndTime(null);
		
		param.setSkip(2);
		result = BCPay.startQueryRefund(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size()<=10);
		
		param.setSkip(-1);
		result = BCPay.startQueryRefund(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null);
		
		param.setSkip(0);
		result = BCPay.startQueryRefund(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.OK.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size() <=10 );
		param.setSkip(null);
		
		param.setLimit(8);
		result = BCPay.startQueryRefund(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null );
	
		param.setLimit(52);
		result = BCPay.startQueryRefund(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null );
		param.setLimit(null);
		
		param.setRefundNo(refundNo);
		result = BCPay.startQueryRefund(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.OK.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList().size() <=10 );
		
		param.setRefundNo(refundNo.substring(0, 8) + "000");
		result = BCPay.startQueryRefund(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null );
	
		param.setRefundNo(refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3);
		result = BCPay.startQueryRefund(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null );
		
		param.setRefundNo(refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24);
		result = BCPay.startQueryRefund(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null );
		
		param.setRefundNo(refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
		result = BCPay.startQueryRefund(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getBcRefundList() == null );
	}
	
	@SuppressWarnings("deprecation")
	private void testQueryRefundCount(PAY_CHANNEL channel) {
		BCRefundQueryParameter param = new BCRefundQueryParameter();
		BCQueryResult result = BCPay.startQueryRefundCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount()>=0);
		
		result = BCPay.startQueryRefundCount(null);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		
		param.setChannel(channel);
		result = BCPay.startQueryRefundCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount()>=0);
		param.setChannel(null);
		
		param.setBillNo(billNo);
		result = BCPay.startQueryRefundCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount()>=0);
		
		param.setBillNo(TestConstant.NOT_EXIST_BILL_NO);
		result = BCPay.startQueryRefundCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount()>=0);
		
		param.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
		result = BCPay.startQueryRefundCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
		
		param.setBillNo(billNo.substring(0, 7));
		result = BCPay.startQueryRefundCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
		
		param.setBillNo(billNo + "A");
		result = BCPay.startQueryRefundCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.PARAM_INVALID.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
		param.setBillNo(null);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, +1);
		param.setStartTime(cal.getTime());
		result = BCPay.startQueryRefundCount(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount()>=0);
		
		cal.add(Calendar.MONTH, -1);
		param.setEndTime(cal.getTime());
		result = BCPay.startQueryRefundCount(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null);
		
		param.setStartTime(null);
		param.setEndTime(null);
		
		param.setRefundNo(refundNo);
		result = BCPay.startQueryRefundCount(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.OK.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount()>=0 );
		
		param.setRefundNo(refundNo.substring(0, 8) + "000");
		result = BCPay.startQueryRefundCount(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null );
	
		param.setRefundNo(refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3);
		result = BCPay.startQueryRefundCount(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null );
		
		param.setRefundNo(refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24);
		result = BCPay.startQueryRefundCount(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null );
		
		param.setRefundNo(refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
		result = BCPay.startQueryRefundCount(param);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getTotalCount() == null );
	}
	
	@SuppressWarnings("deprecation")
	public void testQueryBillById(BCOrder param) {
		BCPayResult result = BCPay.startBCPay(param);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		
		String objectId = result.getObjectId();
		System.out.println(objectId);
		BCQueryResult queryResult = BCPay.startQueryBillById(objectId);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), queryResult.getResultMsg());
		
		queryResult = BCPay.startQueryBillById(TestConstant.INVALID_OBJECT_ID);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, queryResult.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testQueryRefundById() {
		BCQueryResult result = BCPay.startQueryRefundById(TestConstant.VALID_REFUND_OBJECT_ID);
		assertEquals(TestConstant.ASSERT_MESSAGE, RESULT_TYPE.OK.name(), result.getResultMsg());
		
		result = BCPay.startQueryRefundById(TestConstant.INVALID_OBJECT_ID);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
	}
	
	private void testRefundUpdate(PAY_CHANNEL channel) {
		BCQueryStatusResult result = BCPay.startRefundUpdate(null, refundNo);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		
		result = BCPay.startRefundUpdate(channel, null);
		System.out.println(result.getErrDetail());
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
	
		result = BCPay.startRefundUpdate(channel, refundNo.substring(0, 8) + "000");
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		System.out.println(result.getErrDetail());
		result = BCPay.startRefundUpdate(channel, refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		System.out.println(result.getErrDetail());
		result = BCPay.startRefundUpdate(channel, refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24);
		Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.PARAM_INVALID.name()));
		System.out.println(result.getErrDetail());
		
		result = BCPay.startRefundUpdate(channel, refundNo);
		if (channel.equals(PAY_CHANNEL.WX) || channel.equals(PAY_CHANNEL.KUAIQIAN) || channel.equals(PAY_CHANNEL.YEE) || channel.equals(PAY_CHANNEL.BD)) {
			System.out.println(result.getErrDetail());
			Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.NO_SUCH_REFUND.name()));
			
			result = BCPay.startRefundUpdate(channel, refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
			System.out.println(result.getErrDetail());
			Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getResultMsg().contains(RESULT_TYPE.NO_SUCH_REFUND.name()));
		} else {
			Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getErrDetail().contains("渠道选择错误"));
			
			result = BCPay.startRefundUpdate(channel, refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
			Assert.assertTrue(TestConstant.ASSERT_MESSAGE, result.getErrDetail().contains("渠道选择错误"));
		}
	}
	
	
}

package cn.beecloud;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.QR_PAY_MODE;
import cn.beecloud.bean.*;;

public class BCPayTest {
	
	private String billNo;
	private String subject;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}  

	@Before
	public void setUp() throws Exception {
		BeeCloud.registerApp(TestConstant.KTestAppID, TestConstant.kTestAppSecret);
	}

	@Test
	public void testPay() {  
		billNo = BCUtil.generateRandomUUIDPure();
		subject = "ALI_WEB unit test";
		BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_WEB, 1, billNo, subject);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aliWebUnitTest", "aliWebUnitTest");
		param.setReturnUrl("http://localhost:8080/PC-Web-Pay-Demo/aliReturnUrl.jsp");
		param.setOptional(map);
		
		BCPayResult result = BCPay.startBCPay(param);
		System.out.println("test");
	}
	
	@Test
	public void testRefund() {
		
		BCRefundParameter param = new BCRefundParameter("1516e2ce5b8f4d599dcb13fd2d9b0eaa", "201509240000", 1);
		param.setChannel(PAY_CHANNEL.ALI);
		
		BCPayResult result = BCPay.startBCRefund(param);
		System.out.println("test1");
	
	}
	
	@Test
	public void testQueryBill() {
		BCQueryParameter param = new BCQueryParameter();
		param.setChannel(PAY_CHANNEL.PAYPAL);
		
		BCQueryResult bcQueryResult = BCPay.startQueryBill(param);
		System.out.println("test1");
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
}

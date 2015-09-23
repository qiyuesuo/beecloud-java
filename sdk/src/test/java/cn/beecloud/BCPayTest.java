package cn.beecloud;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.bean.TransferData;
import cn.beecloud.bean.*;;

public class BCPayTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		BeeCloud.registerApp(TestConstant.KTestAppID, TestConstant.kTestAppSecret);
	}

	@Test
	public void testPay() {  
		Map map = new HashMap();
		map.put("opchannel", "1002");
		BCPayResult result = BCPay.startBCPay(PAY_CHANNEL.UN_WEB, 1, "201508130100010000005644", "气费", map, "http://118.186.253.55:8889/V04/99bill.html", null, null, null,null, null, null,null);
		System.out.println("test");
	}
	
	@Test
	public void testRefund() {
		
		
		BCPayResult result = BCPay.startBCRefund(PAY_CHANNEL.WX, "201507170000", "327c4023fc3b46fd9caceaf3cd3bb3d6", 2, null, false);
		System.out.println("test1");
	
		result = BCPay.startBCRefund(PAY_CHANNEL.UN,  "201507142677696345", null, 1, null, false);
		System.out.println("test2");
	
		result = BCPay.startBCRefund(PAY_CHANNEL.ALI, "201507101112", null, 1,  null, false);
		System.out.println("test3");
		
	}
	
	@Test
	public void testQueryBill() {
		BCQueryResult bcQueryResult = BCPay.startQueryBill(null, "1122366773", null, null, null, 50);
		System.out.println("test1");
	}
	
	@Test
	public void testQueryRefund() {
		BCQueryResult bcQueryResult = BCPay.startQueryRefund(PAY_CHANNEL.ALI_WEB, null, null, null, null, null, null);
	}
	
	@Test
	public void testQueryWeChatRefundStatus() {
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

package cn.beecloud;

import mockit.integration.junit4.JMockit;

import javax.ws.rs.client.Client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class BCPayTest {
	
	protected Client client;
	
	@Before
	public void setUp() throws Exception {
		BeeCloud.registerApp(TestConstant.KTestAppID, TestConstant.kTestAppSecret);
	}

	@Test
	public void javaSDKTest() {  
		PayTest.testPay();
		BillQueryTest.testQueryBillById();
		BillQueryTest.testQueryBill();
		BillQueryTest.testQueryBillCount();
		RefundTest.testRefund();
		RefundQueryTest.testQueryRefundById();
		RefundQueryTest.testQueryRefund();
		RefundQueryTest.testQueryRefundCount();
		RefundTest.testRefundUpdate();
		BatchHandleTest.testBatchRefund();
		TransferTest.testTransfer();
		TransfersTest.testTransfers();
		InternationalPayTest.testInternationalPay();
	}
}
	

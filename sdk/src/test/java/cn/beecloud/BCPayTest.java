package cn.beecloud;

import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCT1TransferParameter;
import mockit.integration.junit4.JMockit;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;


/**
 * JAVA SDK 单元测试主类
 * 
 * @author Rui
 */
@RunWith(JMockit.class)
public class BCPayTest {

    @Before
    public void setUp() throws Exception {
        BeeCloud.registerApp(TestConstant.KTestAppID, null, TestConstant.kTestAppSecret,
                TestConstant.kTestMasterSecret);
    }

    @Test
    public void t1TransferTest() {
        BeeCloud.registerApp("beacfdf5-badd-4a11-9b23-9ef3801732d1", null, "0fa599d9-b0ae-41b3-85de-d3153809004d",
                "4505865b-0818-4174-a487-12faa56695fa");
        BCT1TransferParameter transferParameter = new BCT1TransferParameter();
        transferParameter.setTotalFee(600);
        transferParameter.setBankAccountName("孙琪");
        transferParameter.setBankAccountNo("4367455038759507");
        transferParameter.setBankName("中国建设银行");
        transferParameter.setBillNo("tx123456789003");
        transferParameter.setIsPersonal("1"); // 1 - 对私, 0 - 对公
        //transferParameter.setOptional(new HashMap<String, Object>());

        Map<String, Object> result = null;
        try {
            result = BCPay.startBCT1transfer(transferParameter);
        } catch (BCException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(result);
    }

    @Test
    public void javaSDKTest() {

        /**
         * 测试LIVE模式
         */
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
        TransferTest.testBCTransfer();

        UserTest.testUserRegister();
        UserTest.testUserBatchImport();
        UserTest.testUserBatchQuery();
        HistoryBillsTest.testHistoryBills();

        TransferQueryTest.testTransferQuery();
        TransferQueryTest.testTransferQueryCount();

        /**
         * 测试SANDBOX模式
         */
        BCCache.setSandbox(true);
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

//        UserTest.testUserRegister();
//        UserTest.testUserBatchImport();
//        UserTest.testUserBatchQuery();
//        HistoryBillsTest.testHistoryBills();
    }
}

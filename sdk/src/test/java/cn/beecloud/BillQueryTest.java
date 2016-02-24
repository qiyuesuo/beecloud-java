package cn.beecloud;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.StrictExpectations;

import org.junit.Assert;

import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCOrder;
import cn.beecloud.bean.BCQueryParameter;


/**
 * 订单查询单元测试，包括单笔查询，批量查询、总数查询
 * 
 * @author Rui
 * @since 2015/11/08
 */
public class BillQueryTest {

    static String billNo = BCUtil.generateRandomUUIDPure();

    static void testQueryBill() {
        BCQueryParameter param = new BCQueryParameter();
        List<BCOrder> bcOrderList = new LinkedList<BCOrder>();
        try {
            bcOrderList = BCPay.startQueryBill(null);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.QUERY_PARAM_EMPTY));
        }

        try {
            param.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
            bcOrderList = BCPay.startQueryBill(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }

        try {
            param.setBillNo(billNo.substring(0, 7));
            bcOrderList = BCPay.startQueryBill(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }

        try {
            param.setBillNo(billNo + "A");
            bcOrderList = BCPay.startQueryBill(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }
        param.setBillNo(billNo);

        try {
            param.setSkip(-1);
            bcOrderList = BCPay.startQueryBill(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name())
                            || ex.getMessage().contains(RESULT_TYPE.OTHER_ERROR.name())
                            || ex.getMessage().contains(RESULT_TYPE.NOT_CORRECT_RESPONSE.name()));// 服务端验证，可能存在网络问题或者响应错误问题，加上OTHER_ERROR判断
        }

        try {
            param.setLimit(8);
            bcOrderList = BCPay.startQueryBill(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.LIMIT_FORMAT_INVALID));
        }

        try {
            param.setLimit(52);
            bcOrderList = BCPay.startQueryBill(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.LIMIT_FORMAT_INVALID));
        }

        // mock网络请求
        if (BCCache.isSandbox()) {
            mockSandboxQueryBill();
            return;
        }
        mockQueryBill();
    }

    static void testQueryBillById() {

        try {
            BCPay.startQueryBillById(null);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.OBJECT_ID_EMPTY));
        }

        try {
            BCPay.startQueryBillById(TestConstant.INVALID_OBJECT_ID);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.OBJECT_ID_INVALID));
        }
        // mock网络请求
        if (BCCache.isSandbox()) {
            mockSandboxQueryBillById();
            return;
        }

        mockQueryBillById();
    }

    static void testQueryBillCount() {
        BCQueryParameter param = new BCQueryParameter();
        Integer count;
        try {
            count = BCPay.startQueryBillCount(null);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.QUERY_PARAM_EMPTY));
        }

        try {
            param.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
            count = BCPay.startQueryBillCount(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }

        try {
            param.setBillNo(billNo.substring(0, 7));
            count = BCPay.startQueryBillCount(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }

        try {
            param.setBillNo(billNo + "A");
            count = BCPay.startQueryBillCount(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }

        param.setBillNo(null);
        // mock网络请求
        if (BCCache.isSandbox()) {
            mockSandboxQueryBillCount();
            return;
        }
        mockBillCount();
    }

    private static void mockQueryBill() {
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        List<BCOrder> bcOrderList = new LinkedList<BCOrder>();
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        returnMap.put("bills", generateMockBills());
        returnMap.put("count", generateMockBills().size());

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doGet",
                        withSubstring(BCUtilPrivate.getkApiQueryBill().substring(14)),
                        withAny(Map.class));
                returns(returnMap);
                result = new BCException(RESULT_TYPE.APP_INVALID.ordinal(),
                        RESULT_TYPE.APP_INVALID.name(), TestConstant.MOCK_APP_INVALID_ERRMSG);
            }
        };

        BCQueryParameter param = new BCQueryParameter();
        param.setBillNo(billNo);
        try {
            bcOrderList = BCPay.startQueryBill(param);
            Assert.assertEquals("", 2, bcOrderList.size());
            for (BCOrder order : bcOrderList) {
                Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID, order.getObjectId());
                Assert.assertEquals("", TestConstant.MOCK_BILL_NO, order.getBillNo());
                Assert.assertEquals("",
                        TestUtil.transferDateFromLongToString(TestConstant.MOCK_CREATE_TIME),
                        order.getDateTime());
                Assert.assertEquals("", TestConstant.MOCK_CHANNEL,
                        order.getChannel().toString().split("_")[0]);
                Assert.assertEquals("", TestConstant.MOCK_SUB_CHANNEL,
                        order.getChannel().toString());
                Assert.assertEquals("", TestConstant.MOCK_PAY_RESULT, order.isResult());
                Assert.assertTrue("", order.getOptionalString().equals("") || order
                        .getOptionalString().equals(TestConstant.MOCK_OPTIONAL_JSON_STRING));
                Assert.assertEquals("", true, order.isRevertResult());
                Assert.assertEquals("", true, order.isRefundResult());
                Assert.assertTrue("", order.getMessageDetail().equals("不显示") || order
                        .getMessageDetail().equals(TestConstant.MOCK_MESSAGE_DETAIL_STRING));
                Assert.assertEquals("", TestConstant.MOCK_TITLE, order.getTitle());
                Assert.assertEquals("", TestConstant.MOCK_TOTAL_FEE, order.getTotalFee());
                Assert.assertTrue("", order.getChannelTradeNo().equals(TestConstant.MOCK_TRADE_NO)
                        || order.getChannelTradeNo().equals(""));
            }
        } catch (BCException ex) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

        try {
            bcOrderList = BCPay.startQueryBill(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.APP_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.MOCK_APP_INVALID_ERRMSG));
        }
    }

    private static void mockSandboxQueryBill() {
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        List<BCOrder> bcOrderList = new LinkedList<BCOrder>();
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        returnMap.put("bills", generateMockBills());
        returnMap.put("count", generateMockBills().size());

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doGet",
                        withSubstring(BCUtilPrivate.getkApiSandboxQueryBill().substring(14)),
                        withAny(Map.class));
                returns(returnMap);
                result = new BCException(RESULT_TYPE.APP_INVALID.ordinal(),
                        RESULT_TYPE.APP_INVALID.name(), TestConstant.MOCK_APP_INVALID_ERRMSG);
            }
        };

        BCQueryParameter param = new BCQueryParameter();
        param.setBillNo(billNo);
        try {
            bcOrderList = BCPay.startQueryBill(param);
            Assert.assertEquals("", 2, bcOrderList.size());
            for (BCOrder order : bcOrderList) {
                Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID, order.getObjectId());
                Assert.assertEquals("", TestConstant.MOCK_BILL_NO, order.getBillNo());
                Assert.assertEquals("",
                        TestUtil.transferDateFromLongToString(TestConstant.MOCK_CREATE_TIME),
                        order.getDateTime());
                Assert.assertEquals("", TestConstant.MOCK_CHANNEL,
                        order.getChannel().toString().split("_")[0]);
                Assert.assertEquals("", TestConstant.MOCK_SUB_CHANNEL,
                        order.getChannel().toString());
                Assert.assertEquals("", TestConstant.MOCK_PAY_RESULT, order.isResult());
                Assert.assertTrue("", order.getOptionalString().equals("") || order
                        .getOptionalString().equals(TestConstant.MOCK_OPTIONAL_JSON_STRING));
                Assert.assertEquals("", true, order.isRevertResult());
                Assert.assertEquals("", true, order.isRefundResult());
                Assert.assertTrue("", order.getMessageDetail().equals("不显示") || order
                        .getMessageDetail().equals(TestConstant.MOCK_MESSAGE_DETAIL_STRING));
                Assert.assertEquals("", TestConstant.MOCK_TITLE, order.getTitle());
                Assert.assertEquals("", TestConstant.MOCK_TOTAL_FEE, order.getTotalFee());
                Assert.assertTrue("", order.getChannelTradeNo().equals(TestConstant.MOCK_TRADE_NO)
                        || order.getChannelTradeNo().equals(""));
            }
        } catch (BCException ex) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

        try {
            bcOrderList = BCPay.startQueryBill(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.APP_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.MOCK_APP_INVALID_ERRMSG));
        }
    }

    private static void mockQueryBillById() {
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        Map<String, Object> payMap = new HashMap<String, Object>();
        buildMockBill(payMap);
        returnMap.put("pay", payMap);

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doGet",
                        withSubstring(BCUtilPrivate.getkApiQueryBillById().substring(14)),
                        withAny(Map.class));
                returns(returnMap);
            }
        };
        BCOrder order;
        try {
            order = BCPay.startQueryBillById(TestConstant.MOCK_OBJECT_ID);
            Assert.assertEquals("", TestConstant.MOCK_BILL_NO, order.getBillNo());
            Assert.assertEquals("", TestConstant.MOCK_TRADE_NO, order.getChannelTradeNo());
            Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID, order.getObjectId());
            Assert.assertEquals("", TestConstant.MOCK_PAY_RESULT, order.isResult());
            Assert.assertEquals("",
                    TestUtil.transferDateFromLongToString(TestConstant.MOCK_CREATE_TIME),
                    order.getDateTime());
            Assert.assertEquals("", TestConstant.MOCK_TOTAL_FEE, order.getTotalFee());
            Assert.assertEquals("", TestConstant.MOCK_CHANNEL,
                    order.getChannel().toString().split("_")[0]);
            Assert.assertEquals("", TestConstant.MOCK_SUB_CHANNEL, order.getChannel().toString());
            Assert.assertEquals("", TestConstant.MOCK_OPTIONAL_JSON_STRING,
                    order.getOptionalString());
            Assert.assertEquals("", true, order.isRevertResult());
            Assert.assertEquals("", true, order.isRefundResult());
            Assert.assertEquals("", TestConstant.MOCK_MESSAGE_DETAIL_STRING,
                    order.getMessageDetail());
            Assert.assertEquals("", TestConstant.MOCK_TITLE, order.getTitle());
        } catch (BCException e) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

        new StrictExpectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doGet",
                        withSubstring(BCUtilPrivate.getkApiQueryBillById().substring(14)),
                        withAny(Map.class));
                result = new BCException(RESULT_TYPE.APP_INVALID.ordinal(),
                        RESULT_TYPE.APP_INVALID.name(), RESULT_TYPE.APP_INVALID.name());
            }
        };

        try {
            order = BCPay.startQueryBillById(TestConstant.MOCK_OBJECT_ID);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.APP_INVALID.name()));
        }
    }

    private static void mockSandboxQueryBillById() {
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        Map<String, Object> payMap = new HashMap<String, Object>();
        buildMockBill(payMap);
        returnMap.put("pay", payMap);

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doGet",
                        withSubstring(BCUtilPrivate.getkApiSandboxQueryBillById().substring(14)),
                        withAny(Map.class));
                returns(returnMap);
            }
        };
        BCOrder order;
        try {
            order = BCPay.startQueryBillById(TestConstant.MOCK_OBJECT_ID);
            Assert.assertEquals("", TestConstant.MOCK_BILL_NO, order.getBillNo());
            Assert.assertEquals("", TestConstant.MOCK_TRADE_NO, order.getChannelTradeNo());
            Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID, order.getObjectId());
            Assert.assertEquals("", TestConstant.MOCK_PAY_RESULT, order.isResult());
            Assert.assertEquals("",
                    TestUtil.transferDateFromLongToString(TestConstant.MOCK_CREATE_TIME),
                    order.getDateTime());
            Assert.assertEquals("", TestConstant.MOCK_TOTAL_FEE, order.getTotalFee());
            Assert.assertEquals("", TestConstant.MOCK_CHANNEL,
                    order.getChannel().toString().split("_")[0]);
            Assert.assertEquals("", TestConstant.MOCK_SUB_CHANNEL, order.getChannel().toString());
            Assert.assertEquals("", TestConstant.MOCK_OPTIONAL_JSON_STRING,
                    order.getOptionalString());
            Assert.assertEquals("", true, order.isRevertResult());
            Assert.assertEquals("", true, order.isRefundResult());
            Assert.assertEquals("", TestConstant.MOCK_MESSAGE_DETAIL_STRING,
                    order.getMessageDetail());
            Assert.assertEquals("", TestConstant.MOCK_TITLE, order.getTitle());
        } catch (BCException e) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }
        new StrictExpectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doGet",
                        withSubstring(BCUtilPrivate.getkApiSandboxQueryBillById().substring(14)),
                        withAny(Map.class));
                result = new BCException(RESULT_TYPE.APP_INVALID.ordinal(),
                        RESULT_TYPE.APP_INVALID.name(), RESULT_TYPE.APP_INVALID.name());
            }
        };

        try {
            order = BCPay.startQueryBillById(TestConstant.MOCK_OBJECT_ID);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.APP_INVALID.name()));
        }
    }

    private static void mockBillCount() {
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        returnMap.put("count", 10);

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doGet",
                        withSubstring(BCUtilPrivate.getkApiQueryBillCount().substring(14)),
                        withAny(Map.class));
                returns(returnMap);
            }
        };

        BCQueryParameter param = new BCQueryParameter();
        param.setBillNo(billNo);

        try {
            Integer count = BCPay.startQueryBillCount(param);
            Assert.assertEquals("", 10, (int) count);
        } catch (BCException ex) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }
    }

    private static void mockSandboxQueryBillCount() {
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        returnMap.put("count", 10);

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doGet",
                        withSubstring(BCUtilPrivate.getkApiSandboxQueryBillCount().substring(14)),
                        withAny(Map.class));
                returns(returnMap);
            }
        };

        BCQueryParameter param = new BCQueryParameter();
        param.setBillNo(billNo);

        try {
            Integer count = BCPay.startQueryBillCount(param);
            Assert.assertEquals("", 10, (int) count);
        } catch (BCException ex) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

    }

    private static List<Map<String, Object>> generateMockBills() {
        List<Map<String, Object>> bills = new LinkedList<Map<String, Object>>();
        Map<String, Object> bill = new HashMap<String, Object>();
        Map<String, Object> billAnother = new HashMap<String, Object>();
        buildMockBill(bill);

        buildMockBillAnother(billAnother);

        bills.add(bill);
        bills.add(billAnother);
        return bills;
    }

    private static void buildMockBillAnother(Map<String, Object> billAnother) {
        billAnother.put("id", TestConstant.MOCK_OBJECT_ID);
        billAnother.put("bill_no", TestConstant.MOCK_BILL_NO);
        billAnother.put("total_fee", TestConstant.MOCK_TOTAL_FEE);
        billAnother.put("trade_no", "");
        billAnother.put("channel", TestConstant.MOCK_CHANNEL);
        billAnother.put("sub_channel", TestConstant.MOCK_SUB_CHANNEL);
        billAnother.put("title", TestConstant.MOCK_TITLE);
        billAnother.put("spay_result", TestConstant.MOCK_PAY_RESULT);
        billAnother.put("create_time", TestConstant.MOCK_CREATE_TIME);
        billAnother.put("optional", "");
        billAnother.put("revert_result", true);
        billAnother.put("refund_result", true);
    }

    private static void buildMockBill(Map<String, Object> bill) {
        bill.put("id", TestConstant.MOCK_OBJECT_ID);
        bill.put("bill_no", TestConstant.MOCK_BILL_NO);
        bill.put("total_fee", TestConstant.MOCK_TOTAL_FEE);
        bill.put("trade_no", TestConstant.MOCK_TRADE_NO);
        bill.put("channel", TestConstant.MOCK_CHANNEL);
        bill.put("sub_channel", TestConstant.MOCK_SUB_CHANNEL);
        bill.put("title", TestConstant.MOCK_TITLE);
        bill.put("spay_result", TestConstant.MOCK_PAY_RESULT);
        bill.put("create_time", TestConstant.MOCK_CREATE_TIME);
        bill.put("optional", TestConstant.MOCK_OPTIONAL_JSON_STRING);
        bill.put("message_detail", TestConstant.MOCK_MESSAGE_DETAIL_STRING);
        bill.put("revert_result", true);
        bill.put("refund_result", true);
    }

}

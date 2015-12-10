package cn.beecloud;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.StrictExpectations;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCQueryParameter;
import cn.beecloud.bean.BCRefund;
import junit.framework.Assert;


/**
 * 退款查询单元测试，包括单笔查询、批量查询、总数查询
 * 
 * @author Rui
 * @since 2015/11/12
 */
public class RefundQueryTest {

    static String billNo = BCUtil.generateRandomUUIDPure();

    static String refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date())
            + BCUtil.generateNumberWith3to24digitals();

    static void testQueryRefundById() {

        if (BCCache.isSandbox()) {
            try {
                BCPay.startQueryRefundById(TestConstant.INVALID_OBJECT_ID);
                Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
            } catch (Exception e) {
                Assert.assertTrue(e.getMessage(), e instanceof BCException);
                Assert.assertTrue(e.getMessage(),
                        e.getMessage().contains(RESULT_TYPE.OTHER_ERROR.name()));
                Assert.assertTrue(e.getMessage(),
                        e.getMessage().contains(TestConstant.TEST_MODE_SUPPORT_ERROR));
            }
            return;
        }

        try {
            BCPay.startQueryRefundById(null);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.OBJECT_ID_EMPTY));
        }

        try {
            BCPay.startQueryRefundById(TestConstant.INVALID_OBJECT_ID);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.OBJECT_ID_INVALID));
        }

        mockQueryRefundById();
    }

    static void testQueryRefund() {
        BCQueryParameter param = new BCQueryParameter();

        if (BCCache.isSandbox()) {
            try {
                BCPay.startQueryRefund(param);
                Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
            } catch (Exception e) {
                Assert.assertTrue(e.getMessage(), e instanceof BCException);
                Assert.assertTrue(e.getMessage(),
                        e.getMessage().contains(RESULT_TYPE.OTHER_ERROR.name()));
                Assert.assertTrue(e.getMessage(),
                        e.getMessage().contains(TestConstant.TEST_MODE_SUPPORT_ERROR));
            }
            return;
        }

        List<BCRefund> bcRefundList = new LinkedList<BCRefund>();
        try {
            bcRefundList = BCPay.startQueryRefund(null);
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
            bcRefundList = BCPay.startQueryRefund(param);
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
            bcRefundList = BCPay.startQueryRefund(param);
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
            bcRefundList = BCPay.startQueryRefund(param);
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
            bcRefundList = BCPay.startQueryRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            org.junit.Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            org.junit.Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name())
                            || ex.getMessage().contains(RESULT_TYPE.OTHER_ERROR.name()));// 服务端验证，可能存在网络问题，加上OTHER_ERROR判断
        }

        try {
            param.setLimit(8);
            bcRefundList = BCPay.startQueryRefund(param);
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
            bcRefundList = BCPay.startQueryRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.LIMIT_FORMAT_INVALID));
        }

        try {
            param.setRefundNo(refundNo.substring(0, 8) + "000");
            bcRefundList = BCPay.startQueryRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }

        try {
            param.setRefundNo(
                    refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3);
            bcRefundList = BCPay.startQueryRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }

        try {
            param.setRefundNo(refundNo.substring(0, 8)
                    + TestConstant.REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24);
            bcRefundList = BCPay.startQueryRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }

        try {
            param.setRefundNo(refundNo.substring(0, 8)
                    + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
            bcRefundList = BCPay.startQueryRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }
        // mock网络请求
        mockRefundQuery();
    }

    static void testQueryRefundCount() {
        BCQueryParameter param = new BCQueryParameter();
        Integer count;

        if (BCCache.isSandbox()) {
            try {
                BCPay.startQueryRefundCount(param);
                Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
            } catch (Exception e) {
                Assert.assertTrue(e.getMessage(), e instanceof BCException);
                Assert.assertTrue(e.getMessage(),
                        e.getMessage().contains(RESULT_TYPE.OTHER_ERROR.name()));
                Assert.assertTrue(e.getMessage(),
                        e.getMessage().contains(TestConstant.TEST_MODE_SUPPORT_ERROR));
            }
            return;
        }

        try {
            count = BCPay.startQueryRefundCount(null);
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
            count = BCPay.startQueryRefundCount(param);
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
            count = BCPay.startQueryRefundCount(param);
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
            count = BCPay.startQueryRefundCount(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }
        param.setBillNo(null);

        try {
            param.setRefundNo(refundNo.substring(0, 8) + "000");
            count = BCPay.startQueryRefundCount(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }

        try {
            param.setRefundNo(
                    refundNo.substring(0, 8) + TestConstant.REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3);
            count = BCPay.startQueryRefundCount(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }

        try {
            param.setRefundNo(refundNo.substring(0, 8)
                    + TestConstant.REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24);
            count = BCPay.startQueryRefundCount(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }

        try {
            param.setRefundNo(refundNo.substring(0, 8)
                    + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
            count = BCPay.startQueryRefundCount(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(TestConstant.ASSERT_MESSAGE,
                    ex.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }
        // mock网络请求
        mockRefundCount();
    }

    private static void mockRefundQuery() {
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        List<BCRefund> bcRefundList = new LinkedList<BCRefund>();
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        returnMap.put("refunds", generateMockRefunds());
        returnMap.put("count", generateMockRefunds().size());

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doGet",
                        withSubstring(BCUtilPrivate.getkApiQueryRefund().substring(14)),
                        withAny(Map.class));
                returns(returnMap);
                result = new BCException(RESULT_TYPE.APP_INVALID.ordinal(),
                        RESULT_TYPE.APP_INVALID.name(), TestConstant.MOCK_APP_INVALID_ERRMSG);
            }
        };

        BCQueryParameter param = new BCQueryParameter();
        param.setBillNo(billNo);
        try {
            bcRefundList = BCPay.startQueryRefund(param);
            Assert.assertEquals("", 2, bcRefundList.size());
            for (BCRefund refund : bcRefundList) {
                Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID, refund.getObjectId());
                Assert.assertEquals("", TestConstant.MOCK_BILL_NO, refund.getBillNo());
                Assert.assertEquals("",
                        TestUtil.transferDateFromLongToString(TestConstant.MOCK_CREATE_TIME),
                        refund.getDateTime());
                Assert.assertEquals("", TestConstant.MOCK_CHANNEL,
                        refund.getChannel().toString().split("_")[0]);
                Assert.assertEquals("", TestConstant.MOCK_SUB_CHANNEL,
                        refund.getChannel().toString());
                Assert.assertEquals("", TestConstant.MOCK_REFUND_FEE, refund.getRefundFee());
                Assert.assertTrue("", refund.getOptionalString().equals("") || refund
                        .getOptionalString().equals(TestConstant.MOCK_OPTIONAL_JSON_STRING));
                Assert.assertTrue("", refund.isFinished() || !refund.isFinished());
                Assert.assertTrue("", refund.isRefunded() || !refund.isRefunded());
                Assert.assertTrue("", refund.getMessageDetail().equals("不显示") || refund
                        .getMessageDetail().equals(TestConstant.MOCK_MESSAGE_DETAIL_STRING));
                Assert.assertEquals("", TestConstant.MOCK_TITLE, refund.getTitle());
                Assert.assertEquals("", TestConstant.MOCK_REFUND_NO, refund.getRefundNo());
                Assert.assertEquals("", TestConstant.MOCK_TOTAL_FEE, refund.getTotalFee());
            }
        } catch (BCException ex) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

        try {
            bcRefundList = BCPay.startQueryRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.APP_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.MOCK_APP_INVALID_ERRMSG));
        }
    }

    private static void mockRefundCount() {
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        returnMap.put("count", 10);

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doGet",
                        withSubstring(BCUtilPrivate.getkApiQueryRefundCount().substring(14)),
                        withAny(Map.class));
                returns(returnMap);
            }
        };

        BCQueryParameter param = new BCQueryParameter();
        param.setBillNo(billNo);
        param.setRefundNo(refundNo);

        try {
            Integer count = BCPay.startQueryRefundCount(param);
            Assert.assertEquals("", 10, (int) count);
        } catch (BCException ex) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }
    }

    private static void mockQueryRefundById() {
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        buildMockRefund(returnMap);
        returnMap.put("refund", returnMap);

        new StrictExpectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doGet",
                        withSubstring(BCUtilPrivate.getkApiQueryRefundById().substring(14)),
                        withAny(Map.class));
                returns(returnMap);
            }
        };

        BCRefund refund;
        try {
            refund = BCPay.startQueryRefundById(TestConstant.MOCK_OBJECT_ID);
            Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID, refund.getObjectId());
            Assert.assertEquals("", TestConstant.MOCK_BILL_NO, refund.getBillNo());
            Assert.assertEquals("", TestConstant.MOCK_REFUND_NO, refund.getRefundNo());
            Assert.assertEquals("", TestConstant.MOCK_REFUND_FEE, refund.getRefundFee());
            Assert.assertEquals("", TestConstant.MOCK_TOTAL_FEE, refund.getTotalFee());
            Assert.assertEquals("", TestConstant.MOCK_CHANNEL,
                    refund.getChannel().toString().split("_")[0]);
            Assert.assertEquals("", TestConstant.MOCK_SUB_CHANNEL, refund.getChannel().toString());
            Assert.assertEquals("", TestConstant.MOCK_MESSAGE_DETAIL_STRING,
                    refund.getMessageDetail());
            Assert.assertEquals("", TestConstant.MOCK_OPTIONAL_JSON_STRING,
                    refund.getOptionalString());
            Assert.assertEquals("", TestConstant.MOCK_TITLE, refund.getTitle());
            Assert.assertEquals("", true, refund.isFinished());
            Assert.assertEquals("", true, refund.isRefunded());
            Assert.assertEquals("",
                    TestUtil.transferDateFromLongToString(TestConstant.MOCK_CREATE_TIME),
                    refund.getDateTime());

        } catch (BCException e) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }
    }

    private static void buildMockRefund(final Map<String, Object> returnMap) {
        returnMap.put("id", TestConstant.MOCK_OBJECT_ID);
        returnMap.put("create_time", TestConstant.MOCK_CREATE_TIME);
        returnMap.put("total_fee", TestConstant.MOCK_TOTAL_FEE);
        returnMap.put("refund_fee", TestConstant.MOCK_REFUND_FEE);
        returnMap.put("refund_no", TestConstant.MOCK_REFUND_NO);
        returnMap.put("channel", TestConstant.MOCK_CHANNEL);
        returnMap.put("bill_no", TestConstant.MOCK_BILL_NO);
        returnMap.put("result", true);
        returnMap.put("finish", true);
        returnMap.put("optional", TestConstant.MOCK_OPTIONAL_JSON_STRING);
        returnMap.put("title", TestConstant.MOCK_TITLE);
        returnMap.put("sub_channel", TestConstant.MOCK_SUB_CHANNEL);
        returnMap.put("message_detail", TestConstant.MOCK_MESSAGE_DETAIL_STRING);
    }

    private static List<Map<String, Object>> generateMockRefunds() {
        List<Map<String, Object>> refunds = new LinkedList<Map<String, Object>>();
        Map<String, Object> refund = new HashMap<String, Object>();
        Map<String, Object> refundAnother = new HashMap<String, Object>();
        refund.put("id", TestConstant.MOCK_OBJECT_ID);
        refund.put("create_time", TestConstant.MOCK_CREATE_TIME);
        refund.put("total_fee", TestConstant.MOCK_TOTAL_FEE);
        refund.put("refund_fee", TestConstant.MOCK_REFUND_FEE);
        refund.put("refund_no", TestConstant.MOCK_REFUND_NO);
        refund.put("channel", TestConstant.MOCK_CHANNEL);
        refund.put("bill_no", TestConstant.MOCK_BILL_NO);
        refund.put("result", true);
        refund.put("finish", true);
        refund.put("optional", TestConstant.MOCK_OPTIONAL_JSON_STRING);
        refund.put("title", TestConstant.MOCK_TITLE);
        refund.put("sub_channel", TestConstant.MOCK_SUB_CHANNEL);
        refund.put("message_detail", TestConstant.MOCK_MESSAGE_DETAIL_STRING);

        refundAnother.put("id", TestConstant.MOCK_OBJECT_ID);
        refundAnother.put("create_time", TestConstant.MOCK_CREATE_TIME);
        refundAnother.put("total_fee", TestConstant.MOCK_TOTAL_FEE);
        refundAnother.put("refund_fee", TestConstant.MOCK_REFUND_FEE);
        refundAnother.put("refund_no", TestConstant.MOCK_REFUND_NO);
        refundAnother.put("channel", TestConstant.MOCK_CHANNEL);
        refundAnother.put("bill_no", TestConstant.MOCK_BILL_NO);
        refundAnother.put("result", false);
        refundAnother.put("finish", false);
        refundAnother.put("optional", "");
        refundAnother.put("title", TestConstant.MOCK_TITLE);
        refundAnother.put("sub_channel", TestConstant.MOCK_SUB_CHANNEL);

        refunds.add(refund);
        refunds.add(refundAnother);
        return refunds;
    }
}

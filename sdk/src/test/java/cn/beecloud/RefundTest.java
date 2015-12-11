package cn.beecloud;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mockit.Deencapsulation;
import mockit.Expectations;

import org.junit.Assert;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCRefund;


/**
 * 退款单元测试
 * 
 * @author Rui
 * @since 2015/11/08
 */
public class RefundTest {

    static String billNo = BCUtil.generateRandomUUIDPure();
    static String refundNo = new SimpleDateFormat("yyyyMMdd").format(new Date())
            + BCUtil.generateNumberWith3to24digitals();
    static String subject = "javasdk unit test";
    static PAY_CHANNEL channel = PAY_CHANNEL.ALI;
    static PAY_CHANNEL channelWX = PAY_CHANNEL.WX;

    static void testRefund() {

        BCRefund param = new BCRefund();
        initRefundPara(param);

        if (BCCache.isSandbox()) {
            try {
                BCPay.startBCRefund(param);
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
            BCPay.startBCRefund(null);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.REFUND_PARAM_EMPTY));
        }

        try {
            param.setBillNo(null);
            BCPay.startBCRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.BILL_NO_EMPTY));
        }
        param.setBillNo(billNo);

        try {
            param.setRefundFee(null);
            BCPay.startBCRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage()
                    .contains(TestConstant.REFUND_FEE_EMPTY));
        }
        param.setRefundFee(1);

        try {
            param.setRefundFee(0);
            BCPay.startBCRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.REFUND_FEE_INVALID));
        }
        param.setRefundFee(1);

        try {
            param.setRefundNo(null);
            BCPay.startBCRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.REFUND_NO_EMPTY));
        }
        param.setRefundNo(refundNo);

        try {
            param.setBillNo(billNo.substring(0, 7));
            BCPay.startBCRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }
        param.setBillNo(billNo);

        try {
            param.setBillNo(billNo + "A");
            BCPay.startBCRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }
        param.setBillNo(billNo);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_WEEK, -1);
        String date = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
        try {
            param.setRefundNo(date + BCUtil.generateNumberWith3to24digitals());
            BCPay.startBCRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }
        param.setRefundNo(refundNo);

        try {
            date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            param.setRefundNo(date + "000");
            BCPay.startBCRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }
        param.setRefundNo(refundNo);

        try {
            param.setRefundNo(date + TestConstant.REFUND_NO_SERIAL_NUMBER_LESSER_THAN_3);
            BCPay.startBCRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }
        param.setRefundNo(refundNo);

        try {
            param.setRefundNo(date + TestConstant.REFUND_NO_SERIAL_NUMBER_GREATER_THAN_24);
            BCPay.startBCRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }
        param.setRefundNo(refundNo);

        try {
            param.setRefundNo(date + TestConstant.REFUND_NO_SERIAL_NUMBER_WITH_SPECIAL_CHARACTER);
            BCPay.startBCRefund(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.REFUND_NO_FORMAT_INVALID));
        }
        param.setRefundNo(refundNo);
        //mock网络请求
        mockRefund(param);
    }

    static void testRefundUpdate() {
        String message;

        if (BCCache.isSandbox()) {
            try {
                BCPay.startRefundUpdate(null, refundNo);
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
            message = BCPay.startRefundUpdate(null, refundNo);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.CHANNEL_EMPTY));
        }

        try {
            message = BCPay.startRefundUpdate(PAY_CHANNEL.ALI, null);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.REFUND_UPDATE_CHANNEL_INVALID));
        }

        mockRefundUpdate();
    }

    private static void mockRefund(BCRefund refund) {
        final Map<String, Object> returnMapWithUrl = new HashMap<String, Object>();
        returnMapWithUrl.put("id", TestConstant.MOCK_OBJECT_ID);
        returnMapWithUrl.put("result_code", 0);
        returnMapWithUrl.put("result_msg", "OK");
        returnMapWithUrl.put("err_detail", "");
        returnMapWithUrl.put("url", TestConstant.MOCK_ALI_REFUND_URL);

        final Map<String, Object> returnMapWithoutUrl = new HashMap<String, Object>();
        returnMapWithoutUrl.put("id", TestConstant.MOCK_OBJECT_ID);
        returnMapWithoutUrl.put("result_code", 0);
        returnMapWithoutUrl.put("result_msg", "OK");
        returnMapWithoutUrl.put("err_detail", "");

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doPost", withSubstring(BCUtilPrivate
                        .getkApiRefund().substring(14)), withAny(Map.class));
                returns(returnMapWithUrl, returnMapWithoutUrl);
                result = new BCException(RESULT_TYPE.CHANNEL_ERROR.ordinal(),
                        RESULT_TYPE.CHANNEL_ERROR.name(), RESULT_TYPE.CHANNEL_ERROR.name());
                result = new BCException(RESULT_TYPE.NO_SUCH_BILL.ordinal(),
                        RESULT_TYPE.NO_SUCH_BILL.name(), RESULT_TYPE.NO_SUCH_BILL.name());
            }
        };

        try {
            refund.setChannel(PAY_CHANNEL.ALI);
            BCPay.startBCRefund(refund);
            Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID, refund.getObjectId());
            Assert.assertEquals("", TestConstant.MOCK_ALI_REFUND_URL, refund.getAliRefundUrl());
        } catch (BCException ex) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }
        refund.setAliRefundUrl(null);

        try {
            refund.setChannel(PAY_CHANNEL.WX);
            refund.setNeedApproval(true);
            BCPay.startBCRefund(refund);
            Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID, refund.getObjectId());
            Assert.assertEquals("", null, refund.getAliRefundUrl());
        } catch (BCException ex) {
            ex.printStackTrace();
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

        try {
            BCPay.startBCRefund(refund);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.CHANNEL_ERROR.name()));
        }

        try {
            BCPay.startBCRefund(refund);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.NO_SUCH_BILL.name()));
        }
    }

    private static void mockRefundUpdate() {
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        returnMap.put("refund_status", TestConstant.MOCK_REFUND_UPDATE_MSG);

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doGet", withSubstring(BCUtilPrivate
                        .getkApiRefundUpdate().substring(14)), withAny(Map.class));
                returns(returnMap);
            }
        };

        try {
            String message = BCPay.startRefundUpdate(channelWX, refundNo);
            Assert.assertEquals("", TestConstant.MOCK_REFUND_UPDATE_MSG, message);
        } catch (BCException e) {
            e.printStackTrace();
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }
    }

    private static void initRefundPara(BCRefund refund) {
        refund.setBillNo(billNo);
        refund.setRefundNo(refundNo);
        refund.setRefundFee(1);
        refund.setTitle(subject);
        refund.setNeedApproval(false);
    }

}

package cn.beecloud;

import java.util.HashMap;
import java.util.Map;

import mockit.Deencapsulation;
import mockit.Expectations;

import org.junit.Assert;

import cn.beecloud.BCEumeration.CARD_TYPE;
import cn.beecloud.BCEumeration.PAYPAL_CURRENCY;
import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCInternationlOrder;
import cn.beecloud.bean.CreditCardInfo;


/**
 * 国际支付单元测试
 * 
 * @author Rui
 * @since 2015-11-10
 */
public class InternationalPayTest {

    static String billNo = BCUtil.generateRandomUUIDPure();
    static PAY_CHANNEL channel = PAY_CHANNEL.PAYPAL_PAYPAL;
    static PAYPAL_CURRENCY currency = PAYPAL_CURRENCY.USD;
    static Integer totalFee = 1;
    static String title = "international pay test";
    static String creditCardId = TestConstant.MOCK_CREDIT_CARD_ID;
    static String returnUrl = "http://beecloud.cn/return.url";
    static CreditCardInfo creditCardInfo = new CreditCardInfo();
    static String cardNo = "1234567899";
    static Integer expireMonth = 11;
    static Integer expireYear = 19;
    static Integer cvv = 220;
    static String firstName = "firstName";
    static String lastName = "lastName";
    static CARD_TYPE cardType = CARD_TYPE.mastercard;

    static {
        creditCardInfo.setCardNo(cardNo);
        creditCardInfo.setCardType(cardType);
        creditCardInfo.setCvv(cvv);
        creditCardInfo.setExpireMonth(expireMonth);
        creditCardInfo.setExpireYear(expireYear);
        creditCardInfo.setFirstName(firstName);
        creditCardInfo.setLastName(lastName);
    }

    static void testInternationalPay() {
        BCInternationlOrder order = new BCInternationlOrder();
        initInternationalPayParam(order);

        if (BCCache.isSandbox()) {
            try {
                BCPay.startBCInternatioalPay(order);
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
            BCPay.startBCInternatioalPay(null);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.INTERNATIONAL_PAY_PARAM_EMPTY));
        }

        try {
            order.setBillNo(null);
            BCPay.startBCInternatioalPay(order);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.BILL_NO_EMPTY));
        }
        order.setBillNo(billNo);

        try {
            order.setChannel(null);
            BCPay.startBCInternatioalPay(order);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.CHANNEL_EMPTY));
        }
        order.setChannel(channel);

        try {
            order.setTotalFee(null);
            BCPay.startBCInternatioalPay(order);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.TOTAL_FEE_EMPTY));
        }
        order.setTotalFee(totalFee);

        try {
            order.setBillNo(billNo.substring(0, 7));
            BCPay.startBCInternatioalPay(order);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }

        try {
            order.setBillNo(billNo + "A");
            BCPay.startBCInternatioalPay(order);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }

        try {
            order.setBillNo(TestConstant.BILL_NO_WITH_SPECIAL_CHARACTER);
            BCPay.startBCInternatioalPay(order);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }
        order.setBillNo(billNo);

        try {
            order.setCurrency(null);
            BCPay.startBCInternatioalPay(order);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.CURRENCY_EMPTY));
        }
        order.setCurrency(currency);

        try {
            order.setTitle(null);
            BCPay.startBCInternatioalPay(order);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(), e.getMessage().contains(TestConstant.TITLE_EMPTY));
        }

        try {
            order.setTitle(TestConstant.TITLE_WITH_CHARACTER＿GREATER_THAN_32);
            BCPay.startBCInternatioalPay(order);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(TestConstant.TITLE_FORMAT_INVALID));
        }

        try {
            order.setTitle(TestConstant.MOCK_TITLE_16_CHINESE_CHARACTER);
            BCPay.startBCInternatioalPay(order);
        } catch (BCException ex) {
            Assert.assertTrue("", !ex.getMessage().contains(TestConstant.TITLE_FORMAT_INVALID));
        }
        order.setTitle(title);

        try {
            order.setCreditCardInfo(null);
            order.setChannel(PAY_CHANNEL.PAYPAL_CREDITCARD);
            BCPay.startBCInternatioalPay(order);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (BCException ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue("", ex.getMessage().contains(TestConstant.CREDIT_CARD_INFO_EMPTY));
        }
        order.setCreditCardInfo(creditCardInfo);

        try {
            order.setCreditCardId(null);
            order.setChannel(PAY_CHANNEL.PAYPAL_SAVED_CREDITCARD);
            BCPay.startBCInternatioalPay(order);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (BCException ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue("", ex.getMessage().contains(TestConstant.CREDIT_CARD_ID_EMPTY));
        }
        order.setCreditCardId(creditCardId);

        try {
            order.setChannel(PAY_CHANNEL.PAYPAL_PAYPAL);
            order.setReturnUrl(null);
            BCPay.startBCInternatioalPay(order);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (BCException ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue("", ex.getMessage().contains(TestConstant.PAYPAL_RETURN_URL_EMPTY));
        }
        order.setChannel(channel);
        order.setReturnUrl(returnUrl);
        // mock网络请求
        mockInternationalPay(order);
    }

    private static void mockInternationalPay(BCInternationlOrder order) {
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("id", TestConstant.MOCK_OBJECT_ID);
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        returnMap.put("url", TestConstant.MOCK_PAY_URL);

        final Map<String, Object> creditCardReturnMap = new HashMap<String, Object>();
        creditCardReturnMap.put("id", TestConstant.MOCK_OBJECT_ID);
        creditCardReturnMap.put("result_code", 0);
        creditCardReturnMap.put("result_msg", "OK");
        creditCardReturnMap.put("err_detail", "");
        creditCardReturnMap.put("credit_card_id", TestConstant.MOCK_CREDIT_CARD_ID);

        final Map<String, Object> savedCreditCardReturnMap = new HashMap<String, Object>();
        savedCreditCardReturnMap.put("id", TestConstant.MOCK_OBJECT_ID);
        savedCreditCardReturnMap.put("result_code", 0);
        savedCreditCardReturnMap.put("result_msg", "OK");
        savedCreditCardReturnMap.put("err_detail", "");

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doPost",
                        withSubstring(BCUtilPrivate.getApiInternationalPay().substring(14)),
                        withAny(Map.class));
                returns(returnMap, creditCardReturnMap, savedCreditCardReturnMap);
                result = new BCException(RESULT_TYPE.CHANNEL_ERROR.ordinal(),
                        RESULT_TYPE.CHANNEL_ERROR.name(), TestConstant.MOCK_CHANNEL_ERROR_MSG);
            }
        };

        try {
            order = BCPay.startBCInternatioalPay(order);
            Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID, order.getObjectId());
            Assert.assertEquals("", TestConstant.MOCK_PAY_URL, order.getUrl());
        } catch (BCException e) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

        try {
            order.setChannel(PAY_CHANNEL.PAYPAL_CREDITCARD);
            order = BCPay.startBCInternatioalPay(order);
            Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID, order.getObjectId());
            Assert.assertEquals("", TestConstant.MOCK_CREDIT_CARD_ID, order.getCreditCardId());
        } catch (BCException e) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

        try {
            order.setChannel(PAY_CHANNEL.PAYPAL_SAVED_CREDITCARD);
            order = BCPay.startBCInternatioalPay(order);
            Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID, order.getObjectId());
        } catch (BCException e) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

        try {
            order = BCPay.startBCInternatioalPay(order);
            Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID, order.getObjectId());
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.CHANNEL_ERROR.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.MOCK_CHANNEL_ERROR_MSG));
        }
    }

    private static void initInternationalPayParam(BCInternationlOrder order) {
        order.setBillNo(billNo);
        order.setChannel(channel);
        order.setCreditCardId(creditCardId);
        order.setCreditCardInfo(creditCardInfo);
        order.setCurrency(currency);
        order.setReturnUrl(returnUrl);
        order.setTitle(title);
        order.setTotalFee(totalFee);
    }
}

package cn.beecloud;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mockit.Deencapsulation;
import mockit.Expectations;

import org.junit.Assert;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.BCBatchRefund;
import cn.beecloud.bean.BCException;


/**
 * 批量审核单元测试
 * 
 * @author Rui
 * @since 2015/11/09
 */
public class BatchHandleTest {

    static Boolean agree = true;
    static PAY_CHANNEL channel = PAY_CHANNEL.ALI;
    static List<String> idList;

    static {
        idList = new LinkedList<String>();
        idList.add(TestConstant.MOCK_APPROVE_IDLIST_ID1);
        idList.add(TestConstant.MOCK_APPROVE_IDLIST_ID2);
    }

    static void testBatchRefund() {
        BCBatchRefund batchRefund = new BCBatchRefund();
        initBatchRefundPara(batchRefund);

        if (BCCache.isSandbox()) {
            try {
                BCPay.startBatchRefund(batchRefund);
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
            batchRefund = BCPay.startBatchRefund(null);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.BATCH_REFUND_PARAM_EMPTY));
        }

        try {
            batchRefund.setAgree(null);
            batchRefund = BCPay.startBatchRefund(batchRefund);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.BATCH_REFUND_AGREE_EMPTY));
        }
        batchRefund.setAgree(agree);

        try {
            batchRefund.setChannel(null);
            batchRefund = BCPay.startBatchRefund(batchRefund);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.BATCH_REFUND_CHANNEL_EMPTY));
        }
        batchRefund.setChannel(channel);

        try {
            batchRefund.setIds(null);
            batchRefund = BCPay.startBatchRefund(batchRefund);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.BATCH_REFUND_ID_LIST_EMPTY));
        }
        batchRefund.setIds(idList);
        //mock 网络请求
        mockBatchRefund(batchRefund);
    }

    private static void mockBatchRefund(BCBatchRefund batchRefund) {
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("id", TestConstant.MOCK_OBJECT_ID);
        Map<String, String> map = new HashMap<String, String>();
        map.put(TestConstant.MOCK_APPROVE_IDLIST_ID1, TestConstant.MOCK_APPROVE_ID1_RESULT);
        map.put(TestConstant.MOCK_APPROVE_IDLIST_ID2, TestConstant.MOCK_APPROVE_ID2_RESULT);
        returnMap.put("result_map", map);
        returnMap.put("url", TestConstant.MOCK_ALI_REFUND_URL);
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");

        final Map<String, Object> returnMapDeny = new HashMap<String, Object>();
        returnMapDeny.put("result_code", 0);
        returnMapDeny.put("result_msg", "OK");
        returnMapDeny.put("err_detail", "");

        new Expectations() {
            {
                Deencapsulation.invoke(BCPay.class, "doPut", withSubstring(BCUtilPrivate
                        .getApiBatchRefund().substring(14)), withAny(Map.class));
                returns(returnMap, returnMapDeny);
                result = new BCException(RESULT_TYPE.PAY_FACTOR_NOT_SET.ordinal(),
                        RESULT_TYPE.PAY_FACTOR_NOT_SET.name(),
                        RESULT_TYPE.PAY_FACTOR_NOT_SET.name());
            }
        };

        try {
            batchRefund = BCPay.startBatchRefund(batchRefund);
            for (Map.Entry<String, String> entry : batchRefund.getIdResult().entrySet()) {
                Assert.assertTrue("", entry.getKey().equals(TestConstant.MOCK_APPROVE_IDLIST_ID1)
                        || entry.getKey().equals(TestConstant.MOCK_APPROVE_IDLIST_ID2));
                Assert.assertTrue("", entry.getValue().equals(TestConstant.MOCK_APPROVE_ID1_RESULT)
                        || entry.getValue().equals(TestConstant.MOCK_APPROVE_ID2_RESULT));
            }
            Assert.assertEquals("", TestConstant.MOCK_ALI_REFUND_URL, batchRefund.getAliRefundUrl());
        } catch (BCException e) {
            e.printStackTrace();
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }
        batchRefund.setAliRefundUrl(null);
        batchRefund.setIdResult(new HashMap<String, String>());

        try {
            batchRefund.setAgree(false);
            batchRefund = BCPay.startBatchRefund(batchRefund);
            Assert.assertEquals("", null, batchRefund.getAliRefundUrl());
            Assert.assertTrue("", batchRefund.getIdResult().isEmpty());
        } catch (BCException e) {
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }
    }

    private static void initBatchRefundPara(BCBatchRefund batchRefund) {
        batchRefund.setAgree(agree);
        batchRefund.setChannel(PAY_CHANNEL.ALI);
        batchRefund.setIds(idList);
    }
}

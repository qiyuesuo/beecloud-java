package cn.beecloud;

import static junit.framework.Assert.assertEquals;

import cn.beecloud.bean.BCT0TransferParameter;
import java.util.*;

import mockit.Deencapsulation;
import mockit.Expectations;

import org.junit.Assert;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.BCException;
import cn.beecloud.bean.ALITransferData;
import cn.beecloud.bean.TransfersParameter;
import org.junit.Test;

/**
 * 批量打款单元测试
 * 
 * @author BweeCloud
 * @since 2015/111/14
 */
public class TransfersTest {

    static String batchNo = BCUtil.generateRandomUUIDPure();
    static String transferId1 = BCUtil.generateRandomUUIDPure();
    static String transferId2 = BCUtil.generateRandomUUIDPure();
    static String receiverName1 = TestConstant.ALI_TRANSFER_RECEIVER_NAME_1;
    static String receiverName2 = TestConstant.ALI_TRANSFER_RECEIVER_NAME_2;
    static String receiverAccount1 = TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1;
    static String receiverAccount2 = TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_2;
    static String transferNote = TestConstant.TRANSFER_NOTE;
    static List<ALITransferData> list = new ArrayList<ALITransferData>();
    static String accountName = TestConstant.ALI_TRANSFER_ACCOUNT_NAME;
    static PAY_CHANNEL channel = PAY_CHANNEL.ALI;
    static ALITransferData data1;
    static ALITransferData data2;

    static {
        data1 = new ALITransferData(transferId1, receiverAccount1, receiverName1, 1, transferNote);
        data2 = new ALITransferData(transferId2, receiverAccount2, receiverName2, 1, transferNote);
        list.add(data1);
        list.add(data2);
    }

    static void testTransfers() {
        TransfersParameter param = new TransfersParameter();
        initTransfersParam(param);

        if (BCCache.isSandbox()) {
            try {
                BCPay.startTransfers(param);
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

        String url = "";

        try {
            url = BCPay.startTransfers(null);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFERS_PARAM_EMPTY));
        }

        try {
            param.setChannel(null);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFERS_CHANNEL_EMPTY));
        }
        param.setChannel(channel);

        try {
            param.setBatchNo(null);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFERS_BATCH_NO_EMPTY));
        }
        param.setBatchNo(batchNo);

        try {
            param.setAccountName(null);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFERS_ACCOUNT_NAME_EMPTY));
        }
        param.setAccountName(accountName);

        try {
            param.setChannel(PAY_CHANNEL.WX);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFERS_CHANNEL_SUPPORT_INVALID));
        }
        param.setChannel(channel);

        try {
            param.setTransferDataList(null);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFERS_DATA_LIST_EMPTY));
        }
        param.setTransferDataList(list);

        try {
            param.setBatchNo(TestConstant.ALI_TRANSFER_BATCH_NO_WITH_SPECIAL_CHARACTER);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFERS_BATCH_NO_FORMAT_INVALID));
        }

        try {
            param.setBatchNo(batchNo + "A");
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFERS_BATCH_NO_FORMAT_INVALID));
        }

        try {
            param.setBatchNo(batchNo.substring(0, 10));
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFERS_BATCH_NO_FORMAT_INVALID));
        }
        param.setBatchNo(batchNo);

        try {
            data1.setTransferId(null);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_ID_EMPTY));
        }
        data1.setTransferId(transferId1);

        try {
            data1.setReceiverAccount(null);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.RECEIVER_ACCOUNT_EMPTY));
        }
        data1.setReceiverAccount(receiverAccount1);

        try {
            data1.setReceiverName(null);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.RECEIVER_NAME_EMPTY));
        }
        data1.setReceiverName(receiverName1);

        try {
            data1.setTransferFee(null);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_FEE_EMPTY));
        }
        data1.setTransferFee(1);

        try {
            data1.setTransferNote(null);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_NOTE_EMPTY));
        }
        data1.setTransferNote(transferNote);

        try {
            data1.setTransferId(transferId1 + "A");
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_ID_FORMAT_INVALID));
        }

        try {
            data1.setTransferId(TestConstant.INVALID_TRANSFER_ID);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_ID_FORMAT_INVALID));
        }
        data1.setTransferId(transferId1);

        try {
            data1.setTransferFee(-1);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_FEE_INVALID));
        }

        try {
            data1.setTransferFee(0);
            url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.TRANSFER_FEE_INVALID));
        }
        data1.setTransferFee(1);
        // mock网络请求
        mockTransfers(param);
    }

    private static void mockTransfers(TransfersParameter param) {

        final Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("result_code", 0);
        returnMap.put("result_msg", "OK");
        returnMap.put("err_detail", "");
        returnMap.put("url", TestConstant.MOCK_ALI_TRANSFER_URL);

        new Expectations() {
            {
                Deencapsulation.invoke(RequestUtil.class, "request",
                        withSubstring(BCUtilPrivate.getkApiTransfers().substring(19)),
                        withAny(Map.class), withAny(RequestUtil.REQUEST_TYPE.class));
                returns(returnMap);
                result = new BCException(RESULT_TYPE.APP_INVALID.ordinal(),
                        RESULT_TYPE.APP_INVALID.name(), TestConstant.MOCK_APP_INVALID_ERRMSG);
            }
        };

        try {
            String url = BCPay.startTransfers(param);
            assertEquals(TestConstant.ASSERT_MESSAGE, TestConstant.MOCK_ALI_TRANSFER_URL, url);
        } catch (BCException ex) {
            ex.printStackTrace();
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
        }

        try {
            String url = BCPay.startTransfers(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (BCException ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.APP_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.MOCK_APP_INVALID_ERRMSG));
        }
    }

    private static void initTransfersParam(TransfersParameter param) {
        param.setAccountName(accountName);
        param.setBatchNo(batchNo);
        param.setChannel(channel);
        param.setTransferDataList(list);
    }

    @Test
    public void testT0Transfer() {
        BeeCloud.registerApp("c89a3345-48ae-495c-96e1-dd34fb95ff3a", "",
                "6d43ee31-3b6e-44ea-b0e1-b37e8d3448a6", "5a45285b-75e7-475c-9296-d11bb5d06620");
        BCT0TransferParameter p = new BCT0TransferParameter(101, UUID.randomUUID().toString().replaceAll("-", ""),
                "1", "中国银行", "6217856101009660486", "高健峰", "boc", "test", "", null);
//        try {
//            Map<String, Object> result = BCPay.startBCT0transfer(p);
//            System.out.println(result);
//        } catch (BCException e) {
//            e.printStackTrace();
//        }
    }
}

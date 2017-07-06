package cn.beecloud;

import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.*;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.StrictExpectations;
import org.junit.Assert;

import java.util.*;


/**
 * 打款订单查询单元测试
 * 
 * @author sy
 * @since 2017/7/6
 */
public class TransferQueryTest {

    static String billNo = BCUtil.generateRandomUUIDPure();

    static void testTransferQuery() {
        BCTransferQueryParameter param = new BCTransferQueryParameter();
        List<BCTransferOrder> bcOrderList = new LinkedList<BCTransferOrder>();
        try {
            bcOrderList = BCPay.startTransferQuery(null);
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
            param.setNeedDetail(true);
            param.setTransferStatus(BCEumeration.TRANSFER_STATUS.PROCESSING);
            param.setStartTime(new Date(1449120000000l));
            param.setEndTime(new Date(System.currentTimeMillis()));
            bcOrderList = BCPay.startTransferQuery(param);
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
            bcOrderList = BCPay.startTransferQuery(param);
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
            bcOrderList = BCPay.startTransferQuery(param);
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
            bcOrderList = BCPay.startTransferQuery(param);
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
            bcOrderList = BCPay.startTransferQuery(param);
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
            bcOrderList = BCPay.startTransferQuery(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.LIMIT_FORMAT_INVALID));
        }
    }



    static void testTransferQueryCount() {
        BCTransferQueryParameter param = new BCTransferQueryParameter();
        Integer count;
        try {
            count = BCPay.startTransferQueryCount(null);
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
            count = BCPay.startTransferQueryCount(param);
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
            count = BCPay.startTransferQueryCount(param);
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
            count = BCPay.startTransferQueryCount(param);
            Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
            Assert.assertTrue(ex.getMessage(),
                    ex.getMessage().contains(TestConstant.BILL_NO_FORMAT_INVALID));
        }

    }

}

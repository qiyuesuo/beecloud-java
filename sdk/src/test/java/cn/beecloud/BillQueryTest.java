package cn.beecloud;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mockit.Deencapsulation;
import mockit.Expectations;

import org.junit.Assert;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCOrder;
import cn.beecloud.bean.BCQueryParameter;

public class BillQueryTest {
	
	static String billNo = BCUtil.generateRandomUUIDPure();

	static void testBillQuery() {
		BCQueryParameter param = new BCQueryParameter();
		List<BCOrder> bcOrderList = new LinkedList<BCOrder>();
		try {
			bcOrderList = BCPay.startQueryBill(null);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
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
			System.out.println(ex.getMessage());
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(
					ex.getMessage(),
					ex.getMessage().contains(
							TestConstant.BILL_NO_FORMAT_INVALID));
		}

		try {
			param.setBillNo(billNo.substring(0, 7));
			bcOrderList = BCPay.startQueryBill(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(
					ex.getMessage(),
					ex.getMessage().contains(
							TestConstant.BILL_NO_FORMAT_INVALID));
		}

		try {
			param.setBillNo(billNo + "A");
			bcOrderList = BCPay.startQueryBill(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(
					ex.getMessage(),
					ex.getMessage().contains(
							TestConstant.BILL_NO_FORMAT_INVALID));
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, +1);
		param.setStartTime(cal.getTime());
		cal.add(Calendar.MONTH, -1);
		param.setEndTime(cal.getTime());
		try {
			bcOrderList = BCPay.startQueryBill(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
		}
		param.setStartTime(null);
		param.setEndTime(null);

		try {
			param.setSkip(-1);
			bcOrderList = BCPay.startQueryBill(param);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
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

		mockQueryBill();
	}

	private void mockQueryBill() {
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
						withSubstring(BCUtilPrivate.getkApiQueryBillCount()
								.substring(14)), withAny(Map.class));
				returns(returnMap);
				result = new BCException(RESULT_TYPE.APP_INVALID.ordinal(),
						RESULT_TYPE.APP_INVALID.name(),
						TestConstant.MOCK_APP_INVALID_ERRMSG);
			}
		};

		BCQueryParameter param = new BCQueryParameter();
		param.setBillNo(billNo);
		try {
			bcOrderList = BCPay.startQueryBill(param);
			Assert.assertEquals("", 2, bcOrderList.size());
			for (BCOrder order : bcOrderList) {
				Assert.assertEquals("", TestConstant.MOCK_OBJECT_ID,
						order.getObjectId());
				Assert.assertEquals("", TestConstant.MOCK_BILL_NO,
						order.getBillNo());
				Assert.assertEquals(
						"",
						TestUtil.transferDateFromLongToString(TestConstant.MOCK_CREATE_TIME),
						order.getDateTime());
				Assert.assertEquals("", TestConstant.MOCK_CHANNEL, order
						.getChannel().toString().split("_")[0]);
				Assert.assertEquals("", TestConstant.MOCK_SUB_CHANNEL, order
						.getChannel().toString());
				Assert.assertEquals("", TestConstant.MOCK_PAY_RESULT,
						order.isRefundResult());
				Assert.assertTrue(
						"",
						order.getOptionalString().equals("")
								|| order.getOptionalString().equals(
										TestConstant.MOCK_OPTIONAL_JSON_STRING));
				Assert.assertEquals("", true, order.isRevertResult());
				Assert.assertEquals("", true, order.isRefundResult());
				Assert.assertTrue(
						"",
						order.getMessageDetail().equals("不显示")
								|| order.getMessageDetail()
										.equals(TestConstant.MOCK_MESSAGE_DETAIL_STRING));
				Assert.assertEquals("", TestConstant.MOCK_TITLE,
						order.getTitle());
				Assert.assertEquals("", TestConstant.MOCK_TOTAL_FEE,
						order.getTotalFee());
				Assert.assertTrue(
						"",
						order.getChannelTradeNo().equals(
								TestConstant.MOCK_TRADE_NO)
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
			Assert.assertTrue(
					ex.getMessage(),
					ex.getMessage().contains(
							TestConstant.MOCK_APP_INVALID_ERRMSG));
		}
	}
	
	private List<Map<String, Object>> generateMockBills() {
		List<Map<String, Object>> bills = new LinkedList<Map<String, Object>>();
		Map<String, Object> bill = new HashMap<String, Object>();
		Map<String, Object> billAnother = new HashMap<String, Object>();
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
		
		bills.add(bill);
		bills.add(billAnother);
		return bills;
	}

}

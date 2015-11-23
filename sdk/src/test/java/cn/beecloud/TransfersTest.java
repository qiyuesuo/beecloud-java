package cn.beecloud;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mockit.Deencapsulation;
import mockit.Expectations;

import org.junit.Assert;

import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.BCEumeration.TRANSFER_CHANNEL;
import cn.beecloud.bean.BCException;
import cn.beecloud.bean.ALITransferData;

public class TransfersTest {
	
	static String batchNo = BCUtil.generateRandomUUIDPure();
	static String transferId1 = BCUtil.generateRandomUUIDPure();
	static String transferId2 = BCUtil.generateRandomUUIDPure();
	static List<ALITransferData> list = new ArrayList<ALITransferData>();
	static ALITransferData data1;
	static ALITransferData data2;

	static {
		data1 = new ALITransferData(transferId1,
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1,
				TestConstant.TRANSFER_NOTE);
		data2 = new ALITransferData(transferId2,
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_2,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_2, 1,
				TestConstant.TRANSFER_NOTE);
		list.add(data1);
		list.add(data2);
	}

	static void testTransfers() {
		String url = "";
		try {
			url = BCPay.startTransfers(null, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(TestConstant.CHANNEL_EMPTY));
		}

		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, null,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(TestConstant.BATCH_NO_EMPTY));
		}

		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.WX_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(
					ex.getMessage(),
					ex.getMessage().contains(
							TestConstant.CHANNEL_SUPPORT_INVALID));
		}

		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo, null, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(TestConstant.ACCOUNT_NAME_EMPTY));
		}

		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, null);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(TestConstant.TRANSFER_DATA_EMPTY));
		}

		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER,
					TestConstant.ALI_TRANSFER_BATCH_NO_WITH_SPECIAL_CHARACTER,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(
					ex.getMessage(),
					ex.getMessage().contains(
							TestConstant.BATCH_NO_FORMAT_INVALID));
		}

		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo + "A",
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(
					ex.getMessage(),
					ex.getMessage().contains(
							TestConstant.BATCH_NO_FORMAT_INVALID));
		}

		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER,
					batchNo.substring(0, 10),
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(
					ex.getMessage(),
					ex.getMessage().contains(
							TestConstant.BATCH_NO_FORMAT_INVALID));
		}

		ALITransferData data1 = new ALITransferData(null,
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1,
				TestConstant.TRANSFER_NOTE);
		list.add(data1);
		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(TestConstant.TRANSFER_ID_EMPTY));
		}
		list.remove(data1);

		data1 = new ALITransferData(transferId1, null,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1,
				TestConstant.TRANSFER_NOTE);
		list.add(data1);
		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(
					ex.getMessage(),
					ex.getMessage().contains(
							TestConstant.RECEIVER_ACCOUNT_EMPTY));
		}
		list.remove(data1);

		data1 = new ALITransferData(transferId1,
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1, null, 1,
				TestConstant.TRANSFER_NOTE);
		list.add(data1);
		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(TestConstant.RECEIVER_NAME_EMPTY));
		}
		list.remove(data1);

		data1 = new ALITransferData(transferId1,
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, null,
				TestConstant.TRANSFER_NOTE);
		list.add(data1);
		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(TestConstant.TRANSFER_FEE_EMPTY));
		}
		list.remove(data1);

		data1 = new ALITransferData(transferId1,
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1, null);
		list.add(data1);
		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(TestConstant.TRANSFER_NOTE_EMPTY));
		}
		list.remove(data1);

		data1 = new ALITransferData(transferId1 + "A",
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1,
				TestConstant.TRANSFER_NOTE);
		list.add(data1);
		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(
					ex.getMessage(),
					ex.getMessage().contains(
							TestConstant.TRANSFER_ID_FORMAT_EMPTY));
		}
		list.remove(data1);

		data1 = new ALITransferData("",
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1,
				TestConstant.TRANSFER_NOTE);
		list.add(data1);
		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(TestConstant.TRANSFER_ID_EMPTY));
		}
		list.remove(data1);

		data1 = new ALITransferData(TestConstant.INVALID_TRANSFER_ID,
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1,
				TestConstant.TRANSFER_NOTE);
		list.add(data1);
		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(
					ex.getMessage(),
					ex.getMessage().contains(
							TestConstant.TRANSFER_ID_FORMAT_EMPTY));
		}
		list.remove(data1);

		data1 = new ALITransferData(transferId1,
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, -1,
				TestConstant.TRANSFER_NOTE);
		list.add(data1);
		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(TestConstant.TRANSFER_FEE_INVALID));
		}
		list.remove(data1);

		data1 = new ALITransferData(transferId1,
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 0,
				TestConstant.TRANSFER_NOTE);
		list.add(data1);
		try {
			url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (Exception ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.PARAM_INVALID.name()));
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(TestConstant.TRANSFER_FEE_INVALID));
		}
		list.remove(data1);
		list.remove(data2);

		mockAliTransfer();
	}

	private static void mockAliTransfer() {
		data1 = new ALITransferData(transferId1,
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_1,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_1, 1,
				TestConstant.TRANSFER_NOTE);
		data2 = new ALITransferData(transferId2,
				TestConstant.ALI_TRANSFER_RECEIVER_ACCOUNT_2,
				TestConstant.ALI_TRANSFER_RECEIVER_NAME_2, 1,
				TestConstant.TRANSFER_NOTE);
		list.add(data1);
		list.add(data2);

		final Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("result_code", 0);
		returnMap.put("result_msg", "OK");
		returnMap.put("err_detail", "");
		returnMap.put("url", TestConstant.MOCK_ALI_TRANSFER_URL);

		new Expectations() {
			{
				Deencapsulation.invoke(BCPay.class, "doPost",
						withSubstring(BCUtilPrivate.getkApiTransfers()
								.substring(14)), withAny(Map.class));
				returns(returnMap);
				result = new BCException(RESULT_TYPE.APP_INVALID.ordinal(),
						RESULT_TYPE.APP_INVALID.name(),
						TestConstant.MOCK_APP_INVALID_ERRMSG);
			}
		};

		try {
			String url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			assertEquals(TestConstant.ASSERT_MESSAGE,
					TestConstant.MOCK_ALI_TRANSFER_URL, url);
		} catch (BCException ex) {
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_THROWN);
		}
		
		try {
			String url = BCPay.startTransfers(TRANSFER_CHANNEL.ALI_TRANSFER, batchNo,
					TestConstant.ALI_TRANSFER_ACCOUNT_NAME, list);
			Assert.fail(TestConstant.ASSERT_MESSAGE_BCEXCEPTION_NOT_THROWN);
		} catch (BCException ex) {
			Assert.assertTrue(ex.getMessage(), ex instanceof BCException);
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(RESULT_TYPE.APP_INVALID.name()));
			Assert.assertTrue(ex.getMessage(),
					ex.getMessage().contains(TestConstant.MOCK_APP_INVALID_ERRMSG));
		}
	}
}

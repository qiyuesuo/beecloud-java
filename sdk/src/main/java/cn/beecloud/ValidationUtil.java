package cn.beecloud;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCOrder;
import cn.beecloud.bean.BCQueryParameter;
import cn.beecloud.bean.BCRefund;
import cn.beecloud.bean.BCRefundParameter;
import cn.beecloud.bean.TransferData;

/**
 * This class is used to unify the validation for all the
 * incoming parameters of payment.
 * @author Ray
 * @version 0.1
 * @since 2015/6/11 
 */
public class ValidationUtil 
{
	private final static String BILL_NO_FORMAT_INVALID =
			"billNo 是一个长度介于8至32字符的数字字母字符串！";
	
	private final static String BATCH_NO_FORMAT_INVALID =
			"batchNo 是一个长度在11到32个字符的数字字母字符串！";
	
	private final static String PAY_PARAM_EMPTY =
			"支付参数不能为空！";
	
	private final static String REFUND_PARAM_EMPTY = 
			"退款参数不能为空！";
	
	private final static String QUERY_PARAM_EMPTY = 
			"查询参数不能为空！";
	
	private final static String BILL_NO_EMPTY =
			"billNo 必填！";
	
	private final static String BATCH_NO_EMPTY =
			"batchNo 必填！";
	
	private final static String TRANSFER_DATA_EMPTY =
			"transferData 必填！";

	private final static String TRANSFER_ID_EMPTY =
			"transferId 不能为空！";
	
	private final static String RECEIVER_ACCOUNT_EMPTY =
			"receiverAccount 不能为空！";
	
	private final static String RECEIVER_NAME_EMPTY =
			"receiverName 不能为空！";
	
	private final static String TRANSFER_FEE_EMPTY =
			"transferFee 不能为空！";
	
	private final static String TRANSFER_FEE_INVALID = 
			"transferFee 必须大于0！";
	
	private final static String TRANSFER_NOTE_EMPTY =
			"transferNote 不能为空！";
	
	private final static String ACCOUNT_NAME_EMPTY =
			"accountName 必填！";
	
	private final static String TITLE_EMPTY =
			"title 必填！";
	
	private final static String TOTAL_FEE_EMPTY =
			"totalFee 必填！";
	
	private final static String REFUND_FEE_EMPTY =
			"refundFee 必填！";
	
	private final static String REFUND_FEE_INVALID =
			"refundFee 必须大于零！";
	
	private final static String QR_PAY_MODE_EMPTY =
			"qrPayMode 必填！";
	
	private final static String RETURN_URL_EMPTY = 
			"returnUrl 必填！";
	
	private final static String REFUND_NO_EMPTY =
			"refundNo 必填！";
	
	private final static String CHANNEL_EMPTY =
			"channel 必填！";
	
	private final static String YEE_NOBANCARD_FACTOR_EMPTY =
			"cardNo, cardPwd, frqid 必填！";
	
	private final static String REFUND_NO_FORMAT_INVALID =
			"refundNo 是格式为当前日期加3-24位数字字母（不能为000）流水号的字符串！ ";
	
	private final static String TITLE_FORMAT_INVALID =
			"title 是一个长度不超过32字节的字符串！";
	
	private final static String LIMIT_FORMAT_INVALID =
			"limit 的最大长度为50！";
	
	private final static String OPENID_EMPTY =
			"openid 必填！";
	
	private final static String CHANNEL_INVALID_FOR_REFUND =
			"退款只支持WX, UN, ALI !";
	
	private final static String TRANSFER_ID_FORMAT_EMPTY = 
			"transferId 是一个长度不超过32字符的数字字母字符串！";
	
	private final static String TRANSFER_LIST_SIZE_INVALID = 
			"transferData 长度不能超过1000！";
	
	private final static String CHANNEL_SUPPORT_INVALID =
			"批量打款仅支持ALI";
	
	final static String PRE_REFUND_SUCCEED = "预退款成功！ ";
	
	final static String REFUND_REJECT = "退款被拒绝！ ";
	
	final static String REFUND_SUCCESS = "退款已经成功！ ";
	
	final static String PAY_SUCCESS = "支付成功！ ";
	
	public static BCPayResult validateBCPay(PAY_CHANNEL channel, String billNo, String title, String returnUrl, String openId) {
		if (channel == null) {
			return new BCPayResult(CHANNEL_EMPTY, RESULT_TYPE.PARAM_INVALID);
		} else if (StrUtil.empty(billNo)) {
			return new BCPayResult(BILL_NO_EMPTY, RESULT_TYPE.PARAM_INVALID);
		} else if (!billNo.matches("[0-9A-Za-z]{8,32}")) {
			return new BCPayResult(BILL_NO_FORMAT_INVALID, RESULT_TYPE.PARAM_INVALID);
		} else if (StrUtil.empty(title)) {
			return new BCPayResult(TITLE_EMPTY, RESULT_TYPE.PARAM_INVALID);
		} else if (StrUtil.empty(returnUrl) && 
				(channel.equals(PAY_CHANNEL.ALI_WEB) || 
						channel.equals(PAY_CHANNEL.ALI_QRCODE) || 
							channel.equals(PAY_CHANNEL.UN_WEB))) {
			return new BCPayResult(RETURN_URL_EMPTY, RESULT_TYPE.PARAM_INVALID);
		} else if (channel.equals(PAY_CHANNEL.WX_JSAPI) && StrUtil.empty(openId)){
			return new BCPayResult(OPENID_EMPTY, RESULT_TYPE.PARAM_INVALID);
		} else
			try {
				if (title.getBytes("GBK").length > 32) {
					return new BCPayResult(TITLE_FORMAT_INVALID, RESULT_TYPE.PARAM_INVALID);
				}
			} catch (UnsupportedEncodingException e) {
				if (title.length() > 16) {
					return new BCPayResult(TITLE_FORMAT_INVALID, RESULT_TYPE.PARAM_INVALID);
				}
			}
		
		return new BCPayResult(RESULT_TYPE.OK);	
	}

	public static BCQueryStatusResult validateQueryRefundStatus(PAY_CHANNEL channel,
			String refundNo) {
		if (channel == null) {
			return new BCQueryStatusResult(CHANNEL_EMPTY, RESULT_TYPE.PARAM_INVALID);
		} else if (StrUtil.empty(refundNo)) {
			return new BCQueryStatusResult(REFUND_NO_EMPTY, RESULT_TYPE.PARAM_INVALID);
		}
		return new BCQueryStatusResult(RESULT_TYPE.OK);
	}

	public static BCPayResult validateBCTransfer(PAY_CHANNEL channel,
			String batchNo, String accountName, List<TransferData> transferData) {
		if (channel == null) {
			return new BCPayResult(CHANNEL_EMPTY, RESULT_TYPE.PARAM_INVALID);
		} else if (!channel.equals(PAY_CHANNEL.ALI)) { 
			return new BCPayResult(CHANNEL_SUPPORT_INVALID, RESULT_TYPE.PARAM_INVALID);
		} else if (batchNo == null) {
			return new BCPayResult(BATCH_NO_EMPTY, RESULT_TYPE.PARAM_INVALID);
		} else if (!batchNo.matches("[0-9A-Za-z]{11,32}")) {
			return new BCPayResult(BATCH_NO_FORMAT_INVALID, RESULT_TYPE.PARAM_INVALID);
		} else if (accountName == null) {
			return new BCPayResult(ACCOUNT_NAME_EMPTY, RESULT_TYPE.PARAM_INVALID);
		} else if (transferData == null) {
			return new BCPayResult(TRANSFER_DATA_EMPTY, RESULT_TYPE.PARAM_INVALID);
		}
		for(TransferData data : transferData) {
			if (StrUtil.empty(data.getTransferId())) {
				return new BCPayResult(TRANSFER_ID_EMPTY, RESULT_TYPE.PARAM_INVALID); 
			} else if (!data.getTransferId().matches("[0-9A-Za-z]{1,32}")) {
				return new BCPayResult(TRANSFER_ID_FORMAT_EMPTY, RESULT_TYPE.PARAM_INVALID); 
			} else if (StrUtil.empty(data.getReceiverAccount())) {
				return new BCPayResult(RECEIVER_ACCOUNT_EMPTY, RESULT_TYPE.PARAM_INVALID); 
			} else if (StrUtil.empty(data.getReceiverName())) {
				return new BCPayResult(RECEIVER_NAME_EMPTY, RESULT_TYPE.PARAM_INVALID); 
			} else if (StrUtil.empty(data.getTransferFee())) {
				return new BCPayResult(TRANSFER_FEE_EMPTY, RESULT_TYPE.PARAM_INVALID); 
			} else if (data.getTransferFee() <= 0) {
				return new BCPayResult(TRANSFER_FEE_INVALID, RESULT_TYPE.PARAM_INVALID); 
			} else if (StrUtil.empty(data.getTransferNote())) {
				return new BCPayResult(TRANSFER_NOTE_EMPTY, RESULT_TYPE.PARAM_INVALID); 
			}
		}
		
		if (transferData.size() > 1000) {
			return new BCPayResult(TRANSFER_LIST_SIZE_INVALID, RESULT_TYPE.PARAM_INVALID); 
		}
		return new BCPayResult(RESULT_TYPE.OK);
	}

	public static void validateBCPay(BCOrder para) throws BCException {
		
		if (para == null) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), PAY_PARAM_EMPTY);
		}
		if (para.getChannel() == null) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), CHANNEL_EMPTY);
		}  else if (StrUtil.empty(para.getBillNo())) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_EMPTY);
		}  else if (StrUtil.empty(para.getTitle())) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), TITLE_EMPTY);
		}  else if (StrUtil.empty(para.getTotalFee())) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), TOTAL_FEE_EMPTY);
		}  else if (para.getBillNo() != null && !para.getBillNo().matches("[0-9A-Za-z]{8,32}")) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_FORMAT_INVALID);
		}  else if (StrUtil.empty(para.getReturnUrl()) && 
				(para.getChannel().equals(PAY_CHANNEL.ALI_WEB) || 
						para.getChannel().equals(PAY_CHANNEL.ALI_QRCODE) || 
						para.getChannel().equals(PAY_CHANNEL.UN_WEB) ||
						para.getChannel().equals(PAY_CHANNEL.JD_WEB) ||
						para.getChannel().equals(PAY_CHANNEL.JD_WAP))) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), RETURN_URL_EMPTY);
		} else if (para.getChannel().equals(PAY_CHANNEL.WX_JSAPI) && StrUtil.empty(para.getOpenId())){
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), OPENID_EMPTY);
		} else if (para.getChannel().equals(PAY_CHANNEL.ALI_QRCODE) && StrUtil.empty(para.getQrPayMode())){
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), QR_PAY_MODE_EMPTY);
		} else if (para.getChannel().equals(PAY_CHANNEL.YEE_NOBANKCARD) && (para.getCardNo() == null ||
				para.getCardPwd() == null || para.getFrqid() == null)) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), YEE_NOBANCARD_FACTOR_EMPTY);
		} else
			try {
				if (para.getTitle().getBytes("GBK").length > 32) {
					throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), TITLE_FORMAT_INVALID);
				}
			} catch (UnsupportedEncodingException e) {
				if (para.getTitle().length() > 16) {
					throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), TITLE_FORMAT_INVALID);
				}
			}
	}

	public static void validateBCRefund(BCRefund para) throws BCException {
		if (para == null) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), REFUND_PARAM_EMPTY);
		} else if (StrUtil.empty(para.getBillNo())) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_EMPTY);
		} else if (StrUtil.empty(para.getRefundFee())) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), REFUND_FEE_EMPTY);
		} else if (para.getRefundFee() <=0) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), REFUND_FEE_INVALID);
		} else if (StrUtil.empty(para.getRefundNo())) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), REFUND_NO_EMPTY);
		} else if (para.getChannel() != null && !para.getChannel().equals(PAY_CHANNEL.WX) && !para.getChannel().equals(PAY_CHANNEL.ALI) && !para.getChannel().equals(PAY_CHANNEL.UN) 
				 && !para.getChannel().equals(PAY_CHANNEL.YEE) && !para.getChannel().equals(PAY_CHANNEL.JD) && !para.getChannel().equals(PAY_CHANNEL.KUAIQIAN) && !para.getChannel().equals(PAY_CHANNEL.BD)) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), CHANNEL_INVALID_FOR_REFUND); 
		} else if (!para.getRefundNo().startsWith(new SimpleDateFormat("yyyyMMdd").format(new Date()))){
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), REFUND_NO_FORMAT_INVALID); 
		} else if (!para.getRefundNo().substring(8, para.getRefundNo().length()).matches("[0-9A-Za-z]{3,24}") || 
				para.getRefundNo().substring(8, para.getRefundNo().length()).matches("000") ){
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), REFUND_NO_FORMAT_INVALID); 
		} else if (!para.getBillNo().matches("[0-9A-Za-z]{8,32}")) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_FORMAT_INVALID); 
		} 
	}

	public static void validateQueryBill(BCQueryParameter para) throws BCException {
		if (para == null) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), QUERY_PARAM_EMPTY); 
		} else if (!StrUtil.empty(para.getBillNo()) && !para.getBillNo().matches("[0-9A-Za-z]{8,32}")) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_FORMAT_INVALID); 
		} else if (para.getLimit() != null && para.getLimit() > 50) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), LIMIT_FORMAT_INVALID); 
		}
	}

	public static void validateQueryRefund(BCQueryParameter para) throws BCException {
		if (para == null) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), QUERY_PARAM_EMPTY); 
		} else if (!StrUtil.empty(para.getBillNo()) && !para.getBillNo().matches("[0-9A-Za-z]{8,32}")) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), BILL_NO_FORMAT_INVALID); 
		} else if (!StrUtil.empty(para.getRefundNo()) && (!para.getRefundNo().substring(8, para.getRefundNo().length()).matches("[0-9A-Za-z]{3,24}") || 
				para.getRefundNo().substring(8, para.getRefundNo().length()).matches("000")) ) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), REFUND_NO_FORMAT_INVALID); 
		} else if (para.getLimit() != null && para.getLimit() > 50) {
			throw new BCException(RESULT_TYPE.PARAM_INVALID.ordinal(), RESULT_TYPE.PARAM_INVALID.name(), LIMIT_FORMAT_INVALID); 
		}
	}
}

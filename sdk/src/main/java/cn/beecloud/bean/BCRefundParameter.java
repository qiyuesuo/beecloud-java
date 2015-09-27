package cn.beecloud.bean;

import java.util.Map;

import cn.beecloud.BCEumeration.PAY_CHANNEL;

/**
 * 退款参数类，封装了退款所需的参数
 * @author Rui.Feng
 * @since 2015.9.24
 */
public class BCRefundParameter {
	
	/**
	 * 商户订单号， 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一	
	 * (必填)
	 */
	private String billNo;
	
	/**
	 * 商户退款单号	， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。
     * 例如：201506101035040000001	
     * (必填)
	 */
	private String refundNo;
	
	/**
	 * 退款金额， 只能为整数，单位为分，例如1	
	 * (必填)
	 */
	private Integer refundFee;
	
	/**
	 * 渠道类型， 根据不同场景选择不同的支付方式，包含：
	 * {@link PAY_CHANNEL#WX}: 微信
	 * {@link PAY_CHANNEL#ALI}: 支付宝
	 * {@link PAY_CHANNEL#UN}: 银联
	 * {@link PAY_CHANNEL#YEE}: 易宝
	 * {@link PAY_CHANNEL#JD}: 京东
	 * {@link PAY_CHANNEL#KUAIQIAN}: 快钱
	 * {@link PAY_CHANNEL#BD}: 百度
	 * (选填)
	 */
	private PAY_CHANNEL channel;
	
	/**
	 * 附加数据 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据，例如{"key1":"value1","key2":"value2",...}
	 * (选填)	
	 */
	private Map<String, Object> optional;
	
	/**
	 *  标识该笔是预退款还是直接退款
	 *  (选填)
	 */
	private Boolean needApproval;
	
	/**
	 * 构造函数，参数为发起退款的3个必填参数
	 * @param billNo {@link #billNo}
	 * @param refundNo {@link #refundNo}
	 * @param refundFee {@link #refundFee}
	 */
	public BCRefundParameter(String billNo, String refundNo, Integer refundFee) {
		this.billNo = billNo;
		this.refundNo = refundNo;
		this.refundFee = refundFee;
	}

	/**
	 * Access field {@link #channel}
	 */
	public PAY_CHANNEL getChannel() {
		return channel;
	}
	
	/**
	 * Set field {@link #channel}
	 */
	public void setChannel(PAY_CHANNEL channel) {
		this.channel = channel;
	}
	
	/**
	 * Access field {@link #optional}
	 */
	public Map<String, Object> getOptional() {
		return optional;
	}

	/**
	 * Set field {@link #optional}
	 */
	public void setOptional(Map<String, Object> optional) {
		this.optional = optional;
	}
	
	/**
	 * Access field {@link #needApproval}
	 */
	public Boolean getNeedApproval() {
		return needApproval;
	}
	
	/**
	 * Set field {@link #needApproval}
	 */
	public void setNeedApproval(Boolean needApproval) {
		this.needApproval = needApproval;
	}
	
	/**
	 * Access field {@link #billNo}
	 */
	public String getBillNo() {
		return billNo;
	}
	
	/**
	 * Access field {@link #refundNo}
	 */
	public String getRefundNo() {
		return refundNo;
	}
	
	/**
	 * Access field {@link #refundFee}
	 */
	public Integer getRefundFee() {
		return refundFee;
	}
}

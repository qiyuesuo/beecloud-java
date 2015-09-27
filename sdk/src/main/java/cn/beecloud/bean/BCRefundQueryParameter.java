package cn.beecloud.bean;

/**
 * 查询参数类，封装了比可退款查询所需的参数，继承自{@link BCQueryParameter}}
 * @author Rui.Feng
 * @since 2015.9.24
 */
public class BCRefundQueryParameter extends BCQueryParameter {
	
	/**
	 * 商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。
	 * (选填)
	 */
	private String refundNo;
	
	/**
	 * Access field {@link #refundNo}
	 */
	public String getRefundNo() {
		return refundNo;
	}
	
	/**
	 * Set field {@link #refundNo}
	 */
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
}

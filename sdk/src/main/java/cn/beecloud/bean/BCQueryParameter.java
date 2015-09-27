package cn.beecloud.bean;

import java.util.Date;

import cn.beecloud.BCEumeration.PAY_CHANNEL;

/**
 * 查询参数类，封装了比可账单查询所需的参数
 * @author Rui.Feng
 * @since 2015.9.24
 */
public class BCQueryParameter {
	
	/**
     * 渠道类型， 根据不同场景选择不同的支付方式，包含：
     * {@link PAY_CHANNEL#WX}
     * {@link PAY_CHANNEL#WX_APP}: 微信手机APP支付
	 * {@link PAY_CHANNEL#WX_JSAPI}: 微信公众号二维码支付
	 * {@link PAY_CHANNEL#ALI}
	 * {@link PAY_CHANNEL#ALI_APP}: 支付宝APP支付
	 * {@link PAY_CHANNEL#ALI_WEB}: 支付宝网页支付
	 * {@link PAY_CHANNEL#ALI_WAP}: 支付宝移动网页支付
	 * {@link PAY_CHANNEL#ALI_QRCODE}: 支付宝内嵌二维码支付
	 * {@link PAY_CHANNEL#UN}
	 * {@link PAY_CHANNEL#UN_APP}: 银联APP支付
	 * {@link PAY_CHANNEL#UN_WEB}: 银联网页支付
	 * {@link PAY_CHANNEL#JD}
	 * {@link PAY_CHANNEL#JD_WAP}: 京东移动网页支付
	 * {@link PAY_CHANNEL#JD_WEB}: 京东PC网页支付
	 * {@link PAY_CHANNEL#YEE}
	 * {@link PAY_CHANNEL#YEE_WAP}: 易宝移动网页支付
	 * {@link PAY_CHANNEL#YEE_WEB}: 易宝PC网页支付
	 * {@link PAY_CHANNEL#KUAIQIAN}
	 * {@link PAY_CHANNEL#KUAIQIAN_WAP}: 快钱移动网页支付
	 * {@link PAY_CHANNEL#KUAIQIAN_WEB}: 快钱PC网页支付
	 * {@link PAY_CHANNEL#PAYPAL}
	 * {@link PAY_CHANNEL#PAYPAL_SANDBOX}: paypal 沙箱环境订单
	 * {@link PAY_CHANNEL#PAYPAL_LIVE}: paypal 生产环境订单
	 * {@link PAY_CHANNEL#BD}
	 * {@link PAY_CHANNEL#BD_APP} 百度APP支付
	 * {@link PAY_CHANNEL#BD_WEB} 百度PC网页支付
	 * {@link PAY_CHANNEL#BD_WAP} 百度移动网页支付
	 **/
	private PAY_CHANNEL channel;
	
	/**
	 * 商户订单号， 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一
	 * (选填)
	 */
	private String billNo;
	
	/**
	 * 起始时间， Date类型
	 * (选填)
	 */
	private Date startTime;
	
	/**
	 * 结束时间， Date类型
	 * (选填)
	 */
	private Date endTime;
	
	/**
	 * 查询起始位置	 默认为0。设置为10，表示忽略满足条件的前10条数据	
	 * (选填)
	 */
	private Integer skip;
	
	/**
	 * 查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据
	 * (选填)	
	 */
	private Integer limit;
	
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
	 * Access field {@link #billNo}
	 */
	public String getBillNo() {
		return billNo;
	}
	
	/**
	 * Set field {@link #billNo}
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	/**
	 * Access field {@link #startTime}
	 */
	public Date getStartTime() {
		return startTime;
	}
	
	/**
	 * Set field {@link #startTime}
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * Access field {@link #endTime}
	 */
	public Date getEndTime() {
		return endTime;
	}
	
	/**
	 * Set field {@link #endTime}
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * Access field {@link #skip}
	 */
	public Integer getSkip() {
		return skip;
	}
	
	/**
	 * Set field {@link #skip}
	 */
	public void setSkip(Integer skip) {
		this.skip = skip;
	}
	
	/**
	 * Access field {@link #limit}
	 */
	public Integer getLimit() {
		return limit;
	}
	
	/**
	 * Set field {@link #limit}
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}

package cn.beecloud.bean;
import java.util.Map;

import cn.beecloud.BCEumeration;
import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.QR_PAY_MODE;

/**
 * 支付参数类，封装了调起比可支付系统所需的参数
 * @author Rui.Feng
 * @since 2015.9.24
 */
public class BCPayParameter {
	
	private PAY_CHANNEL channel;
	
	private Integer totalFee;
	
	private String billNo;
	
	private String title;
	
	private Map<String, Object> optional;
	
	private String returnUrl;
	
	private Integer billTimeout;
	
	private String openId;
	
	private String showUrl;
	
	private QR_PAY_MODE qrPayMode;
	
	private String cardNo;
	
	private String cardPwd;
	
	private String frqid;
	
	/**
	 * 构造函数，参数为发起支付的4个必填参数
	 * @param channel {@link #setChannel}
	 * @param totalFee {@link #setTotalFee}
	 * @param billNo {@link #setBillNo}
	 * @param title {@link #setTitle}
	 */
	public BCPayParameter(PAY_CHANNEL channel, Integer totalFee, String billNo,
			String title) {
		this.channel = channel;
		this.totalFee = totalFee;
		this.billNo = billNo;
		this.title = title;
	}
	
	/**
	 * 访问字段  {@link #channel}
	 */
	public PAY_CHANNEL getChannel() {
		return channel;
	}
	
	/**
	 * 访问字段  {@link #totalFee}
	 */
	public Integer getTotalFee() {
		return totalFee;
	}
	
	/**
	 * 访问字段 {@link #billNo}
	 */
	public String getBillNo() {
		return billNo;
	}
	
	/**
	 * 访问字段 {@link #title}
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 访问字段 {@link #optional}
	 */
	public Map<String, Object> getOptional() {
		return optional;
	}
	
	/**
	 * @param optional 附加数据， 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 * (选填)
	 */
	public void setOptional(Map<String, Object> optional) {
		this.optional = optional;
	}
	
	/**
	 * 访问字段 {@link #returnUrl}
	 */
	public String getReturnUrl() {
		return returnUrl;
	}
	
	/**
	 * @param returnUrl 同步返回页面， 支付渠道处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径，
	 * 当 channel 参数为 ALI_WEB 或 ALI_QRCODE 或 UN_WEB 或JD_WEB 或JD_WAP时为必填
	 * (选填)
	 */
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	/**
	 * 访问字段 {@link #billTimeout}
	 */
	public Integer getBillTimeout() {
		return billTimeout;
	}
	
	/**
	 * @param billTimeout 订单失效时间，单位秒，非零正整数，建议不小于30，快钱(KQ)不支持该参数
	 * (选填)
	 */
	public void setBillTimeout(Integer billTimeout) {
		this.billTimeout = billTimeout;
	}
	
	/**
	 * 访问字段 {@link #openId}
	 */
	public String getOpenId() {
		return openId;
	}
	
	/**
	 * @param openId 微信公众号支付(WX_JSAPI)必填
	 * (选填) 
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	/**
	 * 访问字段 {@link #showUrl}
	 */
	public String getShowUrl() {
		return showUrl;
	}
	
	/**
	 * @param showUrl 商品展示地址，需以http://开头的完整路径，例如：http://www.商户网址.com/myorder，（ALI_WEB)的选填参数
	 * (选填)
	 */
	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}
	
	/**
	 * 访问字段 {@link #qrPayMode}
	 */
	public QR_PAY_MODE getQrPayMode() {
		return qrPayMode;
	}
	
	/**
	 * @param qrPayMode 二维码类型，(ALI_QRCODE)的必填参数，二维码类型含义，
	 * {@link QR_PAY_MODE#MODE_BRIEF_FRONT}： 订单码-简约前置模式, 对应 iframe 宽度不能小于 600px, 高度不能小于 300px
 	 * {@link QR_PAY_MODE#MODE_FRONT}： 订单码-前置模式, 对应 iframe 宽度不能小于 300px, 高度不能小于 600px
 	 * {@link QR_PAY_MODE#MODE_MINI_FRONT}, 对应 iframe 宽度不能小于 75px, 高度不能小于 75px 
 	 * (选填)
	 */
	public void setQrPayMode(QR_PAY_MODE qrPayMode) {
		this.qrPayMode = qrPayMode;
	}
	
	/**
	 * 访问字段 {@link #cardNo}
	 */
	public String getCardNo() {
		return cardNo;
	}
	
	/**
	 * @param cardNo 点卡卡号，(YEE_NOBANKCARD)的必填参数，每种卡的要求不一样，例如易宝支持的QQ币卡号是9位的，江苏省内部的QQ币卡号是15位，易宝不支付
	 * (选填)
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	/**
	 * 访问字段 {@link #cardPwd}
	 */
	public String getCardPwd() {
		return cardPwd;
	}
	
	/**
	 * @param cardPwd 点卡密码，简称卡密， (YEE_NOBANKCARD)的必填参数
	 * (选填)
	 */
	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}
	
	/**
	 * 访问字段 {@link #frqid}
	 */
	public String getFrqid() {
		return frqid;
	}
	
	/**
	 * @param frqid 点卡类型编码，(YEE_NOBANKCARD)的必填参数，包含：
	 * 骏网一卡通(JUNNET)
	 * 盛大卡(SNDACARD)
	 * 神州行(SZX)
	 * 征途卡(ZHENGTU)
	 * Q币卡(QQCARD)
	 * 联通卡(UNICOM)
	 * 久游卡(JIUYOU)
	 * 易充卡(YICHONGCARD)
	 * 网易卡(NETEASE)
	 * 完美卡(WANMEI)
	 * 搜狐卡(SOHU)
	 * 电信卡(TELECOM)
	 * 纵游一卡通(ZONGYOU)
	 * 天下一卡通(TIANXIA)
	 * 天宏一卡通(TIANHONG)
	 * 一卡通(THIRTYTWOCARD)
	 */
	public void setFrqid(String frqid) {
		this.frqid = frqid;
	}
	
	/**
	 * @param channel 渠道类型， 根据不同场景选择不同的支付方式，包含：
	 * {@link PAY_CHANNEL#WX_NATIVE}: 微信公众号二维码支付
	 * {@link PAY_CHANNEL#WX_JSAPI}: 微信公众号支付
	 * {@link PAY_CHANNEL#ALI_WEB}: 支付宝网页支付
	 * {@link PAY_CHANNEL#ALI_QRCODE}: 支付宝内嵌二维码支付
	 * {@link PAY_CHANNEL#ALI_WAP}: 支付宝移动网页支付
	 * {@link PAY_CHANNEL#UN_WEB}: 银联网页支付
	 * {@link PAY_CHANNEL#JD_WAP}: 京东移动网页支付
	 * {@link PAY_CHANNEL#JD_WEB}: 京东PC网页支付
	 * {@link PAY_CHANNEL#YEE_WAP}: 易宝移动网页支付
	 * {@link PAY_CHANNEL#YEE_WEB}: 易宝PC网页支付
	 * {@link PAY_CHANNEL#YEE_NOBANKCARD}: 易宝点卡支付
	 * {@link PAY_CHANNEL#KUAIQIAN_WAP}: 快钱移动网页支付
	 * {@link PAY_CHANNEL#KUAIQIAN_WEB}: 快钱PC网页支付
	 * {@link PAY_CHANNEL#BD_WEB}}}: 百度PC网页支付
	 * {@link PAY_CHANNEL#BD_WAP}: 百度移动网页支付
	 * (必填)
	 */
	public void setChannel(PAY_CHANNEL channel) {
		this.channel = channel;
	}
	
	/**
	 * @param totalFee 订单总金额， 只能为整数，单位为分，例如 1	
	 * (必填)
	 */
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	
	/**
	 * @param billNo 商户订单号, 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一, 例如（201506101035040000001）
	 * (必填)
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	/**
	 * @param title 订单标题， 32个字节内，最长支持16个汉字	
	 * (必填)
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}

package cn.beecloud.bean;

import cn.beecloud.BCEumeration.TRANSFER_CHANNEL;


/**
 * BeeCloud单笔打款参数类
 * 
 * @author Rui.Feng
 * @since 2015.11.25
 */
public class TransferParameter {

    private TRANSFER_CHANNEL channel;

    private String transferNo;

    private Integer totalFee;

    private String description;

    private String channelUserId;

    private String channelUserName;

    private RedpackInfo redpackInfo;

    private String accountName;

    /**
     * 访问字段 {@link #channel}
     */
    public TRANSFER_CHANNEL getChannel() {
        return channel;
    }

    /**
     * @param channel
     * （必填）渠道类型，根据不同场景选择不同的支付方式，包含： {@link TRANSFER_CHANNEL#ALI_TRANSFER}:
     * 支付宝企业打款 {@link TRANSFER_CHANNEL#WX_REDPACK}: 微信红包
     * {@link TRANSFER_CHANNEL#WX_TRANSFER}: 微信企业打款
     */
    public void setChannel(TRANSFER_CHANNEL channel) {
        this.channel = channel;
    }

    /**
     * 访问字段 {@link #transferNo}
     */
    public String getTransferNo() {
        return transferNo;
    }

    /**
     * @param transferNo
     * （必填）打款单号，支付宝为11-32位数字字母组合， 微信为10位数字
     */
    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo;
    }

    /**
     * 访问字段 {@link #totalFee}
     */
    public Integer getTotalFee() {
        return totalFee;
    }

    /**
     * @param totalFee
     * （必填）打款金额，此次打款的金额,单位分,正整数(微信红包1.00-200元，微信打款>=1元)
     */
    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 访问字段 {@link #description}
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     * （必填）打款说明，此次打款的说明
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 访问字段 {@link #channelUserId}
     */
    public String getChannelUserId() {
        return channelUserId;
    }

    /**
     * @param channelUserId
     * （必填）用户id，支付渠道方内收款人的标示, 微信为openid, 支付宝为支付宝账户
     */
    public void setChannelUserId(String channelUserId) {
        this.channelUserId = channelUserId;
    }

    /**
     * 访问字段 {@link #channelUserName}
     */
    public String getChannelUserName() {
        return channelUserName;
    }

    /**
     * @param channelUserName
     * （选填）用户名，支付渠道内收款人账户名， 支付宝必填
     */
    public void setChannelUserName(String channelUserName) {
        this.channelUserName = channelUserName;
    }

    /**
     * 访问字段 {@link #redpackInfo}
     */
    public RedpackInfo getRedpackInfo() {
        return redpackInfo;
    }

    /**
     * @param redpackInfo
     * （选填）红包信息，微信红包的详细描述，微信红包必填
     */
    public void setRedpackInfo(RedpackInfo redpackInfo) {
        this.redpackInfo = redpackInfo;
    }

    /**
     * 访问字段 {@link #redpackInfo}
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName
     * （选填）打款方账号名称，打款方账号名全称，支付宝必填，例如：苏州比可网络科技有限公司
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}

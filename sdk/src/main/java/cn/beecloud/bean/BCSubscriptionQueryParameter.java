package cn.beecloud.bean;

import java.util.Date;

/**
 * 订阅查询参数类，封装了BeeCloud订阅信息查询参数信息
 *
 * @author Rui.Feng
 * @since 2016.7.26
 */
public class BCSubscriptionQueryParameter {

    private Date startTime;

    private Date endTime;

    private Integer skip;

    private Integer limit;

    private Boolean countOnly = false;

    private String buyerId;

    private String planId;

    private String cardId;

    /**
     * 访问字段 {@link #startTime}
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     * 起始时间， Date类型 (选填)
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 访问字段 {@link #endTime}
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     * 结束时间， Date类型 (选填)
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 访问字段 {@link #skip}
     */
    public Integer getSkip() {
        return skip;
    }

    /**
     * @param skip
     * 查询起始位置 默认为0。设置为10，表示忽略满足条件的前10条数据 (选填)
     */
    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    /**
     * 访问字段 {@link #limit}
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @param limit
     * 查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据 (选填)
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * 访问字段 {@link #buyerId}
     */
    public String getBuyerId() {
        return buyerId;
    }

    /**
     * @param buyerId
     * 订阅的buyer ID，可以是用户email，也可以是商户系统中的用户ID，（选填）
     */
    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    /**
     * 访问字段 {@link #planId}
     */
    public String getPlanId() {
        return planId;
    }

    /**
     * @param planId
     * 对应的计划id，（选填）
     */
    public void setPlanId(String planId) {
        this.planId = planId;
    }

    /**
     * 访问字段 {@link #cardId}
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * @param cardId
     * 第一次订阅成功后webhook返回，（选填）
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * 访问字段 {@link #countOnly}
     */
    public Boolean getCountOnly() {
        return countOnly;
    }

    /**
     * @param countOnly
     * 仅返回满足条件的subscription的集合数量，默认为false, 如果传入值为true,
     * 则返回满足查询条件的数量，否则返回BCSubscription集合数量，（选填）
     */
    public void setCountOnly(Boolean countOnly) {
        this.countOnly = countOnly;
    }
}

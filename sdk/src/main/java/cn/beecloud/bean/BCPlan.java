package cn.beecloud.bean;

import cn.beecloud.BCEumeration.BC_PLAN_INTERVAL;

import java.util.Map;

/**
 * 订阅计划类，封装了BeeCloud订阅计划信息
 *
 * @author Rui.Feng
 * @since 2016.7.26
 */
public class BCPlan extends BCObject{

    private Integer intervalCount;

    private Integer fee;

    private String name;

    private BC_PLAN_INTERVAL interval;

    private String currency;

    private Integer trailDays;

    private Map<String, Object> optional;

    private Boolean valid = false;

    private String optionalString;

    /**
     * 访问字段 {@link #intervalCount}
     */
    public Integer getIntervalCount() {
        return intervalCount;
    }

    /**
     * 设置字段 {@link #intervalCount}
     */
    public void setIntervalCount(Integer intervalCount) {
        this.intervalCount = intervalCount;
    }

    /**
     * 访问字段 {@link #fee}
     */
    public Integer getFee() {
        return fee;
    }

    /**
     * 设置字段 {@link #fee}
     */
    public void setFee(Integer fee) {
        this.fee = fee;
    }

    /**
     * 访问字段 {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * 设置字段 {@link #name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 访问字段 {@link #interval}
     */
    public BC_PLAN_INTERVAL getInterval() {
        return interval;
    }

    /**
     * 设置字段 {@link #interval}
     */
    public void setInterval(BC_PLAN_INTERVAL interval) {
        this.interval = interval;
    }

    /**
     * 访问字段 {@link #currency}
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置字段 {@link #currency}
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 访问字段 {@link #trailDays}
     */
    public Integer getTrailDays() {
        return trailDays;
    }

    /**
     * 设置字段 {@link #trailDays}
     */
    public void setTrailDays(Integer trailDays) {
        this.trailDays = trailDays;
    }

    /**
     * 访问字段 {@link #optional}
     */
    public Map<String, Object> getOptional() {
        return optional;
    }

    /**
     * 设置字段 {@link #optional}
     */
    public void setOptional(Map<String, Object> optional) {
        this.optional = optional;
    }

    /**
     * 访问字段 {@link #optionalString}
     */
    public String getOptionalString() {
        return optionalString;
    }

    /**
     * 设置字段 {@link #optionalString}
     */
    public void setOptionalString(String optionalString) {
        this.optionalString = optionalString;
    }

    /**
     * 访问字段 {@link #valid}
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * 设置字段 {@link #valid}
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}

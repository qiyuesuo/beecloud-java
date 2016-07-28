package cn.beecloud.bean;

import java.util.Date;
import java.util.Map;

/**
 * Created by rui on 16/7/26.
 */
public class BCPlan extends BCObject{

    private Integer intervalCount;

    private Integer fee;

    private String name;

    private String interval;

    private String currency;

    private Integer trailDays;

    private Map<String, Object> optional;

    private String optionalString;

    public Integer getIntervalCount() {
        return intervalCount;
    }

    public void setIntervalCount(Integer intervalCount) {
        this.intervalCount = intervalCount;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getTrailDays() {
        return trailDays;
    }

    public void setTrailDays(Integer trailDays) {
        this.trailDays = trailDays;
    }

    public Map<String, Object> getOptional() {
        return optional;
    }

    public void setOptional(Map<String, Object> optional) {
        this.optional = optional;
    }

    public String getOptionalString() {
        return optionalString;
    }

    public void setOptionalString(String optionalString) {
        this.optionalString = optionalString;
    }
}

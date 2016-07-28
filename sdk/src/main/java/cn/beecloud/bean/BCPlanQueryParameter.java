package cn.beecloud.bean;

import java.util.Date;

/**
 * Created by rui on 16/7/26.
 */
public class BCPlanQueryParameter {

    private Date startTime;

    private Date endTime;

    private Integer skip;

    private Integer limit;

    private Boolean countOnly = false;

    private String nameWithSubstring;

    private String interval;

    private Integer intervalCount;

    private Integer trialDays;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Boolean getCountOnly() {
        return countOnly;
    }

    public void setCountOnly(Boolean countOnly) {
        this.countOnly = countOnly;
    }

    public String getNameWithSubstring() {
        return nameWithSubstring;
    }

    public void setNameWithSubstring(String nameWithSubstring) {
        this.nameWithSubstring = nameWithSubstring;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Integer getIntervalCount() {
        return intervalCount;
    }

    public void setIntervalCount(Integer intervalCount) {
        this.intervalCount = intervalCount;
    }

    public Integer getTrialDays() {
        return trialDays;
    }

    public void setTrialDays(Integer trialDays) {
        this.trialDays = trialDays;
    }
}

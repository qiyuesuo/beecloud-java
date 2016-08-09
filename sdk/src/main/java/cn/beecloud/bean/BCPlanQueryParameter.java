package cn.beecloud.bean;

import cn.beecloud.BCEumeration;
import cn.beecloud.BCEumeration.BC_PLAN_INTERVAL;


import java.util.Date;

/**
 * 订阅计划查询参数类，封装了BeeCloud订阅计划查询所需的参数
 *
 * @author Rui.Feng
 * @since 2016.8.2
 */
public class BCPlanQueryParameter {

    private Date startTime;

    private Date endTime;

    private Integer skip;

    private Integer limit;

    private Boolean countOnly = false;

    private String name;

    private String nameWithSubstring;

    private BCEumeration.BC_PLAN_INTERVAL interval;

    private Integer intervalCount;

    private Integer trialDays;

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
     * 访问字段 {@link #countOnly}
     */
    public Boolean getCountOnly() {
        return countOnly;
    }

    /**
     * @param countOnly
     * 仅返回满足条件的Plan的集合数量，默认为false, 如果传入值为true, 则返回满足查询条件的数量，否则返回BCPlan集合数量 (选填)
     */
    public void setCountOnly(Boolean countOnly) {
        this.countOnly = countOnly;
    }

    /**
     * 访问字段 {@link #nameWithSubstring}
     */
    public String getNameWithSubstring() {
        return nameWithSubstring;
    }

    /**
     * @param nameWithSubstring
     * 根据name的子字符串查询 (选填)
     */
    public void setNameWithSubstring(String nameWithSubstring) {
        this.nameWithSubstring = nameWithSubstring;
    }

    /**
     * 访问字段 {@link #interval}
     */
    public BC_PLAN_INTERVAL getInterval() {
        return interval;
    }

    /**
     * @param interval
     * 收费周期单位, 只能是day、week、month、year (选填)
     */
    public void setInterval(BC_PLAN_INTERVAL interval) {
        this.interval = interval;
    }

    /**
     * 访问字段 {@link #intervalCount}
     */
    public Integer getIntervalCount() {
        return intervalCount;
    }

    /**
     * @param intervalCount
     * 和interval共同定义收费周期，例如interval=month interval_count=3，那么每3个月收费一次，最大的收费间隔为1年(1 year, 12 months, or 52 weeks) (选填)
     */
    public void setIntervalCount(Integer intervalCount) {
        this.intervalCount = intervalCount;
    }

    /**
     * 访问字段 {@link #trialDays}
     */
    public Integer getTrialDays() {
        return trialDays;
    }

    /**
     * @param trialDays
     * 试用天数 (选填)
     */
    public void setTrialDays(Integer trialDays) {
        this.trialDays = trialDays;
    }

    /**
     * 访问字段 {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     * 计划名 (选填)
     */
    public void setName(String name) {
        this.name = name;
    }
}

package cn.beecloud.bean;

/**
 * Created by sunqifs on 8/16/17.
 */
public class BCQueryCouponParam {
    private String userId;
    private String templateId;
    private Integer status;
    private Integer limitFee;
    private Long createdBefore;
    private Long createdAfter;
    private Long skip;
    private Long limit;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLimitFee() {
        return limitFee;
    }

    public void setLimitFee(Integer limitFee) {
        this.limitFee = limitFee;
    }

    public Long getCreatedBefore() {
        return createdBefore;
    }

    public void setCreatedBefore(Long createdBefore) {
        this.createdBefore = createdBefore;
    }

    public Long getCreatedAfter() {
        return createdAfter;
    }

    public void setCreatedAfter(Long createdAfter) {
        this.createdAfter = createdAfter;
    }

    public Long getSkip() {
        return skip;
    }

    public void setSkip(Long skip) {
        this.skip = skip;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}

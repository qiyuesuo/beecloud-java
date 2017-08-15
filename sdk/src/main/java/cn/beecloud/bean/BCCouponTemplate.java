package cn.beecloud.bean;

/**
 * Created by sunqifs on 8/15/17.
 */
public class BCCouponTemplate {

    private String id;
    private String name;
    private Integer type;
    private String code;
    private Integer limitFee;
    private Float discount;
    private Integer totalCount;
    private Integer maxCountPerUser;
    private Integer deliverCount;
    private Integer useCount;
    private Integer expiryType;
    private Long startTime;
    private Long endTime;
    private Integer deliveryValidDays;
    private Integer status;
    private String mchAccount;
    private String appId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLimitFee() {
        return limitFee;
    }

    public void setLimitFee(Integer limitFee) {
        this.limitFee = limitFee;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getMaxCountPerUser() {
        return maxCountPerUser;
    }

    public void setMaxCountPerUser(Integer maxCountPerUser) {
        this.maxCountPerUser = maxCountPerUser;
    }

    public Integer getDeliverCount() {
        return deliverCount;
    }

    public void setDeliverCount(Integer deliverCount) {
        this.deliverCount = deliverCount;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public Integer getExpiryType() {
        return expiryType;
    }

    public void setExpiryType(Integer expiryType) {
        this.expiryType = expiryType;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getDeliveryValidDays() {
        return deliveryValidDays;
    }

    public void setDeliveryValidDays(Integer deliveryValidDays) {
        this.deliveryValidDays = deliveryValidDays;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMchAccount() {
        return mchAccount;
    }

    public void setMchAccount(String mchAccount) {
        this.mchAccount = mchAccount;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}

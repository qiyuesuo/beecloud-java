package cn.beecloud.bean;

/**
 * 用户类
 *
 * @author sy
 * @since 2017.6.22
 */
public class BCUser {
    //该账户下任意app_id，用于验签
    private String appId;

    private String timeStamp;

    private String appSign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAppSign() {
        return appSign;
    }

    public void setAppSign(String appSign) {
        this.appSign = appSign;
    }
}

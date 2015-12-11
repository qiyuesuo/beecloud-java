package cn.beecloud.bean;

/**
 * 微信红包打款红包信息类
 * 
 * @author Rui.Feng
 * @since 2015.11.24
 */
public class RedpackInfo {

    private String sendName;

    private String wishing;

    private String activityName;

    /**
     * 访问字段 {@link #sendName}
     */
    public String getSendName() {
        return sendName;
    }

    /**
     * @param sendName
     * （必填）红包发送者名称 32位
     */
    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    /**
     * 访问字段 {@link #wishing}
     */
    public String getWishing() {
        return wishing;
    }

    /**
     * @param wishing
     * （必填）红包祝福语 128 位
     */
    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    /**
     * 访问字段 {@link #activityName}
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * @param activityName
     * （必填）红包活动名称 32位
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}

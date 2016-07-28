package cn.beecloud.bean;

/**
 * Created by rui on 16/7/26.
 */
public class BCSubscriptionSMSResult {

    private String smsId;

    private String code;

    /**
     * @return 验证码接口返回id
     */
    public String getSmsId() {
        return smsId;
    }

    /**
     * 设置字段 {@link #smsId}
     */
    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    /**
     * @return 返回验证码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置字段 {@link #code}
     */
    public void setCode(String code) {
        this.code = code;
    }
}

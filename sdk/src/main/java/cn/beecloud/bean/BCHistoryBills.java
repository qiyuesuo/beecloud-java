package cn.beecloud.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by sy on 2017/6/23.
 */
public class BCHistoryBills {
    private String appId;

    private String timeStamp;

    private String appSign;

    private Map<String, List<String>> billInfo;

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

    public Map<String, List<String>> getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(Map<String, List<String>> billInfo) {
        this.billInfo = billInfo;
    }
}

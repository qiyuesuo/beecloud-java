package cn.beecloud.bean;

/**
 * 商户用户批量查询类
 *
 * @author sy
 * @since 2017.6.22
 */
public class BCUsersQuery extends BCUser{
    private String email;

    private String buyerType;

    private String startTime;

    private String endTime;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

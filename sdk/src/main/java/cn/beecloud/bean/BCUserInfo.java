package cn.beecloud.bean;

/**
 * 用户类
 *
 * @author sy
 * @since 2017.6.22
 */
public class BCUserInfo extends BCUser{

    //商户为自己的用户分配的ID。可以是email、手机号、随机字符串等。最长32位。在商户自己系统内必须保证唯一。
    private String buyerId;

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

}

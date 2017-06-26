package cn.beecloud.bean;

import java.util.List;

/**
 * 批量导入用户类
 *
 * @author sy
 * @since 2017.6.22
 */
public class BCUsers extends BCUser{
    //用户账号
    private String email;

    //商户为自己的多个用户分配的IDs。每个ID可以是email、手机号、随机字符串等；最长32位；在商户自己系统内必须保证唯一。
    private List buyerIds;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List getBuyerIds() {
        return buyerIds;
    }

    public void setBuyerIds(List buyerIds) {
        this.buyerIds = buyerIds;
    }
}

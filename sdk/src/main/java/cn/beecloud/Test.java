package cn.beecloud;

import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCOrder;
import cn.beecloud.bean.BCQueryParameter;

import java.util.List;

/**
 * Created by rui on 16/7/5.
 */
public class Test {
    public static void main(String[] args) {
        BeeCloud.registerApp("e66e760b-0f78-44bb-a9ae-b22729d51678", "ca1900cf2-2570-49a3-bfb8-c6e7a1bc1e21", "6fb7db77-96ed-46ef-ae10-1118ee564dd3", "97ca13e4-6f40-4790-9734-ddcdc1da21db");
        BCOrder order = new BCOrder(BCEumeration.PAY_CHANNEL.ALI_WEB, 1, "1122334455aaabbcc", "test");
        order.setReturnUrl("baidu.com");
        try {
            BCQueryParameter query = new BCQueryParameter();
            query.setChannel(BCEumeration.PAY_CHANNEL.ALI);
            List<BCOrder> orders = BCPay.startQueryBill(query);
            System.out.println(orders);
        } catch (BCException e) {
            e.printStackTrace();
        }
    }
}

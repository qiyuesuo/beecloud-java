package cn.beecloud.bean;

import cn.beecloud.BCEumeration;
import cn.beecloud.BCPay;
import cn.beecloud.BeeCloud;

/**
 * Created by rui on 16/7/4.
 */
public class Test {
    public static void main(String[] args) {
        BeeCloud.registerApp("e66e760b-0f78-44bb-a9ae-b22729d51678", null, "6fb7db77-96ed-46ef-ae10-1118ee564dd3", "97ca13e4-6f40-4790-9734-ddcdc1da21db");
        BCOrder order = new BCOrder(BCEumeration.PAY_CHANNEL.ALI_WEB, 1, "1112233ffggggggh", "test");
        order.setReturnUrl("baidu.com");
        try {
            BCPay.startBCPay(order);
        } catch (BCException e) {
            e.printStackTrace();
        }

    }

}

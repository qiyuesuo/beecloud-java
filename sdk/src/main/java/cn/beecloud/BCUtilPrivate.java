package cn.beecloud;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * User: rui.feng Date: 15/07/09
 */
class BCUtilPrivate {

    static final String kApiVersion = "2";

    static String getAppSignature(String timeStamp) {
        String str = BCCache.getAppID() + timeStamp + BCCache.getAppSecret();
        return getMessageDigest(str);
    }

    static String getAppSignature() {
        String str = BCCache.getAppID() + BCCache.getAppSecret();
        return getMessageDigest(str);
    }

    static String getAppSignatureWithMasterSecret(String timeStamp) {
        String str = BCCache.getAppID() + timeStamp + BCCache.getMasterKey();
        return getMessageDigest(str);
    }

    static String getAppSignatureWithTestSecret(String timeStamp) {
        String str = BCCache.getAppID() + timeStamp + BCCache.getTestSecret();
        return getMessageDigest(str);
    }

    static String getMessageDigest(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f' };
        try {
            byte[] buffer = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");

            // 使用指定的字节更新摘要
            mdTemp.update(buffer);

            // 获得密文
            byte[] md = mdTemp.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    static String getkApiBCTransfer() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/bc_transfer";
    }

    static String getkApiBCT0Transfer() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/gateway/bc_transfer";
    }

    static String getkApiBCT1Transfer() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/t1express/transfer";
    }

    static String getkApiPay() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/bill";
    }

    static String getkApiBillConfirm() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/bill/confirm";
    }

    static String getkApiOfflinePay() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/offline/bill";
    }

    static String getkApiAuth() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/auth";
    }

    static String getkApiCardSign() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/card/sign";
    }

    static String getkApiSyncCardVerify() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/card/sync_verify";
    }

    static String getkApiSyncCardSign() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/card/sync_sign";
    }

    static String getkApiRefund() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/refund";
    }

    static String getkApiQueryBill() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/bills?para=";
    }

    static String getkApiQueryBillById() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/bill";
    }

    static String getkApiQueryRefund() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/refunds?para=";
    }

    static String getkApiQueryRefundById() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/refund";
    }

    static String getkApiRefundUpdate() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/refund/status?para=";
    }

    static String getkApiTransfer() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/transfer";
    }

    static String getkApiTransfers() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/transfers";
    }

    static String getkApiQueryBillCount() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/bills/count?para=";
    }

    static String getkApiQueryRefundCount() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/refunds/count?para=";
    }

    static String getApiBatchRefund() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/refund";
    }

    static String getApiInternationalPay() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/international/bill";
    }
    /* 沙箱部分api */

    static String getkSandboxApiPay() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/sandbox/bill";
    }

    static String getkApiSandboxQueryBill() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/sandbox/bills?para=";
    }

    static String getkApiSandboxQueryBillById() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/sandbox/bill";
    }

    static String getkApiSandboxQueryBillCount() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/sandbox/bills/count?para=";
    }

    static String getkApiSandboxNotify() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/sandbox/notify";
    }

    static String getkApiBCTransferBanks() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/bc_transfer/banks?para=";
    }

    static String transferDateFromLongToString(long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    static String getkApiSendSMS() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/sms";
    }

    static String getkApiSubscription() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/subscription";
    }

    static String getkApiQueryPlan() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/plan?";
    }

    static String getkApiQuerySubscription() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/subscription?";
    }

    static String getkApiSubscriptionBanks() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/subscription_banks?";
    }

    public static String getApiOfflineStatusUrl() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/offline/bill/status";
    }

    public static String getGateWayBankListUrl() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/bc_gateway/banks?para=";
    }

    public static String getTransferBankListUrl() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/bc_user_transfer/banks?para=";
    }

    static String getUserApiBCTransfer() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/bc_user_transfer";
    }

    static String getApiUser() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/user";
    }

    static String getApiUsers() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/users";
    }

    static String getApiHistoryBills() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/history_bills";
    }

    static String getApiQueryTransfer() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/transfers?para=";
    }

    static String getApiQueryTransferCount() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/transfers/count?para=";
    }

    static String getkApiQueryCouponTemplate() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/coupon/template";
    }

    static String getkApiCoupon() {
        return BCCache.apiHostArray[(int) (Math.random() * 4)] + "/" + BCUtilPrivate.kApiVersion
                + "/rest/coupon";
    }

    public static String masterSign(String s) {
        String toSign = s + BCCache.getMasterKey();
        return getMessageDigest(toSign);
    }
}

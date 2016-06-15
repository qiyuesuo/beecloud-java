package cn.beecloud;

/**
 * BeeCloud 初始化类
 * 
 * @author Ray
 * @Date: 15/7/11
 */
public class BeeCloud {

    /**
     * BeeCloud JAVA SDK release version.
     */
    public static final String kBeeCloudVersionString = "3.1.6";
    public static final double kBeeCloudVersionNumber = 3.16;

    /**
     * 设置appID、appSecret、masterSecret，在程序开始时运行一遍即可
     * 
     * @param appID
     * （必填）商户在Beecloud后台注册的app id
     * @param appSecret
     * （必填）用于支付、查询
     * @param masterSecret
     * （必填）用于退款、批量打款
     */
    public static void registerApp(String appID, String testSecret, String appSecret,
            String masterSecret) {

        BCCache.setAppID(appID);
        BCCache.setAppSecret(appSecret);
        BCCache.setMasterKey(masterSecret);
        BCCache.setTestSecret(testSecret);
        BCAPIClient.initClient();
    }

    /**
     * 设置网络超时时间, 单位ms, 默认5000
     *
     * @param timeout
     * 访问BeeCloud的超时时间
     */
    public static void setNetworkTimeout(int timeout) {
        BCCache.setNetworkTimeout(timeout);
    }

    public static void setSandbox(boolean sandbox) {
        BCCache.setSandbox(sandbox);
    }
}

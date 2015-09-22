package cn.beecloud;

public class BeeCloud {
    /**
     * BeeCloud SDK release version.
     */
    public static final String kBeeCloudVersionString = "2.3.0";
    public static final double kBeeCloudVersionNumber = 2.30;

    /**
     * 设置appID和appSecret，在程序开始时运行一遍即可
     *
     * @param appID
     * @param appSecret
     */
    public static void registerApp(String appID, String appSecret) {
    	
        BCCache.setAppID(appID);
        BCCache.setAppSecret(appSecret);
        BCAPIClient.initClient();
    }

    /**
     * 设置masterkey, 后续ACL需求预留
     *
     * @param masterKey
     */
    public static void setMasterKey(String masterKey) {
        BCCache.setMasterKey(masterKey);
    }

    /**
     * 设置网络超时时间, 单位ms, 默认5000
     *
     * @param timeout
     */
    public static void setNetworkTimeout(int timeout) {
        BCCache.setNetworkTimeout(timeout);
    }

    /**
     * 设置是否需要本地缓存云端数据及元数据, 默认为true
     *
     * @param needLocalCache
     */
    public static void setNeedCache(boolean needLocalCache) {
        BCCache.setNeedLocalCache(needLocalCache);
    }

    /**
     * 清除所有本地缓存
     */
    public static void clearAllCache() {
    }
}

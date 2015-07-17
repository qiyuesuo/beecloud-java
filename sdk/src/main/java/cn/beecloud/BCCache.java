package cn.beecloud;


/**
 * @author Ray
 * Date: 15/7/08
 */
public class BCCache {

    private static String appID = null;
    private static String appSecret = null;
    private static String masterKey = null;
    private static boolean needLocalCache = true;
    private static int networkTimeout = 500000;
    public static String[] apiHostArray = initApiHostArray();

    static String getAppSecret() {
        return appSecret;
    }

    static void setAppSecret(String appSecret) {
        BCCache.appSecret = appSecret;
    }

    static String getAppID() {
        return appID;
    }

    static void setAppID(String appID) {
        BCCache.appID = appID;
    }

    static String getMasterKey() {
        return masterKey;
    }

    static void setMasterKey(String masterKey) {
        BCCache.masterKey = masterKey;
    }

    static boolean isNeedLocalCache() {
        return needLocalCache;
    }

    static void setNeedLocalCache(boolean needLocalCache) {
        BCCache.needLocalCache = needLocalCache;
    }

    static int getNetworkTimeout() {
        return networkTimeout;
    }

    static void setNetworkTimeout(int networkTimeout) {
        BCCache.networkTimeout = networkTimeout;
    }
    
    static String[] initApiHostArray()
    {
    	apiHostArray = new String[4];
    	apiHostArray[0] = "https://apibj.beecloud.cn";
    	apiHostArray[1] = "https://apisz.beecloud.cn";
    	apiHostArray[2] = "https://apiqd.beecloud.cn";
    	apiHostArray[3] = "https://apihz.beecloud.cn";
    	
    	return apiHostArray;
    }
}

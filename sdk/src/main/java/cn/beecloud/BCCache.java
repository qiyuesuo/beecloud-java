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


    static void setAppSecret(String appSecret) {
        BCCache.appSecret = appSecret;
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
    	apiHostArray[0] = "http://192.168.0.5:8080";
    	apiHostArray[1] = "http://192.168.0.5:8080";
    	apiHostArray[2] = "http://192.168.0.5:8080";
    	apiHostArray[3] = "http://192.168.0.5:8080";
    	
    	return apiHostArray;
    }
    
    public static String getAppSecret() {
        return appSecret;
    }
    
    public static String getAppID() {
        return appID;
    }
}

package cn.beecloud;

public class BeeCloud {
    /**
     * BeeCloud SDK release version.
     */
    public static final String kBeeCloudVersionString = "1.0.1";
    public static final double kBeeCloudVersionNumber = 1.01;

    /**
     * ����appID��appSecret���ڳ���ʼʱ����һ�鼴��
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
     * ����masterkey, ����ACL����Ԥ��
     *
     * @param masterKey
     */
    public static void setMasterKey(String masterKey) {
        BCCache.setMasterKey(masterKey);
    }

    /**
     * �������糬ʱʱ��, ��λms, Ĭ��5000
     *
     * @param timeout
     */
    public static void setNetworkTimeout(int timeout) {
        BCCache.setNetworkTimeout(timeout);
    }

    /**
     * �����Ƿ���Ҫ���ػ����ƶ����ݼ�Ԫ����, Ĭ��Ϊtrue
     *
     * @param needLocalCache
     */
    public static void setNeedCache(boolean needLocalCache) {
        BCCache.setNeedLocalCache(needLocalCache);
    }

    /**
     * ������б��ػ���
     */
    public static void clearAllCache() {
    	
    }
}

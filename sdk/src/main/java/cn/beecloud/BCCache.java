package cn.beecloud;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * BeeCloud JAVA SDK缓存类
 * 
 * @author Ray
 * Date: 15/7/08
 */
public class BCCache {
	
    private static String appID = null;
    private static String appSecret = null;
    private static String testSecret = null;
    private static String masterKey = null;
    private static boolean sandbox = false;
    private static int networkTimeout = 500000;
    static String[] apiHostArray = initApiHostArray();

    /**
     * 缓存 appSecret
     */
    static void setAppSecret(String appSecret) {
        BCCache.appSecret = appSecret;
    }
    
    /**
     * 缓存 appID
     */
    static void setAppID(String appID) {
        BCCache.appID = appID;
    }
    
    /**
     * 缓存 masterKey
     */
    static void setMasterKey(String masterKey) {
        BCCache.masterKey = masterKey;
    }

    static void setSandbox(boolean sandbox) {
        BCCache.sandbox = sandbox;
    }

    static void setTestSecret(String testSecret) {
        BCCache.testSecret = testSecret;
    }

    static boolean isSandbox() {
        return sandbox;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    public static String getTestSecret() {
        return testSecret;
    }

    public static String getAppID() {
        return appID;
    }
    
    static String getMasterKey() {
        return masterKey;
    }

    static int getNetworkTimeout() {
        return networkTimeout;
    }
    
    /**
     * 缓存 networkTimeout
     */
    static void setNetworkTimeout(int networkTimeout) {
        BCCache.networkTimeout = networkTimeout;
    }
    
    /**
     * 初始化rest api服务器
     */
    static String[] initApiHostArray()
    {
    	apiHostArray = new String[4];
    	
    	apiHostArray[0] = "https://apidynamic.beecloud.cn";
    	apiHostArray[1] = "https://apidynamic.beecloud.cn";
    	apiHostArray[2] = "https://apidynamic.beecloud.cn";
    	apiHostArray[3] = "https://apidynamic.beecloud.cn";
    	InputStream inputStream;
    	/*
    	 * 如果类路径下存在配置文件conf.properties，读取其中的backend属性，作为后端连接服务器
    	 */
    	inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties");
    	if (inputStream != null) {
    		Properties prop = new Properties();
    		try {
				prop.load(inputStream);
				String host = prop.getProperty("backend");
				if (!host.trim().equals("")) {
					apiHostArray[0] = "http://" + host + ":8080";
					apiHostArray[1] = "http://" + host + ":8080";
					apiHostArray[2] = "http://" + host + ":8080";
					apiHostArray[3] = "http://" + host + ":8080";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return apiHostArray;
    }
}

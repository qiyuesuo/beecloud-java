package cn.beecloud;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: rui.feng
 * Date: 15/07/09
 */
class BCUtilPrivate {

    static final String kApiStatus = "/status";
    static final String kApiVersion = "1";
    static String kApiHost;
    static String kApiPay;
    static String kApiRefund;
    static String kApiQueryBill;
    static String kApiQueryRefund;
    

    static String getAppSignature(String timeStamp) {
        String str = BCCache.getAppID() + timeStamp + BCCache.getAppSecret() ;
        return getMessageDigest(str);
    }
    
    private static String getMessageDigest(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] buffer = s.getBytes();
            //获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");

            //使用指定的字节更新摘要
            mdTemp.update(buffer);

            //获得密文
            byte[] md = mdTemp.digest();

            //把密文转换成十六进制的字符串形式
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

	static String getkApiPay() {
		return kApiHost = BCCache.apiHostArray[(int)(Math.random()*4)] + "/" + BCUtilPrivate.kApiVersion + "/rest/pay";
	}

	static String getkApiRefund() {
		return kApiHost = BCCache.apiHostArray[(int)(Math.random()*4)] + "/" + BCUtilPrivate.kApiVersion + "/rest/refund";
	}

	static String getkApiQueryBill() {
		return kApiHost = BCCache.apiHostArray[(int)(Math.random()*4)] + "/" + BCUtilPrivate.kApiVersion + "/rest/pay/query";
	}

	static String getkApiQueryRefund() {
		return kApiHost = BCCache.apiHostArray[(int)(Math.random()*4)] + "/" + BCUtilPrivate.kApiVersion + "/rest/refund/query";
	}
	
	static String transferDateFromLongToString(long millisecond) {
		Date date = new Date(millisecond);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}

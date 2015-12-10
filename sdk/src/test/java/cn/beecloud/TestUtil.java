package cn.beecloud;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 测试工具类
 * 
 * @author Rui
 * @since 2015/11/07
 *
 */
public class TestUtil {
    static String transferDateFromLongToString(long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}

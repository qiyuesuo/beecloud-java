package cn.beecloud;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil {
	static String transferDateFromLongToString(long millisecond) {
		Date date = new Date(millisecond);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}

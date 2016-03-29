package cn.beecloud;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    /**
     * 使用mock url生成mock html
     */
    static String generateSandboxHtmlWithUrl(String url) {
        StringBuffer mockHtml = new StringBuffer();
        String[] urlString = url.split("\\?");
        String urlSection = urlString[0];
        String[] paraSection = urlString[1].split("&");

        mockHtml.append("<form id=\"beecloudmock\" name=\"beecloudmock\" action=\"" + urlSection
                + "\" method=\"get\">");

        for (int i = 0; i < paraSection.length; i++) {
            String name = paraSection[i].split("=")[0];
            String value = paraSection[i].split("=")[1];

            try {
                mockHtml.append(
                        "<input type=\"hidden\" name=\"" + name + "\" value=\"" + URLDecoder.decode(value, "UTF-8") + "\"/>");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        // submit按钮控件请不要含有name属性
        mockHtml.append("<input type=\"submit\" value=\"确认\" style=\"display:none;\"></form>");
        mockHtml.append("<script>document.forms['beecloudmock'].submit();</script>");
        return mockHtml.toString();
    }
}

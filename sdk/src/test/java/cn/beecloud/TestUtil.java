package cn.beecloud;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;
import cn.beecloud.bean.Condition;
import cn.beecloud.constant.TestConstant;
import cn.beecloud.util.CommonTestUtil;
import cn.beecloud.util.ali.MD5;


public class TestUtil {

	public static String getObjectId(Client client, String appId, String appKey, String table, String billNo) {
        Map<String, Object> sp = queryByCondition(client, appId, appKey, table, conds);
        Map<String, Object> bill = ((List<Map<String, Object>>) sp.get("results")).get(0);
        return StrUtil.toStr(bill.get(Constant.kKeyObjectId));
    }
	
	public static Map<String, Object> queryByCondition(Client client, String appId, String appKey, String table, String billNo) {
        List<Map<String, Object>> cond_ = new LinkedList<Map<String, Object>>();
        Map<String, Object> billNoMap = new HashMap<String, Object>();
        billNoMap.put("cname", "bill_no");
        billNoMap.put("value", billNO);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("table", table);
        map.put("appId", appId);
        map.put("appSign", MD5.sign(appId, appKey, "UTF-8"));
        map.put("conditions", cond_);

        JSONObject json = JSONObject.fromObject(map);
        StringBuffer sb = new StringBuffer(TestConstant.TEST_SERVER_URL_V1 + "query/byCondition?para=");
        try {
            sb.append(URLEncoder.encode(json.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        WebTarget target = client.target(sb.toString());
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Map<String, Object> res = response.readEntity(Map.class);
        return res;
    }
}

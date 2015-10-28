package cn.beecloud;

import static junit.framework.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;


public class TestUtil {

	public static String getObjectId(Client client, String appId, String appKey, String table, String billNo) {
        Map<String, Object> sp = queryByCondition(client, appId, appKey, table, billNo);
        Map<String, Object> bill = ((List<Map<String, Object>>) sp.get("results")).get(0);
        return StrUtil.toStr(bill.get("objectid"));
    }
	
	public static Map<String, Object> queryByCondition(Client client, String appId, String appKey, String table, String billNo) {
        List<Map<String, Object>> cond_ = new LinkedList<Map<String, Object>>();
        Map<String, Object> billNoMap = new HashMap<String, Object>();
        billNoMap.put("cname", "bill_no");
        billNoMap.put("value", billNo);
        billNoMap.put("type", "e");
        cond_.add(billNoMap);
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
	
	/** This method is used to test the api reponse time, if exceed expect time, it will fail jenkins unit tests
    *
    * @param target please speified uri before pass to this method
    * @param mediaType e.g MediaType.APPLICATION_JSON
    * @param type request method, such as post, get
    * @return
    */
   public static Response apiRequest(WebTarget target, String mediaType, TestConstant.REQUEST_TYPE type, int timeout,  Object map){
       Response response = null;
       long startTime = System.currentTimeMillis();
       try {
           switch (type){
               case GET: response = target.request(mediaType).get();
                   break;
               case POST: response = target.request(mediaType).post(Entity.entity(map, mediaType));
                   break;
           }
       } catch (Exception e){
           System.out.println("[Error] Requesting uri: " + target.getUri());
           System.out.println(e.getMessage());
           e.printStackTrace();
       }
       long endTime = System.currentTimeMillis();
       long duration = (endTime - startTime)/1000;
       boolean res = (timeout == 0)||(duration < timeout);
       if(!res){
           System.out.println("[Error] Requesting uri: " + target.getUri() + "; Reponse time was " + duration + " seconds");
       }
       assertEquals(true, res);
       return response;
   }
   
   public static String randomServerUrl(){
       int rand = new Random().nextInt(TestConstant.TEST_SERVER_URL_ARRAY.length);
       String res = TestConstant.TEST_SERVER_URL_ARRAY[rand];
//       String res = TestConstant.TEST_SERVER_URL_V1;
       //String res = "http://58.211.191.123:8080/1/";
       //String res = "http://192.168.1.101:8080/1/";
       System.out.println("TestConstant.TEST_SERVER_URL_V1 is set as: " + res);
       return res;
   }
}

package cn.beecloud;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import cn.beecloud.bean.BCException;
import net.sf.json.JSONObject;

/**
 * User: joseph Date: 16/7/6
 */
public class RequestUtil {

    private final static String NOT_REGISTER = "未注册";

    private final static String NOT_CORRECT_RESPONSE = "响应不正确";

    private final static String NETWORK_ERROR = "网络错误";

    private final static String TEST_MODE_SUPPORT_ERROR = "测试模式仅支持国内支付(WX_JSAPI暂不支持)、订单查询、订单总数查询、单笔订单查询";

    public enum REQUEST_TYPE {
        POST,
        PUT,
        GET,
        DELETE
    }

    /**
     * doPost方法，封装rest api POST方式请求
     *
     * @param requestUrl
     * 请求url
     * @param param
     * 请求参数
     * @return rest api返回参数
     * @throws BCException
     */
    public static Map<String, Object> doPost(String requestUrl, Map<String, Object> param) throws BCException {
        return request(requestUrl, param, REQUEST_TYPE.POST);
    }

    /**
     * doPut方法，封装rest api PUT方式请求
     *
     * @param requestUrl
     * 请求url
     * @param param
     * 请求参数
     * @return rest api返回参数
     * @throws BCException
     */
    public static Map<String, Object> doPut(String requestUrl, Map<String, Object> param) throws BCException {
        return request(requestUrl, param, REQUEST_TYPE.PUT);
    }

    /**
     * doGet方法，封装rest api GET方式请求
     *
     * @param requestUrl
     * 请求url
     * @param param
     * 请求参数
     * @return rest api返回参数
     * @throws BCException
     */
    public static Map<String, Object> doGet(String requestUrl, Map<String, Object> param) throws BCException {
        return request(requestUrl, param, REQUEST_TYPE.GET);
    }

    public static Map<String, Object> doGet(String requestUrl, String param) throws BCException {
        return request(requestUrl, param, REQUEST_TYPE.GET);
    }

    public static Map<String, Object> doDelete(String requestUrl, String param) throws BCException {
        return request(requestUrl, param, REQUEST_TYPE.DELETE);
    }

    /***
     *
     * @param requestUrl
     * @param param
     * @param request_type
     * @return
     * @throws BCException
     */
    public static Map<String, Object> request(String requestUrl, Object param, REQUEST_TYPE request_type)
            throws BCException {
        HttpURLConnection connection = null;
        Object appId;
        Boolean paramIsMap = true;
        if (param instanceof Map) {//参数为Map
            appId = ((Map)param).get("app_id");
        } else {//参数为String
            appId = StrUtil.toStr(param).contains("app_id")?new Object():null;
            paramIsMap = false;
        }
        if (StrUtil.empty(appId)) {
            throw new BCException(-2, BCEumeration.RESULT_TYPE.OTHER_ERROR.name(), NOT_REGISTER);
        }
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        Integer reponseStatus;
        try {
            if (request_type == REQUEST_TYPE.GET || request_type == REQUEST_TYPE.DELETE) {
                if (paramIsMap) {
                    requestUrl = requestUrl + URLEncoder.encode(JSONObject.fromObject(param).toString(), "UTF-8");
                } else {
                    requestUrl = requestUrl + param;
                }
            }
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request_type.name());
            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("Charset", "UTF-8");

            connection.setReadTimeout(30000);
            connection.setConnectTimeout(30000);
            connection.setDoInput(true);

            // POST || PUT
            if (request_type == REQUEST_TYPE.PUT || request_type == REQUEST_TYPE.POST) {
                connection.setDoOutput(true);
                // Send request
                // 获取URLConnection对象对应的输出流
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(StrUtil.toStr(JSONObject.fromObject(param)).getBytes("UTF-8"));
                outputStream.close();
                // flush输出流的缓冲
//                out.flush();
            }

            reponseStatus = connection.getResponseCode();

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            JSONObject jsonObject = JSONObject.fromObject(StrUtil.toStr(result));
            Integer resultCode = jsonObject.getInt("result_code");
            String resultMessage = jsonObject.getString("result_msg");
            String errorDetail = jsonObject.getString("err_detail");
            if (resultCode == 0) {
                return jsonToMap(jsonObject);
            } else {
                throw new BCException(resultCode, resultMessage, errorDetail, reponseStatus);
            }
        } catch (BCException e) {
            throw e;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BCException(-2, BCEumeration.RESULT_TYPE.OTHER_ERROR.name(), "编码错误" + "," + e.getMessage());
        } catch (ProtocolException e) {
            e.printStackTrace();
            throw new BCException(-2, BCEumeration.RESULT_TYPE.OTHER_ERROR.name(), "网络异常" + "," + e.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new BCException(-2, BCEumeration.RESULT_TYPE.OTHER_ERROR.name(), "url不合法" + "," + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BCException(-2, BCEumeration.RESULT_TYPE.OTHER_ERROR.name(), "网络异常" + "," + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BCException(-2, BCEumeration.RESULT_TYPE.OTHER_ERROR.name(), "未知错误" + "," + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static Map<String, Object> jsonToMap(JSONObject json) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        for (Object key : json.keySet()) {
            resultMap.put(StrUtil.toStr(key), json.get(key));
        }
        return resultMap;
    }
}

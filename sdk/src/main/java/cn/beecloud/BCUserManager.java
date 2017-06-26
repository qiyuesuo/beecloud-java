package cn.beecloud;

import cn.beecloud.bean.*;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sy on 2017/6/22.
 */
public class BCUserManager {
    /**
     * 单个用户注册接口
     *
     * @param user
     * {@link BCUser} (必填) 用户参数
     * @return 调起BeeCloud用户注册后的返回结果
     * @throws BCException
     */
    public static Map<String, Object> userRegister(BCUserInfo user) throws BCException {
        ValidationUtil.validateBCUserRegister(user);
        Map<String, Object> param = new HashMap<String, Object>();
        buildUserRegisterParam(param, user);
        return RequestUtil.doPost(BCUtilPrivate.getApiUser(), param);
    }

    /**
     * 批量用户导入接口
     *
     * @param user
     * {@link BCUsers} (必填) 用户参数
     * @return 调起批量用户导入后的返回结果
     * @throws BCException
     */
    public static Map<String, Object> userBatchImport(BCUsers user) throws BCException {
        ValidationUtil.validateBCUsersImport(user);
        Map<String, Object> param = new HashMap<String, Object>();
        buildUserBatchImportParam(param, user);
        return RequestUtil.doPost(BCUtilPrivate.getApiUsers(), param);
    }

    /**
     * 商户用户批量查询接口
     *
     * @param user
     * {@link BCUsers} (必填) 用户参数
     * @return 调起批量用户导入后的返回结果
     * @throws BCException
     */
    public static Map<String, Object> userBatchQuery(BCUsersQuery user) throws BCException {
        ValidationUtil.validateBCUsersQuery(user);
        Map<String, Object> param = new HashMap<String, Object>();
        buildUserBatchQueryParam(param, user);
        String paramStr = "";
        try {
            paramStr = "?para=" + URLEncoder.encode(JSONObject.fromObject(param).toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BCException(-2, BCEumeration.RESULT_TYPE.OTHER_ERROR.name(), "编码错误" + "," + e.getMessage());
        }
        return RequestUtil.doGet(BCUtilPrivate.getApiUsers(), paramStr);
    }

    /**
     * 构建支付rest api参数
     */
    private static void buildUserRegisterParam(Map<String, Object> param, BCUserInfo para) {

        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", Long.valueOf(para.getTimeStamp()));
        if (BCCache.isSandbox()) {
            param.put("app_sign", BCUtilPrivate.getAppSignatureWithTestSecret(StrUtil.toStr(param
                    .get("timestamp"))));
        } else {
            param.put("app_sign",
                    BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        }
        param.put("buyer_id", para.getBuyerId());
    }

    /**
     * 构建支付rest api参数
     */
    private static void buildUserBatchImportParam(Map<String, Object> param, BCUsers para) {

        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", Long.valueOf(para.getTimeStamp()));
        if (BCCache.isSandbox()) {
            param.put("app_sign", BCUtilPrivate.getAppSignatureWithTestSecret(StrUtil.toStr(param
                    .get("timestamp"))));
        } else {
            param.put("app_sign",
                    BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        }
        param.put("email", para.getEmail());
        param.put("buyer_ids", para.getBuyerIds());
    }

    /**
     * 构建支付rest api参数
     */
    private static void buildUserBatchQueryParam(Map<String, Object> param, BCUsersQuery para) {

        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", Long.valueOf(para.getTimeStamp()));
        if (BCCache.isSandbox()) {
            param.put("app_sign", BCUtilPrivate.getAppSignatureWithTestSecret(StrUtil.toStr(param
                    .get("timestamp"))));
        } else {
            param.put("app_sign",
                    BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        }
        param.put("email", para.getEmail());
        param.put("buyer_type", para.getBuyerType());
    }
}

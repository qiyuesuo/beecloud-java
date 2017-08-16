package cn.beecloud;

import cn.beecloud.bean.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sunqifs on 8/15/17.
 */
public class BCCouponManage {

    public static BCCouponTemplate startQueryCouponTemplateById(String couponTemplateId) throws BCException {

        ValidationUtil.validateQueryById(couponTemplateId);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign",
                BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));

        StringBuilder urlSb = new StringBuilder();
        urlSb.append(BCUtilPrivate.getkApiQueryCouponTemplate());
        urlSb.append("/").append(couponTemplateId);
        urlSb.append("?para=");
        Map<String, Object> ret = RequestUtil.doGet(urlSb.toString(), param);

        return generateBCCouponTemplate((Map<String, Object>) ret.get("coupon_template"));
    }

    public static List<BCCouponTemplate> startQueryCouponTemplates(BCQueryCouponTemplateParam queryParam)
            throws BCException {
        Map<String, Object> param = buildQueryCouponTemplateParam(queryParam);
        StringBuilder urlSb = new StringBuilder();
        urlSb.append(BCUtilPrivate.getkApiQueryCouponTemplate());
        urlSb.append("?para=");
        Map<String, Object> ret = RequestUtil.doGet(urlSb.toString(), param);

        return generateBCCouponTemplateList((List<Map<String, Object>>) ret.get("coupon_templates"));
    }

    public static BCCoupon startCouponDistribute(String templateId, String userId)
            throws BCException {
        Map<String, Object> param = buildCouponParam(templateId, userId);
        Map<String, Object> ret = RequestUtil.doPost(BCUtilPrivate.getkApiCoupon(), param);

        return generateBCCoupon((Map<String, Object>) ret.get("coupon"));
    }

    public static BCCoupon startQueryCouponById(String couponId) throws BCException {
        ValidationUtil.validateQueryById(couponId);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign",
                BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));

        StringBuilder urlSb = new StringBuilder();
        urlSb.append(BCUtilPrivate.getkApiCoupon());
        urlSb.append("/").append(couponId);
        urlSb.append("?para=");
        Map<String, Object> ret = RequestUtil.doGet(urlSb.toString(), param);

        return generateBCCoupon((Map<String, Object>) ret.get("coupon"));

    }

    public static List<BCCoupon> startQueryCoupons(BCQueryCouponParam queryCouponParam)
            throws BCException {
        Map<String, Object> param = buildQueryCouponParam(queryCouponParam);
        StringBuilder urlSb = new StringBuilder();
        urlSb.append(BCUtilPrivate.getkApiCoupon());
        urlSb.append("?para=");
        Map<String, Object> ret = RequestUtil.doGet(urlSb.toString(), param);

        return generateBCCouponList((List<Map<String, Object>>) ret.get("coupons"));
    }

    private static List<BCCoupon> generateBCCouponList(List<Map<String, Object>> coupons) {
        List<BCCoupon> bcCouponList = new LinkedList<BCCoupon>();
        for (Map<String, Object> coupon : coupons) {
            bcCouponList.add(generateBCCoupon(coupon));
        }
        return bcCouponList;
    }

    private static Map<String, Object> buildQueryCouponParam(BCQueryCouponParam queryCouponParam) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign",
                BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        if (!StrUtil.empty(queryCouponParam.getUserId())) {
            param.put("user_id", queryCouponParam.getUserId());
        }
        if (!StrUtil.empty(queryCouponParam.getTemplateId())) {
            param.put("template_id", queryCouponParam.getTemplateId());
        }
        if (!StrUtil.empty(queryCouponParam.getStatus())) {
            param.put("status", queryCouponParam.getStatus());
        }
        if (!StrUtil.empty(queryCouponParam.getLimitFee())) {
            param.put("limit_fee", queryCouponParam.getLimitFee());
        }
        if (!StrUtil.empty(queryCouponParam.getCreatedBefore())) {
            param.put("created_before", queryCouponParam.getCreatedBefore());
        }
        if (!StrUtil.empty(queryCouponParam.getCreatedAfter())) {
            param.put("created_after", queryCouponParam.getCreatedAfter());
        }
        if (!StrUtil.empty(queryCouponParam.getSkip())) {
            param.put("skip", queryCouponParam.getSkip());
        }
        if (!StrUtil.empty(queryCouponParam.getLimit())) {
            param.put("limit", queryCouponParam.getLimit());
        }

        return param;
    }

    private static BCCoupon generateBCCoupon(Map<String, Object> couponMap) {
        BCCoupon bcCoupon = new BCCoupon();
        bcCoupon.setId(StrUtil.toStr(couponMap.get("id")));
        bcCoupon.setTemplate(StrUtil.toStr(couponMap.get("template")));
        bcCoupon.setUserId(StrUtil.toStr(couponMap.get("user_id")));
        bcCoupon.setAppId(StrUtil.toStr(couponMap.get("app_id")));
        bcCoupon.setStatus((Integer) couponMap.get("status"));
        bcCoupon.setCreatedAt((Long) couponMap.get("created_at"));
        bcCoupon.setUpdatedAt((Long) couponMap.get("updated_at"));
        bcCoupon.setStartTime((Long) couponMap.get("start_time"));
        bcCoupon.setEndTime((Long) couponMap.get("end_time"));
        bcCoupon.setUseTime((Long) couponMap.get("use_time"));

        return bcCoupon;
    }

    private static Map<String, Object> buildCouponParam(String templateId, String userId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign",
                BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        param.put("template_id", templateId);
        param.put("user_id", userId);

        return param;
    }

    private static Map<String, Object> buildQueryCouponTemplateParam(BCQueryCouponTemplateParam queryParam) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign",
                BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        if (!StrUtil.empty(queryParam.getName())) {
            param.put("name", queryParam.getName());
        }
        if (!StrUtil.empty(queryParam.getCreatedBefore())) {
            param.put("created_before", queryParam.getCreatedBefore());
        }
        if (!StrUtil.empty(queryParam.getCreatedAfter())) {
            param.put("created_after", queryParam.getCreatedAfter());
        }
        if (!StrUtil.empty(queryParam.getSkip())) {
            param.put("skip", queryParam.getSkip());
        }
        if (!StrUtil.empty(queryParam.getLimit())) {
            param.put("limit", queryParam.getLimit());
        }

        return param;
    }

    private static List<BCCouponTemplate> generateBCCouponTemplateList(List<Map<String, Object>> couponTemplates) {
        List<BCCouponTemplate> bcCouponTemplateList = new LinkedList<BCCouponTemplate>();
        for (Map<String, Object> couponTemplateMap : couponTemplates) {
            bcCouponTemplateList.add(generateBCCouponTemplate(couponTemplateMap));
        }

        return bcCouponTemplateList;
    }

    private static BCCouponTemplate generateBCCouponTemplate(Map<String, Object> couponTemplateMap) {
        BCCouponTemplate bcCouponTemplate = new BCCouponTemplate();
        bcCouponTemplate.setId(StrUtil.toStr(couponTemplateMap.get("id")));
        bcCouponTemplate.setName(StrUtil.toStr(couponTemplateMap.get("name")));
        bcCouponTemplate.setType(StrUtil.parseInt(StrUtil.toStr(couponTemplateMap.get("type"))));
        bcCouponTemplate.setCode(StrUtil.toStr(couponTemplateMap.get("code")));
        bcCouponTemplate.setLimitFee(StrUtil.parseInt(StrUtil.toStr(couponTemplateMap.get("limit_fee"))));
        bcCouponTemplate.setDiscount(StrUtil.parseFloat(StrUtil.toStr(couponTemplateMap.get("discount"))));
        bcCouponTemplate.setTotalCount(StrUtil.parseInt(StrUtil.toStr(couponTemplateMap.get("total_count"))));
        bcCouponTemplate.setMaxCountPerUser(StrUtil.parseInt(StrUtil.toStr(couponTemplateMap.get("max_count_per_user"))));
        bcCouponTemplate.setDeliverCount(StrUtil.parseInt(StrUtil.toStr(couponTemplateMap.get("max_count_per_user"))));
        bcCouponTemplate.setUseCount(StrUtil.parseInt(StrUtil.toStr(couponTemplateMap.get("max_count_per_user"))));
        bcCouponTemplate.setExpiryType(StrUtil.parseInt(StrUtil.toStr(couponTemplateMap.get("id"))));
        bcCouponTemplate.setStartTime(StrUtil.parseLong(StrUtil.toStr(couponTemplateMap.get("id"))));
        bcCouponTemplate.setEndTime(StrUtil.parseLong(StrUtil.toStr(couponTemplateMap.get("id"))));
        bcCouponTemplate.setDeliveryValidDays(StrUtil.parseInt(StrUtil.toStr(couponTemplateMap.get("id"))));
        bcCouponTemplate.setStatus(StrUtil.parseInt(StrUtil.toStr(couponTemplateMap.get("id"))));
        bcCouponTemplate.setMchAccount(StrUtil.toStr(couponTemplateMap.get("id")));
        bcCouponTemplate.setAppId(StrUtil.toStr(couponTemplateMap.get("id")));

        return bcCouponTemplate;
    }
}

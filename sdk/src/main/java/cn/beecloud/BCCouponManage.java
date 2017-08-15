package cn.beecloud;

import cn.beecloud.bean.BCCouponTemplate;
import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCQueryCouponTemplateParam;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sunqifs on 8/15/17.
 */
public class BCCouponManage {

    public static BCCouponTemplate startQueryCouponTemplateById(String couponId) throws BCException {

        ValidationUtil.validateQueryById(couponId);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        StringBuilder urlSb = new StringBuilder();
        param.put("app_sign",
                BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        urlSb.append(BCUtilPrivate.getkApiQueryCouponTemplateById(couponId));

        urlSb.append("?para=");
        Map<String, Object> ret = RequestUtil.doGet(urlSb.toString(), param);

        return generateBCCouponTemplate((Map<String, Object>) ret.get("coupon_template"));
    }

    public static List<BCCouponTemplate> startQueryCouponTemplate(BCQueryCouponTemplateParam queryParam)
            throws BCException {
        Map<String, Object> param = buildQueryCouponTemplateParam(queryParam);
        Map<String, Object> ret = RequestUtil.doGet(BCUtilPrivate.getkApiQueryCouponTemplate(), param);

        return generateBCCouponTemplateList((List<Map<String, Object>>) ret.get("coupon_templates"));
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

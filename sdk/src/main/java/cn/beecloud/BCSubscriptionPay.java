package cn.beecloud;

import cn.beecloud.bean.*;

import java.util.*;

/**
 * Created by rui on 16/7/26.
 */
public class BCSubscriptionPay {
    /**
     * 发送验证码接口
     *
     * @param phone
     * @return 包含sms_id和code的Map
     * @throws BCException
     */
    public static String sendSMS(String phone) throws BCException {


        Map<String, Object> param = new HashMap<String, Object>();

        buildSMSParam(param, phone);

        Map<String, Object> ret = RequestUtil.doPost(BCUtilPrivate.getkApiSendSMS(), param);

        return StrUtil.toStr(ret.get("sms_id"));
    }

    /**
     * 发起订阅
     *
     * @param subscription
     * @return 包含sms_id和code的Map
     * @throws BCException
     */


    public static BCSubscription startSubscription(BCSubscription subscription) throws BCException {

        Map<String, Object> param = new HashMap<String, Object>();

        buildSubscriptionParam(param, subscription);

        Map<String, Object> ret = RequestUtil.doPost(BCUtilPrivate.getkApiSubscription(), param);

        Map<String, Object> subscriptionResult = (Map<String, Object>) ret.get("subscription");

        placeSubsciption(subscription, subscriptionResult);

        return subscription;
    }

    public static String cancelSubscription(BCSubscription subscription) throws BCException{

        Map<String, Object> ret = RequestUtil.doDelete(BCUtilPrivate.getkApiSubscription(), StrUtil.toStr(buildCancelSubscription(subscription)));

        return StrUtil.toStr(ret.get("id"));
    }

    public static Object fetchPlanByCondition(BCPlanQueryParameter para) throws BCException{
        Map<String, Object> ret = RequestUtil.doGet(BCUtilPrivate.getkApiQueryPlan(), buildPlanQueryParam(para));
        if (para.getCountOnly()) {
            return ret.get("total_count");
        }
        return placePlanList((List<Map<String, Object>>) ret.get("plans"));
    }

    public static Object fetchSubsciptionByCondition(BCSubscriptionQueryParameter para) throws BCException {
        Map<String, Object> ret = RequestUtil.doGet(BCUtilPrivate.getkApiQuerySubscription(), buildSubscriptionQueryParam(para));
        if (para.getCountOnly()) {
            return ret.get("total_count");
        }
        return placeSubscriptionList((List<Map<String, Object>>) ret.get("subscriptions"));
    }

    public static SubscriptionBanks fetchSubscrptionBanks() throws BCException{
        Map<String, Object> ret = RequestUtil.doGet(BCUtilPrivate.getkApiSubscriptionBanks(), StrUtil.toStr(buildBasicQueryParam()));
        return placeSubscriptionBanks(ret);
    }

    /**
     * 构建短信验证参数
     */
    private static void buildSMSParam(Map<String, Object> param, String phone) {
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign",
                BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        param.put("phone", StrUtil.toStr(phone));
    }

    private static void buildSubscriptionParam(Map<String, Object> param, BCSubscription subscription) {
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign",
                BCUtilPrivate.getAppSignature(StrUtil.toStr(param.get("timestamp"))));
        param.put("sms_id", subscription.getSmsId());
        param.put("sms_code", subscription.getSmsCode());
        param.put("mobile", subscription.getMobile());
        param.put("buyer_id", subscription.getBuyerId());
        param.put("plan_id", subscription.getPlanId());
        if (!StrUtil.empty(subscription.getCardId())) {
            param.put("card_id", subscription.getCardId());
        }
        if (!StrUtil.empty(subscription.getBankName())) {
            param.put("bank_name", subscription.getBankName());
        }
        if (!StrUtil.empty(subscription.getCardNo())) {
            param.put("card_no", subscription.getCardNo());
        }
        if (!StrUtil.empty(subscription.getIdName())) {
            param.put("id_name", subscription.getIdName());
        }
        if (!StrUtil.empty(subscription.getIdNo())) {
            param.put("id_no", subscription.getIdNo());
        }
        if (!StrUtil.empty(subscription.getAmount())) {
            param.put("amount", subscription.getAmount());
        }
        if (!StrUtil.empty(subscription.getCouponId())) {
            param.put("coupon_id", subscription.getCouponId());
        }
        if (!StrUtil.empty(subscription.getTrialEnd())) {
            param.put("trial_end", subscription.getTrialEnd().getTime());
        }
        if (!StrUtil.empty(subscription.getOptional())) {
            param.put("optional", subscription.getOptional());
        }
    }

    /**
     * 构建Plan查询rest api参数
     */
    private static String buildPlanQueryParam(BCPlanQueryParameter para) {
        StringBuilder sb = buildBasicQueryParam();

        if (para.getSkip() != null) {
            sb.append("&");
            sb.append("timestamp=");
            sb.append(para.getSkip());
        }
        if (para.getLimit() != null) {
            sb.append("&");
            sb.append("limit=");
            sb.append(para.getLimit());
        }
        if (para.getSkip() != null) {
            sb.append("&");
            sb.append("skip=");
            sb.append(para.getSkip());
        }
        if (para.getStartTime() != null) {
            sb.append("&");
            sb.append("created_after=");
            sb.append(para.getStartTime().getTime());
        }
        if (para.getEndTime() != null) {
            sb.append("&");
            sb.append("created_before=");
            sb.append(para.getEndTime().getTime());
        }
        if (para.getCountOnly() != null) {
            sb.append("&");
            sb.append("count_only=");
            sb.append(para.getCountOnly());
        }
        if (para.getNameWithSubstring() != null) {
            sb.append("&");
            sb.append("name_with_substring=");
            sb.append(para.getNameWithSubstring());
        }
        if (para.getInterval() != null) {
            sb.append("&");
            sb.append("interval=");
            sb.append(para.getInterval());
        }
        if (para.getIntervalCount() != null) {
            sb.append("&");
            sb.append("interval_count=");
            sb.append(para.getIntervalCount());
        }
        if (para.getTrialDays() != null) {
            sb.append("&");
            sb.append("trial_days=");
            sb.append(para.getTrialDays());
        }
        return StrUtil.toStr(sb);
    }

    private static String buildSubscriptionQueryParam(BCSubscriptionQueryParameter para) {
        StringBuilder sb = buildBasicQueryParam();

        if (para.getCountOnly() != null) {
            sb.append("&");
            sb.append("count_only=");
            sb.append(para.getCountOnly());
        }
        if (para.getBuyerId() != null) {
            sb.append("&");
            sb.append("buyer_id=");
            sb.append(para.getBuyerId());
        }
        if (para.getPlanId() != null) {
            sb.append("&");
            sb.append("plan_id=");
            sb.append(para.getPlanId());
        }
        if (para.getCardId() != null) {
            sb.append("&");
            sb.append("card_id=");
            sb.append(para.getCardId());
        }
        if (para.getStartTime() != null) {
            sb.append("&");
            sb.append("created_after=");
            sb.append(para.getStartTime().getTime());
        }
        if (para.getEndTime() != null) {
            sb.append("&");
            sb.append("created_before=");
            sb.append(para.getEndTime().getTime());
        }
        return StrUtil.toStr(sb);
    }

    private static StringBuilder buildCancelSubscription(BCSubscription subscription) {
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        sb.append(subscription.getObjectId());
        sb.append("?");
        sb.append(buildBasicQueryParam());

        if (subscription.getCancelAtPeriodEnd() != null) {
            sb.append("?at_period_end=");
            sb.append(subscription.getCancelAtPeriodEnd());
        }
        return sb;
    }

    private static StringBuilder buildBasicQueryParam() {
        StringBuilder sb = new StringBuilder();
        if (!StrUtil.empty(BCCache.getAppID())) {
            sb.append("app_id=");
            sb.append(BCCache.getAppID());
            sb.append("&");
        }

        long currentTime = System.currentTimeMillis();
        sb.append("timestamp=");
        sb.append(currentTime);
        sb.append("&");

        sb.append("app_sign=");
        sb.append(BCUtilPrivate.getAppSignature(StrUtil.toStr(currentTime)));
        return sb;
    }

    private static List<BCPlan> placePlanList(List<Map<String, Object>> plans) {
        List<BCPlan> bcPlanList = new ArrayList<BCPlan>();
        for (Map<String, Object> plan : plans) {
            BCPlan bcPlan = new BCPlan();
            generateBCPlanBean(plan, bcPlan);
            bcPlanList.add(bcPlan);
        }
        return bcPlanList;
    }

    private static List<BCSubscription> placeSubscriptionList(List<Map<String, Object>> subscriptions) {
        List<BCSubscription> subscriptionList = new ArrayList<BCSubscription>();
        for (Map<String, Object> subscription : subscriptions) {
            BCSubscription bcSubscription = new BCSubscription();
            generateBCSubscriptionBean(subscription, bcSubscription);
            subscriptionList.add(bcSubscription);
        }
        return subscriptionList;
    }

    private static BCSubscription placeSubsciption(BCSubscription subscription, Map<String, Object> ret) {
        generateBCSubscriptionBean(ret, subscription);
        return subscription;
    }

    private static SubscriptionBanks placeSubscriptionBanks(Map<String, Object> ret) {
        SubscriptionBanks banks = new SubscriptionBanks();
        banks.setBankList((List)ret.get("banks"));
        banks.setCommonBankList((List)ret.get("common_banks"));
        return banks;
    }

    private static BCPlan generateBCPlanBean(Map<String, Object> plan, BCPlan bcPlan) {
        if (plan.containsKey("created_at")) {
            bcPlan.setCreateDate(new Date((Long) plan
                    .get("created_at")));
        }
        if (plan.containsKey("updated_at")) {
            bcPlan.setUpdateDate(new Date((Long) plan
                    .get("updated_at")));
        }
        if (plan.containsKey("currency")) {
            bcPlan.setCurrency(StrUtil.toStr(plan.get("currency")));
        }
        if (plan.containsKey("object_type")) {
            bcPlan.setType(StrUtil.toStr(plan.get("object_type")));
        }
        if (plan.containsKey("fee")) {
            bcPlan.setFee((Integer) plan.get("fee"));
        }
        if (plan.containsKey("name")) {
            bcPlan.setName(StrUtil.toStr(plan.get("name")));
        }
        if (plan.containsKey("interval")) {
            bcPlan.setInterval(StrUtil.toStr(plan.get("interval")));
        }
        if (plan.containsKey("interval_count")) {
            bcPlan.setIntervalCount((Integer) plan.get("interval_count"));
        }
        if (plan.containsKey("id")) {
            bcPlan.setObjectId(StrUtil.toStr(plan.get("id")));
        }
        if (plan.containsKey("trial_days")) {
            bcPlan.setTrailDays((Integer) plan.get("trial_days"));
        }
        if (plan.containsKey("optional")) {
            bcPlan.setOptionalString(StrUtil.toStr(plan.get("optional")));
        }
        if (plan.containsKey("valid")) {
            bcPlan.setValid((Boolean)plan.get("valid"));
        }
        return bcPlan;
    }

    private static void generateBCSubscriptionBean(Map<String, Object> subscription, BCSubscription bcSubscription) {
        if (subscription.containsKey("id")) {
            bcSubscription.setObjectId(StrUtil.toStr(subscription.get("id")));
        }
        if (subscription.containsKey("account_type")) {
            bcSubscription.setAccountType(StrUtil.toStr(subscription.get("account_type")));
        }
        if (subscription.containsKey("cancel_at_period_end")) {
            bcSubscription.setCancelAtPeriodEnd((Boolean) subscription.get("cancel_at_period_end"));
        }
        if (subscription.containsKey("object_type")) {
            bcSubscription.setType(StrUtil.toStr(subscription.get("object_type")));
        }
        if (subscription.containsKey("created_at")) {
            bcSubscription.setCreateDate(new Date((Long) subscription.get("created_at")));
        }
        if (subscription.containsKey("updated_at")) {
            bcSubscription.setUpdateDate(new Date((Long) subscription.get("updated_at")));
        }
        if (subscription.containsKey("card_id")) {
            bcSubscription.setCardId(StrUtil.toStr(subscription.get("card_id")));
        }
        if (subscription.containsKey("coupon_id")) {
            bcSubscription.setCouponId(StrUtil.toStr(subscription.get("coupon_id")));
        }
        if (subscription.containsKey("valid")) {
            bcSubscription.setValid((Boolean) subscription.get("valid"));
        }
        if (subscription.containsKey("status")) {
            bcSubscription.setStatus(StrUtil.toStr(subscription.get("status")));
        }
        if (subscription.containsKey("plan_id")) {
            bcSubscription.setPlanId(StrUtil.toStr(subscription.get("plan_id")));
        }
        if (subscription.containsKey("buyer_id")) {
            bcSubscription.setBuyerId(StrUtil.toStr(subscription.get("buyer_id")));
        }
        if (subscription.containsKey("trial_end")) {
            bcSubscription.setTrialEnd(new Date((Long) subscription.get("trial_end")));
        }
        if (subscription.containsKey("amount")) {
            bcSubscription.setAmount((Double) subscription.get("amount"));
        }
        if (subscription.containsKey("mobile")) {
            bcSubscription.setMobile(StrUtil.toStr(subscription.get("mobile")));
        }
        if (subscription.containsKey("id_name")) {
            bcSubscription.setIdName(StrUtil.toStr(subscription.get("id_name")));
        }
        if (subscription.containsKey("id_no")) {
            bcSubscription.setIdNo(StrUtil.toStr(subscription.get("id_no")));
        }
        if (subscription.containsKey("bank_name")) {
            bcSubscription.setBankName(StrUtil.toStr(subscription.get("bank_name")));
        }
        if (subscription.containsKey("last4")) {
            bcSubscription.setLast4(StrUtil.toStr(subscription.get("last4")));
        }
        if (subscription.containsKey("optional")) {
            bcSubscription.setOptionalString(StrUtil.toStr(subscription.get("optional")));
        }
    }
}

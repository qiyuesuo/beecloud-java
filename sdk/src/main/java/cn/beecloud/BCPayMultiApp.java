/**
 * *************************
 *
 * @Date: Sep 18, 2015
 * @Time: 2:54:12 PM
 * @Author: Joseph Gao
 * <p/>
 * **************************
 */
package cn.beecloud;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.QR_PAY_MODE;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.BCOrderBean;
import cn.beecloud.bean.BCRefundBean;
import cn.beecloud.bean.TransferData;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.sf.json.JSONObject;

/**
 *
 * 主要为了一个商户下有多个收款APP使用，参考@BCPay
 *
 *
 * @author Gao
 * @since 2015/9/18
 */
public class BCPayMultiApp {

    private String appId;
    private String appSecret;

    private String getAppSignature(String timeStamp) {
        String str = appId + timeStamp + appSecret;
        return BCUtilPrivate.getMessageDigest(str);
    }

    public BCPayMultiApp(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    /**
     * @param channel   （必填）渠道类型， 根据不同场景选择不同的支付方式，包含：
     *                  WX_NATIVE 微信公众号二维码支付
     *                  WX_JSAPI 微信公众号支付
     *                  ALI_WEB 支付宝网页支付
     *                  ALI_QRCODE 支付宝内嵌二维码支付
     *                  ALI_WAP: 支付宝移动网页支付
     *                  UN_WEB 银联网页支付
     *                  JD_WAP: 京东移动网页支付
     *                  JD_WEB: 京东PC网页支付
     *                  YEE_WAP: 易宝移动网页支付
     *                  YEE_WEB: 易宝PC网页支付
     *                  KUAIQIAN_WAP: 快钱移动网页支付
     *                  KUAIQIAN_WEB: 快钱PC网页支付
     * @param totalFee  （必填）订单总金额， 只能为整数，单位为分，例如 1
     * @param billNo    （必填）商户订单号, 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一, 例如（201506101035040000001）
     * @param title     （必填）订单标题， 32个字节内，最长支持16个汉字
     * @param optional  （选填）附加数据， 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据
     * @param returnUrl （选填）同步返回页面	， 支付渠道处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径。当 channel 参数为 ALI_WEB 或 ALI_QRCODE 或 UN_WEB时为必填
     * @param openId    （选填）  微信公众号支付(WX_JSAPI)必填
     * @param showUrl   （选填）商品展示地址，需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.
     * @param qrPayMode （选填）二维码类型，二维码类型含义
     *                  MODE_BRIEF_FRONT： 订单码-简约前置模式, 对应 iframe 宽度不能小于 600px, 高度不能小于 300px
     *                  MODE_FRONT： 订单码-前置模式, 对应 iframe 宽度不能小于 300px, 高度不能小于 600px
     *                  MODE_MINI_FRONT： 订单码-迷你前置模式, 对应 iframe 宽度不能小于 75px, 高度不能小于 75px
     * @return BCPayResult
     */
    public BCPayResult startBCPay(PAY_CHANNEL channel, int totalFee,
                                  String billNo, String title,
                                  Map<String, String> optional, String returnUrl, String openId, String showUrl, QR_PAY_MODE qrPayMode) {

        BCPayResult result;
        result = ValidationUtil.validateBCPay(channel, billNo, title, returnUrl, openId);

        if (result.getType().ordinal() != 0) {
            return result;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", this.appId);
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", this.getAppSignature(param.get("timestamp").toString()));
        param.put("channel", channel.toString());
        param.put("total_fee", totalFee);
        param.put("bill_no", billNo);
        param.put("title", title);
        if (optional != null && optional.size() > 0)
            param.put("optional", optional);
        if (!StrUtil.empty(returnUrl))
            param.put("return_url", returnUrl);
        if (!StrUtil.empty(openId))
            param.put("openid", openId);
        if (!StrUtil.empty(showUrl))
            param.put("show_url", showUrl);
        if (qrPayMode != null) {
            if (qrPayMode.ordinal() == 2) {
                param.put("qr_pay_mode", String.valueOf(qrPayMode.ordinal() + 1));
            } else {
                param.put("qr_pay_mode", String.valueOf(qrPayMode.ordinal()));
            }
        }

        result = new BCPayResult();

        Client client = BCAPIClient.client;
        WebTarget target = client.target(BCUtilPrivate.getkApiPay());
        try {
            Response response = target.request().post(Entity.entity(param, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                        .toStr(ret.get("result_code")).equals("0"));
                if (isSuccess) {
                    if (channel.equals(PAY_CHANNEL.WX_NATIVE)) {
                        if (ret.containsKey("code_url") && null != ret.get("code_url")) {
                            result.setCodeUrl(ret.get("code_url").toString());
                            result.setType(RESULT_TYPE.OK);
                        }
                    } else if (channel.equals(PAY_CHANNEL.WX_JSAPI)) {
                        result.setType(RESULT_TYPE.OK);
                        result.setWxJSAPIMap(generateWXJSAPIMap(ret));
                    } else if (channel.equals(PAY_CHANNEL.ALI_WEB) || channel.equals(PAY_CHANNEL.ALI_QRCODE) || channel.equals(PAY_CHANNEL.ALI_WAP)) {
                        if (ret.containsKey("html") && null != ret.get("html") &&
                                ret.containsKey("url") && null != ret.get("url")) {
                            result.setHtml(ret.get("html").toString());
                            result.setUrl(ret.get("url").toString());
                            result.setType(RESULT_TYPE.OK);
                        }
                    } else if (channel.equals(PAY_CHANNEL.UN_WEB) || channel.equals(PAY_CHANNEL.JD_WAP)
                            || channel.equals(PAY_CHANNEL.JD_WEB) || channel.equals(PAY_CHANNEL.KUAIQIAN_WAP)
                            || channel.equals(PAY_CHANNEL.KUAIQIAN_WEB)) {
                        if (ret.containsKey("html") && null != ret.get("html")) {
                            result.setHtml(ret.get("html").toString());
                            result.setType(RESULT_TYPE.OK);
                        }
                    } else if (channel.equals(PAY_CHANNEL.YEE_WAP) || channel.equals(PAY_CHANNEL.YEE_WEB)) {
                        if (ret.containsKey("url") && null != ret.get("url")) {
                            result.setUrl(ret.get("url").toString());
                            result.setType(RESULT_TYPE.OK);
                        }
                    }
                } else {
                    result.setErrMsg(ret.get("result_msg").toString());
                    result.setErrDetail(ret.get("err_detail").toString());
                    result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
                result.setErrMsg("Not correct response!");
                result.setErrDetail("Not correct response!");
                result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
            result.setErrMsg("Network error!");
            result.setErrDetail(e.getMessage());
            result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
        return result;
    }

    /**
     * @param channel   （选填）渠道类型， 根据不同场景选择不同的支付方式，包含：
     *                  WX  微信
     *                  ALI 支付宝
     *                  UN 银联
     *                  YEE 易宝
     *                  JD 京东
     *                  KUAIQIAN 快钱
     * @param refundNo  （必填）商户退款单号	， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。
     *                  例如：201506101035040000001
     * @param billNo    （必填）商户订单号， 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一
     * @param refundFee （必填）退款金额， 只能为整数，单位为分，例如1
     * @param optional  （选填）附加数据 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据，例如{"key1":"value1","key2":"value2",...}
     * @return BCPayResult
     */
    public BCPayResult startBCRefund(PAY_CHANNEL channel, String refundNo, String billNo, int refundFee, Map optional) {

        BCPayResult result;
        result = ValidationUtil.validateBCRefund(channel, refundNo, billNo);

        if (result.getType().ordinal() != 0) {
            return result;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", this.appId);
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", this.getAppSignature(param.get("timestamp").toString()));
        if (channel != null) {
            param.put("channel", channel.toString());
        }
        param.put("refund_no", refundNo);
        param.put("bill_no", billNo);
        param.put("refund_fee", refundFee);
        if (optional != null && optional.size() > 0)
            param.put("optional", optional);

        result = new BCPayResult();

        Client client = BCAPIClient.client;

        WebTarget target = client.target(BCUtilPrivate.getkApiRefund());
        try {
            Response response = target.request().post(Entity.entity(param, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                        .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                    if (ret.containsKey("url")) {
                        result.setUrl(ret.get("url").toString());
                    }
                    result.setType(RESULT_TYPE.OK);
                    result.setSucessMsg(ValidationUtil.REFUND_SUCCESS);
                } else {
                    result.setErrMsg(ret.get("result_msg").toString());
                    result.setErrDetail(ret.get("err_detail").toString());
                    result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
                result.setErrMsg("Not correct response!");
                result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
            result.setErrMsg("Network error!");
            result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
        return result;
    }

    /**
     * @param channel   （选填）渠道类型， 根据不同场景选择不同的支付方式，包含：
     *                  WX
     *                  WX_APP 微信手机APP支付
     *                  WX_NATIVE 微信公众号二维码支付
     *                  WX_JSAPI 微信公众号支付
     *                  ALI
     *                  ALI_APP 支付宝APP支付
     *                  ALI_WEB 支付宝网页支付
     *                  ALI_WAP: 支付宝移动网页支付
     *                  ALI_QRCODE 支付宝内嵌二维码支付
     *                  UN
     *                  UN_APP 银联APP支付
     *                  UN_WEB 银联网页支付
     *                  JD
     *                  JD_WAP: 京东移动网页支付
     *                  JD_WEB: 京东PC网页支付
     *                  YEE
     *                  YEE_WAP: 易宝移动网页支付
     *                  YEE_WEB: 易宝PC网页支付
     *                  KUAIQIAN
     *                  KUAIQIAN_WAP: 快钱移动网页支付
     *                  KUAIQIAN_WEB: 快钱PC网页支付
     *                  PAYPAL
     *                  PAYPAL_SANDBOX: paypal 沙箱环境订单
     *                  PAYPAL_LIVE: paypal 生产环境订单
     * @param billNo    （选填） 商户订单号， 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一
     * @param startTime （选填） 起始时间， Date类型
     * @param endTime   （选填） 结束时间，Date类型
     * @param skip      （选填） 查询起始位置	 默认为0。设置为10，表示忽略满足条件的前10条数据
     * @param limit     （选填） 查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据
     * @return BCQueryResult
     */
    public BCQueryResult startQueryBill(PAY_CHANNEL channel, String billNo, Date startTime, Date endTime, Integer skip, Integer limit) {

        BCQueryResult result;

        result = ValidationUtil.validateQueryBill(billNo, limit);

        if (result.getType().ordinal() != 0) {
            return result;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", this.appId);
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", this.getAppSignature(param.get("timestamp").toString()));
        if (channel != null) {
            param.put("channel", channel.toString());
        }
        param.put("bill_no", billNo);
        param.put("skip", skip);
        param.put("limit", limit);
        if (startTime != null) {
            param.put("start_time", startTime.getTime());
        }
        if (endTime != null) {
            param.put("end_time", endTime.getTime());
        }

        result = new BCQueryResult();

        Client client = BCAPIClient.client;

        StringBuilder sb = new StringBuilder();
        sb.append(BCUtilPrivate.getkApiQueryBill());

        try {
            sb.append(URLEncoder.encode(
                    JSONObject.fromObject(param).toString(), "UTF-8"));

            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                        .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                    result.setType(RESULT_TYPE.OK);
                    if (ret.containsKey("bills")
                            && !StrUtil.empty(ret.get("bills"))) {
                        result.setBcOrders(generateBCOrderList((List<Map<String, Object>>) ret.get("bills")));
                    }
                } else {
                    result.setErrMsg(ret.get("result_msg").toString());
                    result.setErrDetail(ret.get("err_detail").toString());
                    result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
                result.setErrMsg("Not correct response!");
                result.setErrDetail("Not correct response!");
                result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
            result.setErrMsg("Network error!");
            result.setErrDetail(e.getMessage());
            result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }

        return result;
    }

    /**
     * @param channel   （选填）渠道类型， 根据不同场景选择不同的支付方式，包含：
     *                  WX
     *                  WX_APP 微信手机APP支付
     *                  WX_NATIVE 微信公众号二维码支付
     *                  WX_JSAPI 微信公众号支付
     *                  ALI
     *                  ALI_APP 支付宝APP支付
     *                  ALI_WEB 支付宝网页支付
     *                  ALI_WAP: 支付宝移动网页支付
     *                  ALI_QRCODE 支付宝内嵌二维码支付
     *                  UN
     *                  UN_APP 银联APP支付
     *                  UN_WEB 银联网页支付
     *                  JD
     *                  JD_WAP: 京东移动网页支付
     *                  JD_WEB: 京东PC网页支付
     *                  YEE
     *                  YEE_WAP: 易宝移动网页支付
     *                  YEE_WEB: 易宝PC网页支付
     *                  KUAIQIAN
     *                  KUAIQIAN_WAP: 快钱移动网页支付
     *                  KUAIQIAN_WEB: 快钱PC网页支付
     * @param billNo    （选填） 商户订单号， 32个字符内，数字和/或字母组合，确保在商户系统中唯一
     * @param refundNo  （选填）商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。
     * @param startTime （选填） 起始时间， Date类型
     * @param endTime   （选填） 结束时间， Date类型
     * @param skip      （选填） 查询起始位置	 默认为0。设置为10，表示忽略满足条件的前10条数据
     * @param limit     （选填） 查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据
     * @return BCQueryResult
     */
    public BCQueryResult startQueryRefund(PAY_CHANNEL channel, String billNo, String refundNo, Date startTime, Date endTime, Integer skip, Integer limit) {

        BCQueryResult result;
        result = ValidationUtil.validateQueryRefund(billNo, refundNo, limit);
        if (result.getType().ordinal() != 0) {
            return result;
        }

        result = new BCQueryResult();

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", this.appId);
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", this.getAppSignature(param.get("timestamp").toString()));
        if (channel != null) {
            param.put("channel", channel.toString());
        }
        param.put("bill_no", billNo);
        param.put("refund_no", refundNo);
        if (startTime != null) {
            param.put("start_time", startTime.getTime());
        }
        if (endTime != null) {
            param.put("end_time", endTime.getTime());
        }
        param.put("skip", skip);
        param.put("limit", limit);
        Client client = BCAPIClient.client;

        StringBuilder sb = new StringBuilder();
        sb.append(BCUtilPrivate.getkApiQueryRefund());

        try {
            sb.append(URLEncoder.encode(
                    JSONObject.fromObject(param).toString(), "UTF-8"));

            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                        .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                    result.setType(RESULT_TYPE.OK);
                    if (ret.containsKey("refunds")
                            && ret.get("refunds") != null) {
                        result.setBcRefundList(generateBCRefundList((List<Map<String, Object>>) ret.get("refunds")));
                    }
                } else {
                    result.setErrMsg(ret.get("result_msg").toString());
                    result.setErrDetail(ret.get("err_detail").toString());
                    result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
                result.setErrMsg("Not correct response!");
                result.setErrDetail("Not correct response!");
                result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
            result.setErrMsg("Network error!");
            result.setErrDetail(e.getMessage());
            result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }

        return result;
    }

    /**
     * @param refundNo （必填）商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。
     * @param channel  (必填) 渠道类型， 根据不同场景选择不同的支付方式，包含：
     *                 YEE 易宝
     *                 WX 微信
     *                 KUAIQIAN 快钱
     * @return BCQueryStatusResult
     */
    public BCQueryStatusResult startRefundUpdate(PAY_CHANNEL channel, String refundNo) {

        BCQueryStatusResult result;
        result = ValidationUtil.validateQueryRefundStatus(refundNo);

        if (result.getType().ordinal() != 0) {
            return result;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", this.appId);
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", this.getAppSignature(param.get("timestamp").toString()));
        param.put("channel", channel.toString());
        param.put("refund_no", refundNo);

        result = new BCQueryStatusResult();
        StringBuilder sb = new StringBuilder();
        sb.append(BCUtilPrivate.getkApiRefundUpdate());

        Client client = BCAPIClient.client;

        try {
            sb.append(URLEncoder.encode(
                    JSONObject.fromObject(param).toString(), "UTF-8"));
            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                        .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                    result.setRefundStatus(ret.get("refund_status").toString());
                    result.setType(RESULT_TYPE.OK);
                } else {
                    result.setErrMsg(ret.get("result_msg").toString());
                    result.setErrDetail(ret.get("err_detail").toString());
                    result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
                result.setErrMsg("Not correct response!");
                result.setErrDetail("Not correct response!");
                result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
            result.setErrMsg("Network error!");
            result.setErrDetail(e.getMessage());
            result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
        return result;

    }

    /**
     * @param channel      （必填）渠道类型， 暂时只支持ALI
     * @param batchNo      （必填） 批量付款批号， 此次批量付款的唯一标示，11-32位数字字母组合
     * @param accountName  （必填） 付款方的支付宝账户名, 支付宝账户名称,例如:毛毛
     * @param transferData （必填） 付款的详细数据 {TransferData} 的 List集合。
     * @return BCPayResult
     */
    public BCPayResult startTransfer(PAY_CHANNEL channel, String batchNo, String accountName, List<TransferData> transferData) {
        BCPayResult result;
        result = ValidationUtil.validateBCTransfer(channel, batchNo, accountName, transferData);

        if (result.getType().ordinal() != 0) {
            return result;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", this.appId);
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", this.getAppSignature(param.get("timestamp").toString()));
        param.put("channel", channel.toString());
        param.put("batch_no", batchNo);
        param.put("account_name", accountName);
        List<Map<String, Object>> transferList = new ArrayList<Map<String, Object>>();
        for (TransferData data : transferData) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("transfer_id", data.getTransferId());
            map.put("receiver_account", data.getReceiverAccount());
            map.put("receiver_name", data.getReceiverName());
            map.put("transfer_fee", data.getTransferFee());
            map.put("transfer_note", data.getTransferNote());
            transferList.add(map);
        }
        param.put("transfer_data", transferList);

        result = new BCPayResult();

        Client client = BCAPIClient.client;

        WebTarget target = client.target(BCUtilPrivate.getkApiTransfer());
        try {
            Response response = target.request().post(Entity.entity(param, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                        .toStr(ret.get("result_code")).equals("0"));

                if (isSuccess) {
                    result.setUrl(ret.get("url").toString());
                    result.setType(RESULT_TYPE.OK);

                } else {
                    result.setErrMsg(ret.get("result_msg").toString());
                    result.setErrDetail(ret.get("err_detail").toString());
                    result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
                result.setErrMsg("Not correct response!");
                result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
            result.setErrMsg("Network error!");
            result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
        return result;
    }

    /**
     * @param sign      Webhook提供的签名
     * @param timestamp Webhook提供的timestamp，注意是String格式
     * @return 签名是否正确
     */
    public boolean verifySign(String sign, String timestamp) {
        String mySign = MD5.sign(BCCache.getAppID() + BCCache.getAppSecret(),
                timestamp, "UTF-8");

        if (sign.equals(mySign))
            return true;
        else
            return false;
    }

    /**
     * The method is used to generate Order list by query.
     *
     * @param bills
     * @return list of BCOrderBean
     */
    private List<BCOrderBean> generateBCOrderList(List<Map<String, Object>> bills) {

        List<BCOrderBean> bcOrderList = new ArrayList<BCOrderBean>();
        for (Map bill : bills) {
            BCOrderBean bcOrder = new BCOrderBean();
            bcOrder.setBillNo(bill.get("bill_no").toString());
            bcOrder.setTotalFee(bill.get("total_fee").toString());
            bcOrder.setTitle(bill.get("title").toString());
            bcOrder.setChannel(bill.get("channel").toString());
            bcOrder.setSpayResult(((Boolean) bill.get("spay_result")));
            bcOrder.setCreatedTime((Long) bill.get("created_time"));
            bcOrder.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long) bill.get("created_time")));
            bcOrderList.add(bcOrder);
        }
        return bcOrderList;
    }

    /**
     * The method is used to generate Refund list by query.
     *
     * @param refundList
     * @return list of refund
     */
    private List<BCRefundBean> generateBCRefundList(List<Map<String, Object>> refundList) {

        List<BCRefundBean> bcRefundList = new ArrayList<BCRefundBean>();
        for (Map refund : refundList) {
            BCRefundBean bcRefund = new BCRefundBean();
            bcRefund.setBillNo(refund.get("bill_no").toString());
            bcRefund.setRefundNo(refund.get("refund_no").toString());
            bcRefund.setTotalFee(refund.get("total_fee").toString());
            bcRefund.setRefundFee(refund.get("refund_fee").toString());
            bcRefund.setChannel(refund.get("channel").toString());
            bcRefund.setFinished((Boolean) refund.get("finish"));
            bcRefund.setRefunded((Boolean) refund.get("result"));
            bcRefund.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long) refund.get("created_time")));
            bcRefundList.add(bcRefund);
        }
        return bcRefundList;
    }

    /**
     * Generate a map for JSAPI payment to receive.
     *
     * @param ret
     * @return
     */
    private static Map<String, Object> generateWXJSAPIMap(
            Map<String, Object> ret) {
        HashMap map = new HashMap<String, Object>();
        map.put("appId", ret.get("app_id"));
        map.put("package", ret.get("package"));
        map.put("nonceStr", ret.get("nonce_str"));
        map.put("timeStamp", ret.get("timestamp"));
        map.put("paySign", ret.get("pay_sign"));
        map.put("signType", ret.get("sign_type"));

        return map;
    }

}

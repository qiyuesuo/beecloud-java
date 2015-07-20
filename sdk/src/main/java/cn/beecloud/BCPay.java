/**
 * *************************
 *
 * @Date: Mar 18, 2015
 * @Time: 4:50:02 PM
 * @Author: Junxian Huang
 * <p/>
 * **************************
 */
package cn.beecloud;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.QR_PAY_MODE;
import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.BCOrderBean;
import cn.beecloud.bean.BCRefundBean;
import net.sf.json.JSONObject;

/**
 * This is the core class of BC payment for external invocation consist of start payment, start refund, start query bill
 * start refund query and check wechat refund status functionality.
 * 
 * @author Ray
 * @since 2015/7/11
 */
public class BCPay {

    
	/**
	 * @param channel 
	 * （必填）渠道类型， 根据不同场景选择不同的支付方式，包含：
s	 * 	WX_NATIVE 微信公众号二维码支付
	 * 	WX_JSAPI 微信公众号支付
	 * 	ALI_WEB 支付宝网页支付
	 * 	ALI_QRCODE 支付宝内嵌二维码支付
	 * 	UN_WEB 银联网页支付
	 * @param total_fee 
	 * （必填）订单总金额， 只能为整数，单位为分，例如 1	
	 * @param bill_no 
	 * （必填）商户订单号, 32个字符内，数字和/或字母组合，确保在商户系统中唯一, 例如（201506101035040000001）
	 * @param title 
	 * （必填）订单标题， 32个字节内，最长支持16个汉字	
	 * @param optional
	 * （选填）附加数据， 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据	
	 * @param return_url
	 * （选填）同步返回页面	， 支付渠道处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径。当 channel 参数为 ALI_WEB 或 ALI_QRCODE 或 UN_WEB时为必填
	 * @param openid
	 * （选填）  微信公众号支付(WX_JSAPI)必填
	 * @param show_url
	 * （选填）商品展示地址，需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.
	 * @param qr_pay_mode
	 * （选填）二维码类型，二维码类型含义
		MODE_BRIEF_FRONT： 订单码-简约前置模式, 对应 iframe 宽度不能小于 600px, 高度不能小于 300px
 		MODE_FRONT： 订单码-前置模式, 对应 iframe 宽度不能小于 300px, 高度不能小于 600px
 		MODE_MINI_FRONT： 订单码-迷你前置模式, 对应 iframe 宽度不能小于 75px, 高度不能小于 75px 
	 * @return BCPayResult
	 */
    public static BCPayResult startBCPay(PAY_CHANNEL channel, int total_fee,
                    String bill_no, String title,
                    Map<String, String> optional, String return_url, String openid, String show_url, QR_PAY_MODE qr_pay_mode) {
    	
    	BCPayResult result;
    	result = ValidationUtil.validateBCPay(channel, bill_no, title, return_url, openid);
    	
    	if (result.getType().ordinal()!=0) {
    		return result;
    	}
    	
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
        param.put("channel", channel.toString());
        param.put("total_fee", total_fee);
        param.put("bill_no", bill_no);
        param.put("title", title);
        if (optional != null && optional.size() > 0)
            param.put("optional", optional);
        if (!StrUtil.empty(return_url))
        	param.put("return_url", return_url);
        if (!StrUtil.empty(openid))
        	param.put("openid", openid);
        if (!StrUtil.empty(show_url))
        	param.put("show_url", show_url);
        if (qr_pay_mode != null)
        	param.put("qr_pay_mode", qr_pay_mode.ordinal() == 2 ? qr_pay_mode.ordinal() +1:qr_pay_mode.ordinal());
        
        result = new BCPayResult();
        
        Client client = BCAPIClient.client;
        WebTarget target = client.target("http://58.211.191.123:8080/1/rest/bill");
//        WebTarget target = client.target("http://192.168.1.112:8080/1/rest/bill");
        try {
            Response response = target.request().post(Entity.entity(param, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);

                boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                .toStr(ret.get("result_code")).equals("0"));
                if (isSuccess) {
                	if (channel.equals(PAY_CHANNEL.WX_NATIVE)){
	                    if (ret.containsKey("code_url") && null != ret.get("code_url")) {
	                        result.setCode_url(ret.get("code_url").toString());
	                        result.setType(RESULT_TYPE.OK);
	                    } 
                	} else if (channel.equals(PAY_CHANNEL.WX_JSAPI)) {
                    	result.setType(RESULT_TYPE.OK);
                    	result.setWxJSAPIMap(generateWXJSAPIMap(ret));
                    } else if (channel.equals(PAY_CHANNEL.ALI_WEB) || channel.equals(PAY_CHANNEL.ALI_QRCODE)) {
                		if (ret.containsKey("html") && null != ret.get("html") && 
                				ret.containsKey("url") && null != ret.get("url")) {
	                        result.setHtml(ret.get("html").toString());
	                        result.setUrl(ret.get("url").toString());
	                        result.setType(RESULT_TYPE.OK);
	                    }
                	} else if (channel.equals(PAY_CHANNEL.UN_WEB)) {
                		if (ret.containsKey("html") && null != ret.get("html")) {
	                        result.setHtml(ret.get("html").toString());
	                        result.setType(RESULT_TYPE.OK);
	                    }
                	}
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErr_detail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErr_detail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErr_detail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
        return result;
    }
    
	/**
     * @param channel
     * （必填）渠道类型， 根据不同场景选择不同的支付方式，包含：
	 * 	WX  微信
	 * 	ALI 支付宝
	 * 	UN 银联
     * @param refund_no
     * （必填）商户退款单号	， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。
     * 例如：201506101035040000001	
     * @param bill_no
     * （必填）商户订单号， 32个字符内，数字和/或字母组合，确保在商户系统中唯一	
     * @param refund_fee
     * （必填）退款金额， 只能为整数，单位为分，例如1	
     * @param optional
     * （选填）附加数据 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据，例如{"key1":"value1","key2":"value2",...}	
     * @return BCPayResult
     */
    public static BCPayResult startBCRefund(PAY_CHANNEL channel, String refund_no, String bill_no, int refund_fee, Map optional) {
    	 
    	BCPayResult result;
    	result = ValidationUtil.validateBCRefund(channel, refund_no, bill_no);
    	
    	if (result.getType().ordinal()!=0) {
    		return result;
    	}
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("app_id", BCCache.getAppID());
    	param.put("timestamp", System.currentTimeMillis());
    	param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
    	param.put("channel", channel.toString());
    	param.put("refund_no", refund_no);
    	param.put("bill_no", bill_no);
    	param.put("refund_fee", refund_fee);
    	if (optional != null && optional.size() > 0)
    		param.put("optional", optional);
         
         	result = new BCPayResult();
         
         	Client client = BCAPIClient.client;
//         	WebTarget target = client.target(BCUtilPrivate.getkApiRefund());
//         	WebTarget target = client.target("http://192.168.1.112:8080/1/rest/refund");
         	WebTarget target = client.target("http://58.211.191.123:8080/1/rest/refund");
        try {
             Response response = target.request().post(Entity.entity(param, MediaType.APPLICATION_JSON));
             if (response.getStatus() == 200) {
                 Map<String, Object> ret = response.readEntity(Map.class);

                 boolean isSuccess = (ret.containsKey("result_code") && StrUtil
                                 .toStr(ret.get("result_code")).equals("0"));

                 if (isSuccess) {
             		if (channel.equals(PAY_CHANNEL.ALI)) {
            			result.setUrl(ret.get("url").toString());
            			result.setType(RESULT_TYPE.OK);
            		} else if (channel.equals(PAY_CHANNEL.UN)) {
            			result.setSucessMsg(ret.get("respMsg").toString());
            			result.setType(RESULT_TYPE.OK);
            		} else {
            			result.setSucessMsg(ValidationUtil.REFUND_ACCEPT);
            			result.setType(RESULT_TYPE.OK);
            		}
                 } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                 	result.setErr_detail(ret.get("err_detail").toString());
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
     * @param channel
     * （必填）渠道类型， 根据不同场景选择不同的支付方式，包含：
     *  WX
     * 	WX_APP 微信手机APP支付
	 * 	WX_NATIVE 微信公众号二维码支付
	 * 	WX_JSAPI 微信公众号支付
	 *  ALI
	 * 	ALI_APP 支付宝APP支付
	 * 	ALI_WEB 支付宝网页支付
	 * 	ALI_QRCODE 支付宝内嵌二维码支付
	 * 	UN
	 * 	UN_APP 银联APP支付
	 * 	UN_WEB 银联网页支付
     * @param bill_no
     * （选填） 商户订单号， 32个字符内，数字和/或字母组合，确保在商户系统中唯一
     * @param start_time 
     * （选填） 起始时间， 毫秒时间戳, 13位
     * @param end_time
     * （选填） 结束时间， 毫秒时间戳, 13位	
     * @param skip
     * （选填） 查询起始位置	 默认为0。设置为10，表示忽略满足条件的前10条数据	
     * @param limit
     * （选填） 查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据	
     * @return BCQueryResult
     */
    public static BCQueryResult startQueryBill(PAY_CHANNEL channel, String bill_no, Long start_time, Long end_time, Integer skip, Integer limit) {
    	
    	BCQueryResult result;
    	
    	result = ValidationUtil.validateQueryBill(channel, bill_no, start_time, end_time, limit);
    	
    	if (result.getType().ordinal() != 0) {
    		return result;
    	}
    	 
    	 Map<String, Object> param = new HashMap<String, Object>();
         param.put("app_id", BCCache.getAppID());
         param.put("timestamp", System.currentTimeMillis());
         param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
         param.put("channel", channel.toString());
         param.put("bill_no", bill_no);
         param.put("start_time", start_time);
         param.put("end_time", end_time);
         param.put("skip", skip);
         param.put("limit", limit);
         
         result = new BCQueryResult();
    	
    	Client client = BCAPIClient.client;
    	  
    	StringBuilder sb = new StringBuilder();   
        sb.append("http://58.211.191.123:8080/1/rest/bills?para=");
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
                        result.setBcOrders(generateBCOrderList((List<Map<String, Object>>)ret.get("bills")));
                    }
                } else {
                	result.setErrMsg(ret.get("result_msg").toString());
                	result.setErr_detail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErr_detail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErr_detail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
    	
    	return result;
    }
    
    /**
     * @param channel
     * （必填）渠道类型， 根据不同场景选择不同的支付方式，包含：
     *  WX_APP 微信手机APP支付
	 * 	WX_NATIVE 微信公众号二维码支付
	 * 	WX_JSAPI 微信公众号支付
	 * 	ALI_APP 支付宝APP支付
	 * 	ALI_WEB 支付宝网页支付
	 * 	ALI_QRCODE 支付宝内嵌二维码支付
	 * 	UN_APP 银联APP支付
	 * 	UN_WEB 银联网页支付
     * @param refund_no
     * （DIRECT_REFUND和PRE_REFUND时必填）退款金额， 只能为整数，单位为分，例如1	
     * @param buyer_id
     * （必填）消费者ID， 消费者在商户系统内的唯一标识， 32个字节以内
     * @param start_time
     * （选填） 起始时间， 毫秒时间戳, 13位
     * @param end_time
     * （选填） 结束时间， 毫秒时间戳, 13位	
     * @param skip
     * （选填） 查询起始位置	 默认为0。设置为10，表示忽略满足条件的前10条数据	
     * @param limit
     * （选填） 查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据	
     * @return BCQueryResult
     */
    public static BCQueryResult startQueryRefund(PAY_CHANNEL channel, String bill_no, String refund_no, Long start_time, Long end_time, Integer skip, Integer limit) {
    	
    	BCQueryResult result;
    	result = ValidationUtil.validateQueryRefund(channel, bill_no, refund_no, start_time, end_time, limit);
		if (result.getType().ordinal() != 0) {
			return result;
		}
		
		result = new BCQueryResult();
		
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
        param.put("channel", channel.toString());
        param.put("bill_no", bill_no);
        param.put("refund_no", refund_no);
        param.put("start_time", start_time);
        param.put("end_time", end_time);
        param.put("skip", skip);
        param.put("limit", limit);
	    Client client = BCAPIClient.client;
     	
     	StringBuilder sb = new StringBuilder();
        sb.append("http://58.211.191.123:8080/1/rest/refunds?para=");
//     	sb.append("http://192.168.1.112:8080/1/rest/refund/query?para=");
         
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
                         result.setBcRefundList(generateBCRefundList((List<Map<String, Object>>)ret.get("refunds")));
                     }
                 } else {
                 	result.setErrMsg(ret.get("result_msg").toString());
                 	result.setErr_detail(ret.get("err_detail").toString());
                 	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                 }
             } else {
             	result.setErrMsg("Not correct response!");
             	result.setErr_detail("Not correct response!");
             	result.setType(RESULT_TYPE.RUNTIME_ERROR);
             }
         } catch (Exception e) {
         	result.setErrMsg("Network error!");
         	result.setErr_detail(e.getMessage());
         	result.setType(RESULT_TYPE.RUNTIME_ERROR);
         }
     	
     	return result;
    }
    
    /**
     * @param refund_no
     * （必填）商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。	
     * @return BCQueryStatusResult
     */
    public static BCQueryStatusResult startWeChatRefundStatusQuery(String refund_no) {

    	BCQueryStatusResult result;
    	result = ValidationUtil.validateQueryRefundStatus(refund_no);
    	
		if (result.getType().ordinal() != 0) {
			return result;
		}
    	
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
        param.put("channel", "WX");
        param.put("refund_no", refund_no);
        
        result = new BCQueryStatusResult();
        StringBuilder sb = new StringBuilder();   
        sb.append("http://58.211.191.123:8080/1/rest/refund/status?para=");
//        sb.append("http://192.168.1.101:8080/1/rest/refund/status?para=");
        
        
        Client client = BCAPIClient.client;
        
//        WebTarget target = client.target("http://192.168.1.101:8080/1/rest/pay");
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
                	result.setErr_detail(ret.get("err_detail").toString());
                	result.setType(RESULT_TYPE.RUNTIME_ERROR);
                }
            } else {
            	result.setErrMsg("Not correct response!");
            	result.setErr_detail("Not correct response!");
            	result.setType(RESULT_TYPE.RUNTIME_ERROR);
            }
        } catch (Exception e) {
        	result.setErrMsg("Network error!");
        	result.setErr_detail(e.getMessage());
        	result.setType(RESULT_TYPE.RUNTIME_ERROR);
        }
        return result;
    	
    }
    
    
    
    /**
     * The method is used to generate Order list by query.
     * @param bills
     * @return list of BCOrderBean
     */
    private static List<BCOrderBean> generateBCOrderList(List<Map<String, Object>> bills) {
			
		List<BCOrderBean> bcOrderList = new ArrayList<BCOrderBean>();
		for (Map bill : bills){
			BCOrderBean bcOrder = new BCOrderBean();
			bcOrder.setBill_no(bill.get("bill_no").toString());
			bcOrder.setTotal_fee(bill.get("total_fee").toString());
			bcOrder.setTitle(bill.get("title").toString());
			bcOrder.setChannel(bill.get("channel").toString());
			bcOrder.setSpay_result((Boolean)bill.get("spay_result"));
			bcOrder.setCreated_time((Long)bill.get("created_time"));
			bcOrder.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long)bill.get("created_time")));
			bcOrderList.add(bcOrder);
		}
		return bcOrderList;
	}
    
    /**
     * The method is used to generate Refund list by query.
     * @param refundList
     * @return list of refund
     */
    private static List<BCRefundBean> generateBCRefundList(List<Map<String, Object>> refundList) {
    	
    	List<BCRefundBean> bcRefundList = new ArrayList<BCRefundBean>();
		for (Map refund : refundList){
			BCRefundBean bcRefund = new BCRefundBean();
			bcRefund.setBill_no(refund.get("bill_no").toString());
			bcRefund.setRefund_no(refund.get("refund_no").toString());
			bcRefund.setTotal_fee(refund.get("total_fee").toString());
			bcRefund.setRefund_fee(refund.get("refund_fee").toString());
			bcRefund.setChannel(refund.get("channel").toString());
			bcRefund.setFinished((Boolean)refund.get("finish"));
			bcRefund.setRefunded((Boolean)refund.get("result"));
			bcRefund.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long)refund.get("created_time")));
			bcRefundList.add(bcRefund);
		}
		return bcRefundList;
    }
    
    /**
     * Generate a map for JSAPI payment to receive.
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

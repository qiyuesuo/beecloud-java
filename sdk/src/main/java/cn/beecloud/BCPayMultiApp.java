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
import cn.beecloud.bean.BCBatchRefund;
import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCOrder;
import cn.beecloud.bean.BCQueryParameter;
import cn.beecloud.bean.BCRefund;
import cn.beecloud.bean.BCRefundParameter;
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
	
private final static String NOT_REGISTER = "为注册";
	
	private final static String NOT_CORRECT_RESPONSE = "响应不正确";
	
	private final static String NETWORK_ERROR = "网络错误";


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
	 * @param para {@link BCOrder}支付参数
	 * (必填)
	 * @return 调起比可支付后的返回结果
	 * @throws BCException 
	 */
    public BCOrder startBCPay(BCOrder order) throws BCException {
    	
    	ValidationUtil.validateBCPay(order);
    	
        Map<String, Object> param = new HashMap<String, Object>();
        
        buildPayParam(param, order);
        
        Map<String, Object> ret = doPost(BCUtilPrivate.getkApiPay(), param);
        
        placeOrder(order, ret);
        
        return order;
    }
	
	/**
	 * @param para {@link BCRefund}退款参数
	 * @return 发起退款的返回结果
	 * @throws BCException 
	 */
    public BCRefund startBCRefund(BCRefund refund) throws BCException {
    	 
    	ValidationUtil.validateBCRefund(refund);
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	buildRefundParam(refund, param);
    	
    	Map<String, Object> ret = doPost(BCUtilPrivate.getkApiRefund(), param);
        
    	refund.setObjectId(ret.get("id").toString());
    	if (ret.containsKey("url")) {
    		refund.setAliRefundUrl(ret.get("url").toString());
		} 
    	
    	return refund;
    }


    /**
     * @param para {@link BCQueryParameter}订单查询参数
     * @return 订单查询返回的结果
     * @throws BCException 
     */
    @SuppressWarnings("unchecked")
	public List<BCOrder> startQueryBill(BCQueryParameter para) throws BCException {
    	
    	ValidationUtil.validateQueryBill(para);
    	 
    	Map<String, Object> param = new HashMap<String, Object>();
        buildQueryParam(param, para);
        
        Map<String, Object> ret = doGet(BCUtilPrivate.getkApiQueryBill(), param);
        
        return generateBCOrderList((List<Map<String, Object>>)ret.get("bills"));
        
    }
    
    /**
     * Bill Query by Id.
     * @param objectId the id to query by.
     * @return BCOrder
     * @throws BCException 
     */
    public BCOrder startQueryBillById(String objectId) throws BCException {
    	
		 Map<String, Object> param = new HashMap<String, Object>();
	     param.put("app_id", BCCache.getAppID());
	     param.put("timestamp", System.currentTimeMillis());
	     param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
         
	     StringBuilder urlSb = new StringBuilder();
	     urlSb.append(BCUtilPrivate.getkApiQueryBillById());
	     urlSb.append("/");
	     urlSb.append(objectId);
	     urlSb.append("?para=");
	     Map<String, Object> ret = doGet(urlSb.toString(), param);
	     
         return generateBCOrder((Map<String, Object>)ret.get("pay"));
    }
    
    /**
     * @param para {@link BCQueryParameter}订单总数查询参数
     * @return 订单总数查询返回的结果
     * @throws BCException 
     */
    public Integer startQueryBillCount(BCQueryParameter para) throws BCException {
    	
    	ValidationUtil.validateQueryBill(para);
    	
    	Map<String, Object> param = new HashMap<String, Object>();
        buildQueryCountParam(param, para);
         
        Map<String, Object> ret = doGet(BCUtilPrivate.getkApiQueryBillCount(), param);
    	
        return (Integer)ret.get("count");
    }
    
	/**
	 * @param para {@link BCQueryParameter}}
	 * @return 退款查询返回的结果
	 * @throws BCException 
	 */
    public List<BCRefund> startQueryRefund(BCQueryParameter para) throws BCException {
    	
    	ValidationUtil.validateQueryRefund(para);
		
		Map<String, Object> param = new HashMap<String, Object>();
		
        buildQueryParam(param, para);
        
        Map<String, Object> ret = doGet(BCUtilPrivate.getkApiQueryRefund(), param);
     	
        return generateBCRefundList((List<Map<String, Object>>)ret.get("refunds"));
    }
    
    /**
     * Bill Query by Id.
     * @param objectId the id to query by.
     * @return BCRefund
     * @throws BCException 
     */
    public BCRefund startQueryRefundById(String objectId) throws BCException {
    	
		 Map<String, Object> param = new HashMap<String, Object>();
	     param.put("app_id", BCCache.getAppID());
	     param.put("timestamp", System.currentTimeMillis());
	     param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
         
	     StringBuilder urlSb = new StringBuilder();
	     urlSb.append(BCUtilPrivate.getkApiQueryRefundById());
	     urlSb.append("/");
	     urlSb.append(objectId);
	     urlSb.append("?para=");
	     Map<String, Object> ret = doGet(urlSb.toString(), param);
	     
	     return generateBCRefund((Map<String, Object>)ret.get("refund"));
	     
    }
    
    /**
     * @param para {@link BCQueryParameter}退款总数查询参数
     * @return 退款总数查询返回的结果
     * @throws BCException 
     */
    public Integer startQueryRefundCount(BCQueryParameter para) throws BCException {
    	
    	
    	ValidationUtil.validateQueryRefund(para);
    	
    	Map<String, Object> param = new HashMap<String, Object>();
        buildQueryCountParam(param, para);
         
        Map<String, Object> ret = doGet(BCUtilPrivate.getkApiQueryBillCount(), param);
    	
        return (Integer)ret.get("count");
    }
    
    /**
     * @param refundNo
     * （必填）商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。	
     * @param channel
     * (必填) 渠道类型， 根据不同场景选择不同的支付方式，包含：
     *  YEE 易宝
	 *  WX 微信
	 *  KUAIQIAN 快钱
	 *  BD 百度
     * @return String
     * @throws BCException 
     */
    public String startRefundUpdate(PAY_CHANNEL channel, String refundNo) throws BCException {

    	ValidationUtil.validateQueryRefundStatus(channel, refundNo);
    	
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("app_id", BCCache.getAppID());
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", BCUtilPrivate.getAppSignature(param.get("timestamp").toString()));
        param.put("channel", channel.toString());
        param.put("refund_no", refundNo);
        
        Map<String, Object> ret = doGet(BCUtilPrivate.getkApiRefundUpdate(), param);
        return ret.get("refund_status").toString();
    }
    
    /**
     * @param channel
     * （必填）渠道类型， 暂时只支持ALI 
     * @param batchNo 
     * （必填） 批量付款批号， 此次批量付款的唯一标示，11-32位数字字母组合
     * @param accountName
     * （必填） 付款方的支付宝账户名, 支付宝账户名称,例如:毛毛
     * @param transferData
     * （必填） 付款的详细数据 {TransferData} 的 List集合。
     * @return BCPayResult
     * @throws BCException 
     */
    public String startTransfer(PAY_CHANNEL channel, String batchNo, String accountName, List<TransferData> transferData) throws BCException {
    
    	ValidationUtil.validateBCTransfer(channel, batchNo, accountName, transferData);
    	
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
        
    	Map<String, Object> ret = doPost(BCUtilPrivate.getkApiTransfer(), param);
    	
     	return ret.get("url").toString();
    }
    
    /**
     * 发起预退款审核，包括批量否决和批量同意
     * @param batchRefund
     * （必填） 批量退款参数
     * @return BCBatchRefund
     * @throws BCException 
     */
    public BCBatchRefund startBatchRefund(BCBatchRefund batchRefund) throws BCException {
    	
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("channel", batchRefund.getChannel().toString());
        param.put("agree", batchRefund.getAgree());
        param.put("ids", batchRefund.getIds());
        param.put("app_id", this.appId);
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", this.getAppSignature(param.get("timestamp").toString()));
        
        
        Map<String, Object> ret = doPut(BCUtilPrivate.getApiBatchRefund(), param);
        
        if (ret.containsKey("result_map")) {
        	batchRefund.setIdResult((Map<String, String>)ret.get("result_map"));
        	if (ret.containsKey("url")) {
            	batchRefund.setAliRefundUrl(ret.get("url").toString());
    		} 
        }
        
        return batchRefund;
    }
    
    /**
     * @param sign
     *            Webhook提供的签名
     * @param timestamp
     *            Webhook提供的timestamp，注意是String格式
     * @return 签名是否正确
     */
    public static boolean verifySign(String sign, String timestamp) {
        String mySign = MD5.sign(BCCache.getAppID() + BCCache.getAppSecret(),
                        timestamp, "UTF-8");
        
        if (sign.equals(mySign))
            return true;
        else
            return false;
    }
    
    /**
     * Build Payment parameters
     * @param param to be built
     * @param para used for building 
     */
    private void buildPayParam(Map<String, Object> param,
			BCOrder para) {
    	
    	param.put("app_id", this.appId);
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", this.getAppSignature(param.get("timestamp").toString()));
        param.put("channel", para.getChannel().toString());
        param.put("total_fee", para.getTotalFee());
        param.put("bill_no", para.getBillNo());
        param.put("title", para.getTitle());
        
		if (para.getReturnUrl() != null) {
			param.put("return_url", para.getReturnUrl());
		} 
		if (para.getOptional() != null && para.getOptional().size() > 0) {
			param.put("optional", para.getOptional());
		}
		if (para.getOpenId() != null) {
			param.put("openid", para.getOpenId());
		}
		if (para.getShowUrl() != null) {
			param.put("show_url", para.getShowUrl());
		}
		if (para.getQrPayMode() != null) {
			if (para.getQrPayMode().ordinal() == 2) {
        		param.put("qr_pay_mode", String.valueOf(para.getQrPayMode().ordinal() +1));
        	} else {
        		param.put("qr_pay_mode", String.valueOf(para.getQrPayMode().ordinal()));
        	}
		}
		if (para.getBillTimeout() != null) {
        	param.put("bill_timeout", para.getBillTimeout());
        }
        if (para.getChannel().equals(PAY_CHANNEL.YEE_NOBANKCARD)) {
        	param.put("cardno", para.getCardNo());
        	param.put("cardpwd", para.getCardPwd());
        	param.put("frqid", para.getFrqid());
        }
	}
    
    /**
     * Build Refund parameters
     * @param param to be built
     * @param para used for building 
     */
    private void buildRefundParam(BCRefund para,
			Map<String, Object> param) {
		
    	param.put("app_id", this.appId);
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", this.getAppSignature(param.get("timestamp").toString()));
    	param.put("refund_no", para.getRefundNo());
    	param.put("bill_no", para.getBillNo());
    	param.put("refund_fee", para.getRefundFee());
    	
		if (para.getChannel() != null) {
    		param.put("channel", para.getChannel().toString());
    	}
    	if (para.isNeedApproval() != null) {
    		param.put("need_approval", para.isNeedApproval());
    	}
    	if (para.getOptional() != null && para.getOptional().size() > 0)
    		param.put("optional", para.getOptional());
	}
    
    /**
     * Build Query parameters
     * @param param to be built
     * @param para used for building 
     */
	private void buildQueryParam(Map<String, Object> param,
			BCQueryParameter para) {
		param.put("app_id", this.appId);
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", this.getAppSignature(param.get("timestamp").toString()));
        if (para.getChannel() != null) {
    		param.put("channel", para.getChannel().toString());
    	}
        if (para.getBillNo() != null) {
        	param.put("bill_no", para.getBillNo());
        }
        if (para.getRefundNo() != null) {
        	param.put("refund_no", para.getRefundNo());
        }
        if (para.getSkip() != null) {
        	param.put("skip", para.getSkip());
        }
        if (para.getLimit() != null) {
        	param.put("limit", para.getLimit());
        }
        if (para.getStartTime() != null) {
        	param.put("start_time", para.getStartTime().getTime());
        }
        if (para.getEndTime() != null) {
       	 	param.put("end_time", para.getEndTime().getTime());
        }
        if (para.getNeedDetail() != null && para.getNeedDetail()) {
        	param.put("need_detail", para.getNeedDetail());
        }
        if (para.getNeedApproval() != null && para.getNeedApproval()) {
        	param.put("need_approval", para.getNeedApproval());
        }
	}
    
	/**
     * Build Query Count parameters
     * @param param to be built
     * @param para used for building 
     */
	private void buildQueryCountParam(Map<String, Object> param,
			BCQueryParameter para) {
		param.put("app_id", this.appId);
        param.put("timestamp", System.currentTimeMillis());
        param.put("app_sign", this.getAppSignature(param.get("timestamp").toString()));
        if (para.getChannel() != null) {
    		param.put("channel", para.getChannel().toString());
    	}
        if (para.getBillNo() != null) {
        	param.put("bill_no", para.getBillNo());
        }
        if (para.getRefundNo() != null) {
        	param.put("refund_no", para.getRefundNo());
        }
        if (para.getStartTime() != null) {
        	param.put("start_time", para.getStartTime().getTime());
        }
        if (para.getEndTime() != null) {
       	 param.put("end_time", para.getEndTime().getTime());
        }
	}
	
    /**
     * The method is used to generate Order list by query.
     * @param bills
     * @return list of BCOrderBean
     */
	private static List<BCOrder> generateBCOrderList(List<Map<String, Object>> bills) {
		
		List<BCOrder> bcOrderList = new ArrayList<BCOrder>();
		for (Map<String, Object> bill : bills){
			BCOrder bcOrder = new BCOrder();
			generateBCOrderBean(bill, bcOrder);
			bcOrderList.add(bcOrder);
		}
		return bcOrderList;
	}
    
	/**
     * The method is used to generate an order object.
     * @param bill
     * @return an object of BCOrderBean
     */
	private static BCOrder generateBCOrder(Map<String, Object> bill) {
		BCOrder bcOrder = new BCOrder();
		generateBCOrderBean(bill, bcOrder);
		return bcOrder;
	}
	
    /**
     * The method is used to generate Refund list by query.
     * @param refundList the list of refund taken in
     * @return list of BCRefundBean object
     */
    private static List<BCRefund> generateBCRefundList(List<Map<String, Object>> refundList) {
    	
    	List<BCRefund> bcRefundList = new ArrayList<BCRefund>();
		for (Map<String, Object> refund : refundList){
			BCRefund bcRefund = new BCRefund();
			generateBCRefundBean(refund, bcRefund);
	    	bcRefundList.add(bcRefund);
		}
		return bcRefundList;
    }
    
    /**
     * The method is used to generate a refund object.
     * @param refund the refund map taken in
     * @return list of BCRefundBean object
     */
    private static BCRefund generateBCRefund(Map<String, Object> refund) {
    	BCRefund bcRefund = new BCRefund();
    	generateBCRefundBean(refund, bcRefund);
    	return bcRefund;
    }
    
    /**
     * Generate order bean from order map
     * @param bill the map taken in
     */
    private static void generateBCOrderBean(Map<String, Object> bill,
			BCOrder bcOrder) {
    	bcOrder.setObjectId(bill.get("id").toString());
		bcOrder.setBillNo(bill.get("bill_no").toString());
		bcOrder.setTotalFee((Integer)bill.get("total_fee"));
		bcOrder.setTitle(bill.get("title").toString());
		bcOrder.setChannel(PAY_CHANNEL.valueOf(bill.get("sub_channel").toString()));
		bcOrder.setResulted(((Boolean)bill.get("spay_result")));
		if (bill.containsKey("trade_no") && bill.get("trade_no") != null) {
			bcOrder.setChannelTradeNo(bill.get("trade_no").toString());
		}
		bcOrder.setOptionalString((bill.get("optional").toString()));
		bcOrder.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long)bill.get("create_time")));
		if (bill.containsKey("message_detail")) {
			bcOrder.setMessageDetail(bill.get("message_detail").toString());
		}
		bcOrder.setRefundResult((Boolean)bill.get("refund_result"));
		bcOrder.setRevertResult((Boolean)bill.get("revert_result"));
	}
    
    /**
     * Generate refund bean from refund map
     * @param refund the map taken in
     */
	private static void generateBCRefundBean(Map<String, Object> refund,
			BCRefund bcRefund) {
		bcRefund.setObjectId(refund.get("id").toString());
		bcRefund.setBillNo(refund.get("bill_no").toString());
		bcRefund.setChannel(PAY_CHANNEL.valueOf(refund.get("sub_channel").toString()));
		bcRefund.setFinished((Boolean)refund.get("finish"));
		bcRefund.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long)refund.get("create_time")));
		bcRefund.setOptionalString(refund.get("optional").toString());
		bcRefund.setRefunded((Boolean)refund.get("result"));
		bcRefund.setTitle(refund.get("title").toString());
		bcRefund.setTotalFee(refund.get("total_fee").toString());
		bcRefund.setRefundFee((Integer)refund.get("refund_fee"));
		bcRefund.setRefundNo(refund.get("refund_no").toString());
		bcRefund.setDateTime(BCUtilPrivate.transferDateFromLongToString((Long)refund.get("create_time")));
		if (refund.containsKey("message_detail")) {
			bcRefund.setMessageDetail(refund.get("message_detail").toString());
		}
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
    
    public static Map<String, Object> doPost(String url,
			Map<String, Object> param) throws BCException {
        Client client = BCAPIClient.client;
        if (client == null) {
        	throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), NOT_REGISTER);
        }
        WebTarget target = client.target(url);
        try {
            Response response = target.request().post(Entity.entity(param, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);
                
                Integer resultCode = (Integer)ret.get("result_code");
                String resultMessage = ret.get("result_msg").toString();
                String errorDetail = ret.get("err_detail").toString();

                boolean isSuccess = (resultCode == 0);
                if (isSuccess) {
                	return ret;
                } else {
                	throw new BCException(resultCode, resultMessage, errorDetail);
                }
            } else {
            	throw new BCException(-1, RESULT_TYPE.NOT_CORRECT_RESPONSE.name(), NOT_CORRECT_RESPONSE);
            }
        } catch (Exception e) {
        	throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), NETWORK_ERROR);
        }
	}
    
    public static Map<String, Object> doPut(String url,
			Map<String, Object> param) throws BCException {
        Client client = BCAPIClient.client;
        if (client == null) {
        	throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), NOT_REGISTER);
        }
        WebTarget target = client.target(url);
        try {
            Response response = target.request().put(Entity.entity(param, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);
                
                Integer resultCode = (Integer)ret.get("result_code");
                String resultMessage = ret.get("result_msg").toString();
                String errorDetail = ret.get("err_detail").toString();

                boolean isSuccess = (resultCode == 0);
                if (isSuccess) {
                	return ret;
                } else {
                	throw new BCException(resultCode, resultMessage, errorDetail);
                }
            } else {
            	throw new BCException(-1, RESULT_TYPE.NOT_CORRECT_RESPONSE.name(), NOT_CORRECT_RESPONSE);
            }
        } catch (Exception e) {
        	throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), NETWORK_ERROR);
        }
	}
    
    public static Map<String, Object> doGet(String url,
			Map<String, Object> param) throws BCException {
        Client client = BCAPIClient.client;
        if (client == null) {
        	throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), NOT_REGISTER);
        }
        
    	StringBuilder sb = new StringBuilder();   
        
        try {
        	sb.append(URLEncoder.encode(url, "UTF-8"));
            sb.append(URLEncoder.encode(
                            JSONObject.fromObject(param).toString(), "UTF-8"));

            WebTarget target = client.target(sb.toString());
            Response response = target.request().get();
            if (response.getStatus() == 200) {
                Map<String, Object> ret = response.readEntity(Map.class);
                
                Integer resultCode = (Integer)ret.get("result_code");
                String resultMessage = ret.get("result_msg").toString();
                String errorDetail = ret.get("err_detail").toString();

                boolean isSuccess = (resultCode == 0);

                if (isSuccess) {
                    return ret;
                } else {
                	throw new BCException(resultCode, resultMessage, errorDetail);
                }
            } else {
            	throw new BCException(-1, RESULT_TYPE.NOT_CORRECT_RESPONSE.name(), NOT_CORRECT_RESPONSE);
            }
        } catch (Exception e) {
        	throw new BCException(-2, RESULT_TYPE.OTHER_ERROR.name(), NETWORK_ERROR);
        }
	}
    
    private static void placeOrder(BCOrder order, Map<String, Object> ret) {
    	order.setObjectId(ret.get("id").toString());
    	if (order.getChannel().equals(PAY_CHANNEL.WX_NATIVE)){
            if (ret.containsKey("code_url") && null != ret.get("code_url")) {
            	order.setCodeUrl(ret.get("code_url").toString());
            } 
    	} else if (order.getChannel().equals(PAY_CHANNEL.WX_JSAPI)) {
    		order.setWxJSAPIMap(generateWXJSAPIMap(ret));
        } else if (order.getChannel().equals(PAY_CHANNEL.ALI_WEB) || order.getChannel().equals(PAY_CHANNEL.ALI_QRCODE) || order.getChannel().equals(PAY_CHANNEL.ALI_WAP)) {
    		if (ret.containsKey("html") && null != ret.get("html") && 
    				ret.containsKey("url") && null != ret.get("url")) {
    			order.setHtml(ret.get("html").toString());
    			order.setUrl(ret.get("url").toString());
            }
    	} else if (order.getChannel().equals(PAY_CHANNEL.UN_WEB) || order.getChannel().equals(PAY_CHANNEL.JD_WAP)
    			|| order.getChannel().equals(PAY_CHANNEL.JD_WEB) || order.getChannel().equals(PAY_CHANNEL.KUAIQIAN_WAP) 
    			|| order.getChannel().equals(PAY_CHANNEL.KUAIQIAN_WEB)) {
    		if (ret.containsKey("html") && null != ret.get("html")) {
    			order.setHtml(ret.get("html").toString());
            }
    	} else if (order.getChannel().equals(PAY_CHANNEL.YEE_WAP) || order.getChannel().equals(PAY_CHANNEL.YEE_WEB) || 
    			order.getChannel().equals(PAY_CHANNEL.BD_WEB) || 
    			order.getChannel().equals(PAY_CHANNEL.BD_WAP) ) {
    		if (ret.containsKey("url") && null != ret.get("url")) {
    			order.setUrl(ret.get("url").toString());
            }
    	}
    }
}

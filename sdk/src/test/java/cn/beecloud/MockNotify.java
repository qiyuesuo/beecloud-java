package cn.beecloud;

import static junit.framework.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cn.beecloud.constant.Constant;
import cn.beecloud.constant.TestConstant;
import cn.beecloud.util.CommonUtil;
import cn.beecloudv1.TestUtil;

/**
 * Created by zheyu on 7/7/15.
 */
public class MockNotify {

    public static void pay(Client client, String billNo, String channel){
        String objectId = TestUtil.getObjectId(client, TestConstant.KTestAppID, TestConstant.kTestAppSecret,
                "bill__", billNo);

        TestConstant.CHANNEL_TYPE channel_type = TestConstant.CHANNEL_TYPE.valueOf(channel);
        switch (channel_type) {
            case ALI_QRCODE:
            case ALI_APP:
            case ALI_WEB:
                MockNotify.aliPay(client, billNo, objectId);
                break;
//            case UN_APP:
//                MockNotify.unbPay(client, billNo, TestConstant.UN_PAY_NOTIFY, objectId);
//                break;
//            case UN_WEB:
//                MockNotify.unbPay(client, billNo, TestConstant.UNWEB_PAY_NOTIFY, objectId);
//                break;
//            case WX_APP:
//                MockNotify.wxPay(client, billNo, "APP", TestConstant.TEST_APP_WXMP_KEY, TestConstant.WXAPP_PAY_NOTIFY, objectId);
//                break;
            case WX_NATIVE:
                MockNotify.wxPay(client, billNo, "NATIVE", TestConstant.TEST_WXMP_KEY, TestConstant.WXMP_PAY_NOTIFY, objectId);
                break;
//            case WX_JSAPI:
//                MockNotify.wxPay(client, billNo, "JSAPI", TestConstant.TEST_WXMP_KEY, TestConstant.WXMP_PAY_NOTIFY, objectId);
//                break;
//            case JD_WEB:
//            	MockNotify.jdPay(client, billNo, "","", TestConstant.JD_PAY_NOTIFY, objectId);
//            	 break;
//            case JD_WAP:
//            	MockNotify.jdPay(client, billNo, "","", TestConstant.JD_PAY_NOTIFY, objectId);
//            	 break;
//            case YEE_WAP:
//            	MockNotify.yeePay(client, billNo, "", "",TestConstant.YEE_PAY_WAP_NOTIFY , objectId);
//            	 break;
//            case YEE_WEB:
//            	MockNotify.yeePay(client, billNo, "", "", TestConstant.YEE_PAY_WEB_NOTIFY, objectId);
//            	 break;
//            case KUAIQIAN_WAP:
//            	MockNotify.kqPay(client, billNo, "", "",TestConstant.KQ_PAY_NOTIFY, objectId);
//            	 break;
//            case KUAIQIAN_WEB:
//            	MockNotify.kqPay(client, billNo, "", "", TestConstant.KQ_PAY_NOTIFY, objectId);
//            	 break;
//            case PAYPAL:
//            	MockNotify.ppPay(client, billNo, "", "", TestConstant.PAY_PALM_PAY_NOTIFY, objectId);
//                break;
        }
    }

    public static void aliPay(Client client, String outTradeNo, String objectId){
        Map<String, String> param = new HashMap<String, String>();
        param.put("gmt_create","2015-06-01 10:46:25");
        param.put("buyer_email","gaojf.bokecc@gmail.com");
        param.put("notify_time","2015-06-01 10:46:26");
        param.put("gmt_payment","2015-06-01 10:46:26");
        param.put("seller_email","admin@beecloud.cn");
        param.put("quantity", "1");
        param.put("subject","ae9f789aa15b458e9451c33d6a23b1a");
        param.put("use_coupon", "N");
        param.put("discount","0.00");
        param.put("body","4c816ad13a3d41f1812c492de74e4424");
        param.put("buyer_id", "2088002964651898");
        param.put("notify_type", "trade_status_sync");
        param.put("payment_type","1");
        param.put("out_trade_no",outTradeNo);
        param.put("price","0.01");
        param.put("trade_status", "TRADE_SUCCESS");
        param.put("total_fee", "0.01");
        param.put("trade_no", "2015060100001000890054866987");
        param.put("seller_id", TestConstant.TEST_APP_ALI_ID);
        param.put("is_total_fee_adjust","N");
        param = AlipayCore.paraFilter(param);
        String toSign = AlipayCore.createLinkString(param);
        String sign = MD5.sign(toSign, TestConstant.TEST_APP_ALI_KEY, "utf-8");
        param.put("sign",sign);
        param.put("sign_type" , "MD5");  //RSA notify cannot be mocked
        param.put("bc_appid", TestConstant.KTestAppID + "_" +  objectId);

        WebTarget target = client.target(TestConstant.TEST_SERVER_URL_V1 + TestConstant.ALI_PAY_NOTIFY);
        Response response = TestUtil.apiRequest(target, MediaType.APPLICATION_JSON, TestConstant.REQUEST_TYPE.POST, 0, param);
    }
    
    public static void wxPay(Client client, String outTradeNo, String tradeType, String wxmp_key, String url, String objectId){
        Map<String, String> xmlMap = new HashMap<String, String>();
        xmlMap.put("appid", TestConstant.TEST_APP_WXMP_ID);
        xmlMap.put("bank_type", "BOC_DEBIT");
        xmlMap.put("cash_fee", "1");
        xmlMap.put("fee_type", "CNY");
        xmlMap.put("is_subscribe", "N");
        xmlMap.put("mch_id", TestConstant.TEST_MECH_ID);
        xmlMap.put("nonce_str", "bd0b95d420664a018f360bd824650371");
        //xmlMap.put("openid", openId);
        xmlMap.put("out_trade_no", outTradeNo);
        xmlMap.put("result_code", "SUCCESS");
        xmlMap.put("return_code", "SUCCESS");
        xmlMap.put("time_end", "20150526144744");
        xmlMap.put("total_fee", "1");
        xmlMap.put("trade_type", tradeType);
        xmlMap.put("transaction_id", "1005000400201505260167766969");
        xmlMap.put("attach", objectId);
        String sign = CommonUtil.sign(xmlMap, wxmp_key, Constant.kWechatPayDefaultCharset);
        xmlMap.put("sign", sign);

        WebTarget target = client.target(TestConstant.TEST_SERVER_URL_V1 + url + TestConstant.TEST_APP_ID);
        Response response = TestUtil.apiRequest(target, MediaType.APPLICATION_XML, TestConstant.REQUEST_TYPE.POST, 0,
                CommonUtil.toPostXMLData(xmlMap));
        assertEquals(TestUtil.preErrMsg(target), "success", response.readEntity(String.class));

        //Webhook cn.beecloud.services.BasicService.asyncPayNotify()
        boolean webhookResult = TestUtil.webhookCheck(client, outTradeNo, "WX", TestConstant.TRANSACTION_TYPE_PAY);
        assertEquals(TestUtil.preErrMsg(target), true, webhookResult);
    }
    
//    public static void unbPay(Client client, String orderid, String url, String objectId){
//        Map<String, String> param = new HashMap<String, String>();
//        param.put("bizType", "000201");
//        param.put("orderId", orderid);
//        param.put("txnSubType", "01");
//        param.put("traceNo", "846399");
//        param.put("settleAmt", "1");
//        param.put("settleCurrencyCode", "156");
//        param.put("settleDate", "0513");
//        param.put("txnType", "01");
//        param.put("encoding","utf-8");
//        param.put("version", "5.0.0");
//        param.put("queryId", "201505131634078463998");
//        param.put("accessType", "0");
//        param.put("respMsg", "Success!");
//        param.put("traceTime", "0513163407");
//        param.put("txnTime", "20150513163407");
//        param.put("merId", "201506180310");
//        param.put("currencyCode", "156");
//        param.put("respCode", "00");
//        param.put("txnAmt", "1");
//        param.put("sign_Method", "01");
//        param.put("certId", "I_am_test_app");
//        param.put("signature", "Let_me_pass");
//
//        String paramStr = TestUtil.genString(param);
//        WebTarget target = client.target(TestConstant.TEST_SERVER_URL_V1 + url + TestConstant.TEST_APP_ID + "/" + objectId);
//        Response response = TestUtil.apiRequest(target, MediaType.WILDCARD, TestConstant.REQUEST_TYPE.POST, 0, paramStr);
//        assertEquals(TestUtil.preErrMsg(target), 300, response.getStatus());
//
//        //check webhook receiver
//        boolean webhookResult = TestUtil.webhookCheck(client, orderid, "UN", TestConstant.TRANSACTION_TYPE_PAY);
//        assertEquals(TestUtil.preErrMsg(target), true, webhookResult);
//    }
//
//    public static void wxPay(Client client, String outTradeNo, String tradeType, String wxmp_key, String url, String objectId){
//        Map<String, String> xmlMap = new HashMap<String, String>();
//        xmlMap.put("appid", TestConstant.TEST_APP_WXMP_ID);
//        xmlMap.put("bank_type", "BOC_DEBIT");
//        xmlMap.put("cash_fee", "1");
//        xmlMap.put("fee_type", "CNY");
//        xmlMap.put("is_subscribe", "N");
//        xmlMap.put("mch_id", TestConstant.TEST_MECH_ID);
//        xmlMap.put("nonce_str", "bd0b95d420664a018f360bd824650371");
//        //xmlMap.put("openid", openId);
//        xmlMap.put("out_trade_no", outTradeNo);
//        xmlMap.put("result_code", "SUCCESS");
//        xmlMap.put("return_code", "SUCCESS");
//        xmlMap.put("time_end", "20150526144744");
//        xmlMap.put("total_fee", "1");
//        xmlMap.put("trade_type", tradeType);
//        xmlMap.put("transaction_id", "1005000400201505260167766969");
//        xmlMap.put("attach", objectId);
//        String sign = CommonUtil.sign(xmlMap, wxmp_key, Constant.kWechatPayDefaultCharset);
//        xmlMap.put("sign", sign);
//
//        WebTarget target = client.target(TestConstant.TEST_SERVER_URL_V1 + url + TestConstant.TEST_APP_ID);
//        Response response = TestUtil.apiRequest(target, MediaType.APPLICATION_XML, TestConstant.REQUEST_TYPE.POST, 0,
//                CommonUtil.toPostXMLData(xmlMap));
//        assertEquals(TestUtil.preErrMsg(target), "success", response.readEntity(String.class));
//
//        //Webhook cn.beecloud.services.BasicService.asyncPayNotify()
//        boolean webhookResult = TestUtil.webhookCheck(client, outTradeNo, "WX", TestConstant.TRANSACTION_TYPE_PAY);
//        assertEquals(TestUtil.preErrMsg(target), true, webhookResult);
//    }

    public static void jdPay(Client client, String outTradeNo, String tradeType, String jd_key, String url, String objectId){
    	
    }
    public static void yeePay(Client client, String outTradeNo, String tradeType, String yee_key, String url, String objectId){
    	
    }
    public static void kqPay(Client client, String outTradeNo, String tradeType, String kq_key, String url, String objectId){
    	
    }
    public static void ppPay(Client client, String outTradeNo, String tradeType, String pp_key, String url, String objectId){
    	
    }
//    public static void refund(Client client, String channel, String billNo, String refundNo){
//        List<Condition> conds = new LinkedList<Condition>();
//        conds.add(new Condition(Constant.kColumnBillNo, Constant.CONDITION_TYPE.e, billNo));
//        String objectId = TestUtil.getObjectId(client, TestConstant.TEST_APP_ID, TestConstant.TEST_APP_KEY,
//                Constant.kAllRefundTable, conds);
//
//        TestConstant.CHANNEL_TYPE channel_type = TestConstant.CHANNEL_TYPE.valueOf(channel);
//        switch(channel_type){
//            case WX_APP:
//            case WX_JSAPI:
//            case WX_NATIVE:
//                //MockNotify.wxRefund(client, billNo, refundNo);
//                break;
//            case ALI_APP:
//            case ALI_WEB:
//            case ALI_QRCODE:
//                MockNotify.aliRefund(client, billNo, refundNo, objectId);
//                break;
//            case UN_APP:
//                MockNotify.unRefund(client, billNo, refundNo, TestConstant.UN_REFUND_NOTIFY, objectId);
//                break;
//            case UN_WEB:
//                MockNotify.unRefund(client, billNo, refundNo, TestConstant.UNWEB_REFUND_NOTIFY, objectId);
//                break;
//            case JD_WAP:
//            	MockNotify.jdRefund(client, billNo, refundNo, TestConstant.JD_REFUND_NOTIFY,objectId);
//            	break;
//            case JD_WEB:
//            	MockNotify.jdRefund(client, billNo, refundNo, TestConstant.JD_REFUND_NOTIFY,objectId);
//            	break;
//            case YEE_WAP:
//            	MockNotify.yeeRefund(client, billNo, refundNo, TestConstant.YEE_PAY_WAP_NOTIFY,objectId);
//            	break;
//            case YEE_WEB:
//            	MockNotify.yeeRefund(client, billNo, refundNo, TestConstant.YEE_PAY_WEB_NOTIFY, objectId);
//            	break;
//            case KUAIQIAN_WAP:
//            	MockNotify.kqRefund(client, billNo, refundNo, TestConstant.KQ_REFUND_NOTIFY, objectId);
//            	break;
//            case KUAIQIAN_WEB:
//            	MockNotify.kqRefund(client, billNo, refundNo, TestConstant.KQ_REFUND_NOTIFY, objectId);
//            	break;
//            case PAYPAL:
//            	MockNotify.ppRefund(client, billNo, refundNo, TestConstant.PAY_PALM_REFUND_NOTIFY, objectId);
//            	break;
//        }
//    }

//    private static void unRefund(Client client, String outTradeNo, String outRefundNo, String url, String objectId){
//        Map<String, String> param = new HashMap<String, String>();
//        param.put("bizType","000201");
//        param.put("orderId",outRefundNo);
//        param.put("txnSubType","01");
//        param.put("traceNo","846399");
//        param.put("settleAmt","1");
//        param.put("settleCurrencyCode", "156");
//        param.put("settleDate","0513");
//        param.put("txnType","01");
//        param.put("encoding","utf-8");
//        param.put("version", "5.0.0");
//        param.put("queryId","201505131634078463998");
//        param.put("accessType","0");
//        param.put("respMsg","Success!");
//        param.put("traceTime","0513163407");
//        param.put("txnTime", "20150513163407");
//        param.put("merId", "201506180310");
//        param.put("currencyCode", "156");
//        param.put("respCode", "00");
//        param.put("txnAmt","1");
//        param.put("sign_Method" , "01");
//        param.put("certId","I_am_test_app");
//        param.put("signature","Let_me_pass");
//
//        String paramStr = TestUtil.genString(param);
//        WebTarget target = client.target(TestConstant.TEST_SERVER_URL_V1 + url + TestConstant.TEST_APP_ID + "/" + objectId);
//        Response response = TestUtil.apiRequest(target, MediaType.WILDCARD, TestConstant.REQUEST_TYPE.POST, 0, paramStr);
//        assertEquals(TestUtil.preErrMsg(target), 300, response.getStatus());
//
//        String message = "[ERROR] Method: " + Thread.currentThread().getStackTrace()[3].getMethodName()
//                + " ### Lucene Count Error";
//        boolean webhookResult = TestUtil.webhookCheck(client, outRefundNo, "UN", TestConstant.TRANSACTION_TYPE_REFUND);
//        assertEquals(message, true, webhookResult);
//    }
//
//    private static void aliRefund(Client client, String outTradeNo, String outRefundNo, String objectId){
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("notify_type", "batch_refund_notify");
//        map.put("batch_no", outRefundNo);
//        map.put("out_trade_no", outTradeNo);
//        map.put("success_num", "1");
//        map.put("result_details", "2015062500001000610055143982^0.01^SUCCESS");
//        map = AlipayCore.paraFilter(map);
//        String toSign = AlipayCore.createLinkString(map);
//        String sign = MD5.sign(toSign, TestConstant.TEST_APP_ALI_KEY, Constant.kAliPayDefaultCharset);
//        map.put("sign",sign);
//        map.put("sign_type", "MD5");
//        map.put("bc_appid", TestConstant.TEST_APP_ID + "_" + objectId);
//
//        WebTarget target = client.target(TestConstant.TEST_SERVER_URL_V1 + TestConstant.ALI_REFUND_NOTIFY);
//        Response response = TestUtil.apiRequest(target, MediaType.APPLICATION_JSON, TestConstant.REQUEST_TYPE.POST, 0, map);
//        assertEquals(TestUtil.preErrMsg(target), "success", response.readEntity(String.class));
//
//        String message = "[ERROR] Method: " + Thread.currentThread().getStackTrace()[3].getMethodName()
//                + " ### Lucene Count Error";
//        boolean webhookResult = TestUtil.webhookCheck(client, outTradeNo, "ALI", TestConstant.TRANSACTION_TYPE_REFUND);
//        assertEquals(message, true, webhookResult);
//    }
//
//    public static void jdRefund(Client client, String outTradeNo, String outRefundNo, String url,String objectId){
//    	
//    }
//    public static void yeeRefund(Client client, String outTradeNo, String outRefundNo, String url,String objectId){
//    	
//    }
//    public static void kqRefund(Client client, String outTradeNo, String outRefundNo, String url,String objectId){
//    	
//    }
//    public static void ppRefund(Client client, String outTradeNo, String outRefundNo, String url,String objectId){
//    	
//    }
}

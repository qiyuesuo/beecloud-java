package cn.beecloud.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.beecloud.BCEumeration.PAY_CHANNEL;


/**
 * BeeCloud批量审核类，封装了待批量审核的记录id集合，以及审核的详细信息
 * 
 * @author Rui.Feng
 * @since 2015.11.24
 */
public class BCBatchRefund {

    private List<String> ids;

    private PAY_CHANNEL channel;

    private Boolean agree;

    private String aliRefundUrl;

    private Map<String, String> idResult = new HashMap<String, String>();

    /**
     * 访问字段{@link #ids}
     */
    public List<String> getIds() {
        return ids;
    }

    /**
     * @param ids
     * （必填）退款记录id列表，批量审核的退款记录的唯一标识符集合
     */
    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    /**
     * 访问字段{@link #channel}
     */
    public PAY_CHANNEL getChannel() {
        return channel;
    }

    /**
     * @param channel
     * 渠道类型， 根据不同场景选择不同的支付方式，包含： {@link PAY_CHANNEL#WX}: 微信
     * {@link PAY_CHANNEL#ALI}: 支付宝 {@link PAY_CHANNEL#UN}: 银联
     * {@link PAY_CHANNEL#YEE}: 易宝 {@link PAY_CHANNEL#JD}: 京东
     * {@link PAY_CHANNEL#KUAIQIAN}: 快钱 {@link PAY_CHANNEL#BD}: 百度 (必填)
     */
    public void setChannel(PAY_CHANNEL channel) {
        this.channel = channel;
    }

    /**
     * 访问字段{@link #agree}
     */
    public Boolean getAgree() {
        return agree;
    }

    /**
     * @param agree
     * （必填）同意或者驳回，批量驳回传false，批量同意传true
     */
    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    /**
     * @return 支付宝批量退款跳转url，支付宝预退款批量同意处理成功后返回
     */
    public String getAliRefundUrl() {
        return aliRefundUrl;
    }

    /**
     * 设置字段{@link #aliRefundUrl}
     */
    public void setAliRefundUrl(String aliRefundUrl) {
        this.aliRefundUrl = aliRefundUrl;
    }

    /**
     * @return 退款id、结果信息集合，当批量同意处理成功时，value值为"OK"；当批量同意处理失败时， value值为具体的错误信息
     */
    public Map<String, String> getIdResult() {
        return idResult;
    }

    /**
     * 设置字段{@link #idResult}
     */
    public void setIdResult(Map<String, String> idResult) {
        this.idResult = idResult;
    }
}

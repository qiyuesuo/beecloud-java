package cn.beecloud.bean;

import java.util.LinkedList;
import java.util.List;

import cn.beecloud.BCEumeration.PAY_CHANNEL;


/**
 * BeeCloud批量打款参数类
 * 
 * @author Rui.Feng
 * @since 2015.11.23
 */
public class TransfersParameter {

    private PAY_CHANNEL channel;

    private String batchNo;

    private String accountName;

    private List<ALITransferData> transferDataList = new LinkedList<ALITransferData>();

    /**
     * 访问字段 {@link #channel}
     */
    public PAY_CHANNEL getChannel() {
        return channel;
    }

    /**
     * @param channel
     * （必填）渠道类型，根据不同场景选择不同的支付方式，包含： {@link PAY_CHANNEL#ALI}: 支付宝企业打款
     */
    public void setChannel(PAY_CHANNEL channel) {
        this.channel = channel;
    }

    /**
     * 访问字段 {@link #batchNo}
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * @param batchNo
     * （必填）打款单号，支付宝为11-32位数字字母组合
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * 访问字段 {@link #accountName}
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName
     * （必填）付款方名称，付款账号账户全称
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 访问字段 {@link #transferDataList}
     */
    public List<ALITransferData> getTransferDataList() {
        return transferDataList;
    }

    /**
     * @param transferDataList
     * （必填）付款详细，包含每一笔的具体信息{@link ALITransferData}
     */
    public void setTransferDataList(List<ALITransferData> transferDataList) {
        this.transferDataList = transferDataList;
    }
}

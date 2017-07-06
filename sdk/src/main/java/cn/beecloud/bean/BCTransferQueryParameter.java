package cn.beecloud.bean;

import cn.beecloud.BCEumeration;

import java.util.Date;
import java.util.Map;


/**
 * 打款订单查询 Created by sy on 17/7/4.
 */
public class BCTransferQueryParameter {
    // 发起支付时填写的订单号
    private String billNo;
    // 是否需要返回渠道的回调信息，true为需要
    private Boolean needDetail;
   // 标识订单是否支付成功,SUCCESS表示成功, FAIL表示失败，PROCESSING表示处理中
    private BCEumeration.TRANSFER_STATUS transferStatus;
    // 起始时间
    private Date startTime;
    // 结束时间
    private Date endTime;
    // 查询起始位置
    private Integer skip;
    // 查询的条数
    private Integer limit;

    public BCTransferQueryParameter() {};

    public BCTransferQueryParameter(String billNo, Boolean needDetail, BCEumeration.TRANSFER_STATUS transferStatus,
                                    Date startTime, Date endTime, Integer skip, Integer limit) {
        this.billNo = billNo;
        this.needDetail = needDetail;
        this.transferStatus = transferStatus;
        this.startTime = startTime;
        this.endTime = endTime;
        this.skip = skip;
        this.limit = limit;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Boolean getNeedDetail() {
        return needDetail;
    }

    public void setNeedDetail(Boolean needDetail) {
        this.needDetail = needDetail;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public BCEumeration.TRANSFER_STATUS getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(BCEumeration.TRANSFER_STATUS transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}

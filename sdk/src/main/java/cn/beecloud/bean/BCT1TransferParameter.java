package cn.beecloud.bean;

import java.util.Map;

/**
 * Created by sunqifs on 7/7/17.
 */
public class BCT1TransferParameter {
    // 下发订单总金额 必须是正整数，单位为分
    private Integer totalFee;
    // 商户订单号 8到32位数字和/或字母组合，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复
    private String billNo;
    // 对公对私类型
    private String isPersonal;
    // 银行名称
    private String bankName;
    // 银行账户号
    private String bankAccountNo;
    // 银行账户名称
    private String bankAccountName;
    // optional
    private Map<String, Object> optional;


    public BCT1TransferParameter() {
    }

    public BCT1TransferParameter(Integer totalFee, String billNo,  String isPersonal, String bankName, String bankAccountNo,
                                 String bankAccountName, Map<String, Object> optional) {
        this.totalFee = totalFee;
        this.billNo = billNo;
        this.isPersonal = isPersonal;
        this.bankName = bankName;
        this.bankAccountNo = bankAccountNo;
        this.bankAccountName = bankAccountName;
        this.optional = optional;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getIsPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(String isPersonal) {
        this.isPersonal = isPersonal;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public Map<String, Object> getOptional() {
        return optional;
    }

    public void setOptional(Map<String, Object> optional) {
        this.optional = optional;
    }
}

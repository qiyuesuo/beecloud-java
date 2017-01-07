package cn.beecloud.bean;

/**
 * User: jackybian
 * Date: 2017/1/5
 */
public class BCV2TransferParameter {
    // 下发订单总金额 必须是正整数，单位为分
    private Integer totalFee;
    // 商户订单号 8到32位数字和/或字母组合，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复
    private String billNo;
    // 下发订单备注 UTF8编码格式，32个字节内，最长支持16个汉字
    private String note;
    // 银行全名
    private String bankFullName;
    // 银行卡类型 区分借记卡和信用卡 DE代表借记卡，CR代表信用卡，其他值为非法
    private String cardType;
    // 收款帐户类型 区分对公和对私 P代表私户，C代表公户，其他值为非法
    private String accountType;
    // 收款方的银行卡号
    private String accountNo;
    // 收款方的姓名或者单位名
    private String accountName;
    // 开卡省
    private String bankProvince;
    // 开卡市
    private String bankCity;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBankFullName() {
        return bankFullName;
    }

    public void setBankFullName(String bankFullName) {
        this.bankFullName = bankFullName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public BCV2TransferParameter() {
    }
}

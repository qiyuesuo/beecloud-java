package cn.beecloud.bean;

import java.util.List;

/**
 * Created by rui on 16/7/27.
 */
public class SubscriptionBanks {

    private List<String> bankList;

    private List<String> commonBankList;

    public List<String> getBankList() {
        return bankList;
    }

    public void setBankList(List<String> bankList) {
        this.bankList = bankList;
    }

    public List<String> getCommonBankList() {
        return commonBankList;
    }

    public void setCommonBankList(List<String> commonBankList) {
        this.commonBankList = commonBankList;
    }
}

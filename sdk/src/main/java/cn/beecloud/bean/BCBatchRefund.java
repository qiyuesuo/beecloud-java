package cn.beecloud.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.beecloud.BCEumeration.PAY_CHANNEL;

public class BCBatchRefund {
	
	private List<String> ids;
	
	private PAY_CHANNEL channel;
	
	private Boolean agree;
	
	private String aliRefundUrl;
	
	private Map<String, String> idResult = new HashMap<String, String>();

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public PAY_CHANNEL getChannel() {
		return channel;
	}

	public void setChannel(PAY_CHANNEL channel) {
		this.channel = channel;
	}

	public Boolean getAgree() {
		return agree;
	}

	public void setAgree(Boolean agree) {
		this.agree = agree;
	}

	public String getAliRefundUrl() {
		return aliRefundUrl;
	}

	public void setAliRefundUrl(String aliRefundUrl) {
		this.aliRefundUrl = aliRefundUrl;
	}

	public Map<String, String> getIdResult() {
		return idResult;
	}

	public void setIdResult(Map<String, String> idResult) {
		this.idResult = idResult;
	}
}

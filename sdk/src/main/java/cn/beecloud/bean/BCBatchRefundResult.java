package cn.beecloud.bean;

import java.util.HashMap;
import java.util.Map;

public class BCBatchRefundResult {
	
	private String aliRefundUrl;
	
	private Map<String, String> idResult = new HashMap<String, String>();
	
	private Map<String, String> idResult = new HashMap<String, String>();

	public Map<String, String> getIdResult() {
		return idResult;
	}

	public void setIdResult(Map<String, String> idResult) {
		this.idResult = idResult;
	}

	public String getAliRefundUrl() {
		return aliRefundUrl;
	}

	public void setAliRefundUrl(String aliRefundUrl) {
		this.aliRefundUrl = aliRefundUrl;
	}
}

package cn.beecloud.bean;

import java.util.HashMap;
import java.util.Map;

public class BCBatchRefundResult {
	
	private String resultCode;
	
	private String resultMsg;
	
	private String errDetail = "";
	
	private String aliRefundUrl;
	
	private Map<String, String> idResult = new HashMap<String, String>();

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getErrDetail() {
		return errDetail;
	}

	public void setErrDetail(String errDetail) {
		this.errDetail = errDetail;
	}

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

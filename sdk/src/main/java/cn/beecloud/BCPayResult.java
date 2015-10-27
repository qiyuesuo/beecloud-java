package cn.beecloud;

import java.util.Map;

import cn.beecloud.BCEumeration.RESULT_TYPE;

/**
 * 
 * @author Ray
 * @since 2015/6/12
 * 
 * This class is used to form the result for the SDK invocation.
 * An invoker can present the result message according to the result type enum. 
 */
public class BCPayResult {
	
	private String sucessMsg;
	
	private String resultCode;
	
	private String resultMsg;
	
	private String errDetail = "";
	
	private String codeUrl;
	
	private String html;
	
	private String url;
	
	private String aliQrCode;
	
	private String objectId;
	
	private Map<String, Object> wxJSAPIMap;

	public BCPayResult(RESULT_TYPE type)
	{
		this.resultCode = StrUtil.toStr(type.ordinal());
		this.resultMsg = type.toString();
	}
	
	public BCPayResult(String errDetail, RESULT_TYPE type) {
		this.errDetail = errDetail;
		this.resultCode = StrUtil.toStr(type.ordinal());
		this.resultMsg = type.toString();
	}
	
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getSucessMsg() {
		return sucessMsg;
	}

	public void setSucessMsg(String sucessMsg) {
		this.sucessMsg = sucessMsg;
	}

	public String getErrDetail() {
		return errDetail;
	}

	public void setErrDetail(String errDetail) {
		this.errDetail = errDetail;
	}

	public Map<String, Object> getWxJSAPIMap() {
		return wxJSAPIMap;
	}

	public void setWxJSAPIMap(Map<String, Object> wxJSAPIMap) {
		this.wxJSAPIMap = wxJSAPIMap;
	}

	public String getAliQrCode() {
		return aliQrCode;
	}

	public void setAliQrCode(String aliQrCode) {
		this.aliQrCode = aliQrCode;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}

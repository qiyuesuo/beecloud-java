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
	
	private String errMsg;
	
	private String err_detail;
	
	private RESULT_TYPE type;
	
	private String code_url;
	
	private String html;
	
	private String url;
	
	private Map<String, Object> wxJSAPIMap;

	public RESULT_TYPE getType() {
		return type;
	}

	public void setType(RESULT_TYPE type) {
		this.type = type;
	}
	
	public BCPayResult()
	{
		
	}
	
	public BCPayResult(RESULT_TYPE type)
	{
		this.type = type;
	}
	
	public BCPayResult(String errMsg, RESULT_TYPE type) {
		this.errMsg = errMsg;
		this.type = type;
	}

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
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

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getSucessMsg() {
		return sucessMsg;
	}

	public void setSucessMsg(String sucessMsg) {
		this.sucessMsg = sucessMsg;
	}

	public String getErr_detail() {
		return err_detail;
	}

	public void setErr_detail(String err_detail) {
		this.err_detail = err_detail;
	}

	public Map<String, Object> getWxJSAPIMap() {
		return wxJSAPIMap;
	}

	public void setWxJSAPIMap(Map<String, Object> wxJSAPIMap) {
		this.wxJSAPIMap = wxJSAPIMap;
	}
}

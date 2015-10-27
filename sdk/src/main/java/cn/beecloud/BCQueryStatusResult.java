package cn.beecloud;

import cn.beecloud.BCEumeration.RESULT_TYPE;

/**
 * @author Ray
 * @since 2015/6/12
 * 
 * This class is used to form the result for the invocation of WeChat refund status query interface.
 * An invoker can present the result message according to the result type enum. 
 */
public class BCQueryStatusResult {
	
	private String resultCode;
	
	private String resultMsg;
	
	private String errDetail = "";
	
	private String refundStatus;
	
	public BCQueryStatusResult(RESULT_TYPE type) {
		this.resultCode = StrUtil.toStr(type.ordinal());
		this.resultMsg = type.toString();
	}

	public BCQueryStatusResult(String errDetail, RESULT_TYPE type) {
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

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
}

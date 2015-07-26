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
	
	private String errMsg;
	
	private String errDetail;
	
	private RESULT_TYPE type;
	
	private String refundStatus;

	public BCQueryStatusResult(RESULT_TYPE type) {
		this.type = type;
	}

	public BCQueryStatusResult(String errMsg, RESULT_TYPE type) {
		this.errMsg = errMsg;
		this.type = type;
	}

	public BCQueryStatusResult() {
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrDetail() {
		return errDetail;
	}

	public void setErrDetail(String errDetail) {
		this.errDetail = errDetail;
	}

	public RESULT_TYPE getType() {
		return type;
	}

	public void setType(RESULT_TYPE type) {
		this.type = type;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
}

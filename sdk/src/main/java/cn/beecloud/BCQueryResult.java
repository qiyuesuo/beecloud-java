package cn.beecloud;

import java.util.List;

import cn.beecloud.BCEumeration.RESULT_TYPE;
import cn.beecloud.bean.BCOrderBean;
import cn.beecloud.bean.BCRefundBean;


/**
 * @author Ray
 * @since 2015/6/12
 * 
 * This class is used to form the result for the invocation of query interface.
 * An invoker can present the result message according to the result type enum. 
 */
public class BCQueryResult {
	
	private String resultCode;
	
	private String resultMsg;
	
	private String errDetail;
	
	private BCOrderBean order;
	
	private BCRefundBean refund;
	
	private Integer totalCount;
	
	//if the result is for bill query
    private List<BCOrderBean> bcOrders;
    
    //if the result is for refund query
    private List<BCRefundBean> bcRefundList;
    
	public BCQueryResult(RESULT_TYPE type) {
		this.resultCode = StrUtil.toStr(type.ordinal());
		this.resultMsg = type.toString();
	}

	public BCQueryResult(String errDetail, RESULT_TYPE type) {
		this.errDetail = errDetail;
		this.resultCode = StrUtil.toStr(type.ordinal());
		this.resultMsg = type.toString();
	}

	public BCQueryResult() {
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

	public List<BCOrderBean> getBcOrders() {
		return bcOrders;
	}

	public void setBcOrders(List<BCOrderBean> bcOrders) {
		this.bcOrders = bcOrders;
	}

	public String getErrDetail() {
		return errDetail;
	}

	public void setErrDetail(String errDetail) {
		this.errDetail = errDetail;
	}

	public List<BCRefundBean> getBcRefundList() {
		return bcRefundList;
	}

	public void setBcRefundList(List<BCRefundBean> bcRefundList) {
		this.bcRefundList = bcRefundList;
	}

	public BCOrderBean getOrder() {
		return order;
	}

	public void setOrder(BCOrderBean order) {
		this.order = order;
	}

	public BCRefundBean getRefund() {
		return refund;
	}

	public void setRefund(BCRefundBean refund) {
		this.refund = refund;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}

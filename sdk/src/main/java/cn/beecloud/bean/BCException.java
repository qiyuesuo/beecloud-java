package cn.beecloud.bean;

public class BCException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BCException(Integer resultCode, String resultMessage, String errorDetail) {
		super("resultCode:" + resultCode + ";resultMsg:" + resultMessage + ";errDetail:" + errorDetail);
	}
    public BCException(String msg) {
        super(msg);
    }
}

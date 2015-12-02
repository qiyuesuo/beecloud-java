package cn.beecloud.bean;

/**
 * BeeCloud自定义异常，该异常信息包含错误码，错误基本信息、错误详细信息
 * 
 * @author Rui.Feng
 * @since 2015.11.24
 */
public class BCException extends Exception {

    private static final long serialVersionUID = 1L;

    public BCException(Integer resultCode, String resultMessage, String errorDetail) {
        super("resultCode:" + resultCode + ";resultMsg:" + resultMessage + ";errDetail:"
                + errorDetail);
    }

    public BCException(String msg) {
        super(msg);
    }
}

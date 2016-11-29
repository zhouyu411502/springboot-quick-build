package org.springboot.quick.commons.exception;

/**
 * 异常基类
 * 使用此组件进行异常封装，需要继续此类
 */
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
     * 无参构造函数
     */
    public BaseException() {
        super();
    }

    /**
     * 指定消息
     *
     * @param message 异常内容
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * 指定消息&异常
     *
     * @param message 异常内容
     * @param cause   异常
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 指定异常
     *
     * @param cause 异常
     */
    public BaseException(Throwable cause) {
        super(cause);
    }
}

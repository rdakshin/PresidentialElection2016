package org.data.exception;

/**
 * @author drajavel
 * Custom Exception handler for Runtime Exception 
 */
public class DataFlowException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1597728805696806819L;

	public DataFlowException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public DataFlowException(String message,
                                      Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public DataFlowException(String message,
                                      Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public DataFlowException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public DataFlowException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}

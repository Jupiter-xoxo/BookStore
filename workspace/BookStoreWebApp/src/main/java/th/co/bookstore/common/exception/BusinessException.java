package th.co.bookstore.common.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 3877875431271134599L;

	private String errorCode;
	private String errorDesc;
	
	public BusinessException(String errorCode, String errorDesc) {
		super();
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

}

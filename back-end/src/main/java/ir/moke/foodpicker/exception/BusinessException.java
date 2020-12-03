package ir.moke.foodpicker.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class BusinessException extends RuntimeException implements ExceptionCode {
    private String message;
    private int code;

    public BusinessException(int code) {
        //like csv format
        super();
        this.code = code;
    }

    public BusinessException(int code, String message) {
        //like csv format
        super(message);
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

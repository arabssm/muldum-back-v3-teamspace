package co.kr.muldum.domain.exception;

import org.springframework.http.HttpStatus;

public class InvalidParameterException extends BusinessException {

    private static final String ERROR_CODE = "INVALID_PARAMETER";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public InvalidParameterException(String message) {
        super(message, HTTP_STATUS, ERROR_CODE);
    }

    public InvalidParameterException(String parameterName, String value) {
        super("유효하지 않은 " + parameterName + " 값입니다: " + value, HTTP_STATUS, ERROR_CODE);
    }
}
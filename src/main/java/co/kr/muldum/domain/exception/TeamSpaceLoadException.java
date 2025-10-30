package co.kr.muldum.domain.exception;

import org.springframework.http.HttpStatus;

public class TeamSpaceLoadException extends BusinessException {

    private static final String ERROR_CODE = "TEAMSPACE_LOAD_FAILED";
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public TeamSpaceLoadException(String message) {
        super("팀스페이스 데이터를 불러오는 중 오류가 발생했습니다: " + message, HTTP_STATUS, ERROR_CODE);
    }

    public TeamSpaceLoadException(String message, Throwable cause) {
        super("팀스페이스 데이터를 불러오는 중 오류가 발생했습니다: " + message, HTTP_STATUS, ERROR_CODE, cause);
    }
}
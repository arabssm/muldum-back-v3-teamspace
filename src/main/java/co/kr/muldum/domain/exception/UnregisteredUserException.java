package co.kr.muldum.domain.exception;

import org.springframework.http.HttpStatus;

public class UnregisteredUserException extends BusinessException {

    private static final String ERROR_CODE = "UNREGISTERED_USER";
    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public UnregisteredUserException(String identifier) {
        super("등록되지 않은 사용자입니다. 확인이 필요한 식별자: " + identifier, HTTP_STATUS, ERROR_CODE);
    }

    public static UnregisteredUserException forStudent(int grade, int classNo, int studentNo, int rowNumber) {
        String identifier = String.format("학년 %d, 반 %d, 번호 %d (시트 행 %d)", grade, classNo, studentNo, rowNumber);
        return new UnregisteredUserException(identifier);
    }
}

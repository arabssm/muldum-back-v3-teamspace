package co.kr.muldum.domain.exception;

import org.springframework.http.HttpStatus;

public class TeamNotFoundException extends BusinessException {

    private static final String ERROR_CODE = "TEAM_NOT_FOUND";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public TeamNotFoundException() {
        super("팀 정보를 찾을 수 없습니다.", HTTP_STATUS, ERROR_CODE);
    }

    public TeamNotFoundException(Long teamId) {
        super("팀을 찾을 수 없습니다. (팀 ID: " + teamId + ")", HTTP_STATUS, ERROR_CODE);
    }
}
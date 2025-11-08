package co.kr.muldum.domain.exception;

import co.kr.muldum.domain.constants.ErrorMessages;
import org.springframework.http.HttpStatus;

public class TeamNotFoundException extends BusinessException {

    private static final String ERROR_CODE = "TEAM_NOT_FOUND";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public TeamNotFoundException() {
        super(ErrorMessages.TEAM_NOT_FOUND, HTTP_STATUS, ERROR_CODE);
    }

    public TeamNotFoundException(Long teamId) {
        super(String.format(ErrorMessages.TEAM_NOT_FOUND_WITH_ID, teamId), HTTP_STATUS, ERROR_CODE);
    }
}
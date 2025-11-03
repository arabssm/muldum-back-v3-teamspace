package co.kr.muldum.domain.exception;

import org.springframework.http.HttpStatus;

public class TeamInvitationException extends BusinessException {

    private static final String ERROR_CODE = "TEAM_INVITATION_FAILED";
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public TeamInvitationException(String message) {
        super(message, HTTP_STATUS, ERROR_CODE);
    }

    public TeamInvitationException(String message, Throwable cause) {
        super(message, HTTP_STATUS, ERROR_CODE, cause);
    }
}

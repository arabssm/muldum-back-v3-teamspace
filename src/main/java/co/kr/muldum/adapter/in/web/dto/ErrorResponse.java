package co.kr.muldum.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "에러 응답")
@Getter
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "TEAM_NOT_FOUND")
    private final String errorCode;

    @Schema(description = "에러 메시지", example = "팀 정보를 찾을 수 없습니다.")
    private final String message;

    private ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ErrorResponse of(String errorCode, String message) {
        return new ErrorResponse(errorCode, message);
    }
}

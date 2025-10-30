package co.kr.muldum.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "메시지 응답")
@Getter
public class MessageResponse {

    @Schema(description = "응답 메시지", example = "팀 페이지가 성공적으로 수정되었습니다.")
    private final String message;

    private MessageResponse(String message) {
        this.message = message;
    }

    public static MessageResponse of(String message) {
        return new MessageResponse(message);
    }
}

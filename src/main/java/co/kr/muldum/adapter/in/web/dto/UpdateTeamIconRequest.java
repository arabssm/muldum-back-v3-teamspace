package co.kr.muldum.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(description = "팀 아이콘 수정 요청")
@Getter
public class UpdateTeamIconRequest {

    @Schema(description = "팀 아이콘 URL", example = "/uploads/team/icon/abcd1234.png", required = true)
    @NotBlank(message = "아이콘 URL은 필수입니다.")
    private String url;
}

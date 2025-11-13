package co.kr.muldum.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Schema(description = "팀 페이지 수정 요청")
@Getter
public class UpdateTeamPageRequest {

    @Schema(description = "팀 ID", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    @NotNull(message = "팀 ID는 필수입니다.")
    private UUID teamId;

    @Schema(description = "팀 소개 내용", example = "우리 동아리는 전공동아리를 더 편리하게 사용할 수 있도록 하는 동아리입니다. 물품 신청, 공지, 역대 전공동아리 등을 ...", required = true)
    @NotBlank(message = "팀 소개 내용은 필수입니다.")
    private String content;
}

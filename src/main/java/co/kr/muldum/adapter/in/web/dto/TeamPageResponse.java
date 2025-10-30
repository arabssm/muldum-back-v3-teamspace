package co.kr.muldum.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "팀 페이지 상세 정보 응답")
@Getter
public class TeamPageResponse {

    @Schema(description = "팀 ID", example = "1")
    private final Long team_id;

    @Schema(description = "팀 소개 내용", example = "우리 동아리는 전공동아리를 더 편리하게 사용할 수 있도록 하는 동아리입니다. 물품 신청, 공지, 역대 전공동아리 등을 ...")
    private final String content;

    private TeamPageResponse(Long team_id, String content) {
        this.team_id = team_id;
        this.content = content;
    }

    public static TeamPageResponse of(Long team_id, String content) {
        return new TeamPageResponse(team_id, content);
    }
}

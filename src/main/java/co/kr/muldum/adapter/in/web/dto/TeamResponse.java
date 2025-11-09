package co.kr.muldum.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Schema(description = "팀 정보")
@Getter
public class TeamResponse {

    @Schema(description = "팀 ID", example = "1")
    private final UUID teamId;

    @Schema(description = "팀 이름", example = "아라")
    private final String teamName;

    @Schema(description = "팀 멤버 목록")
    private final List<MemberResponse> member;

    private TeamResponse(UUID teamId, String teamName, List<MemberResponse> member) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.member = member;
    }

    public static TeamResponse of(UUID teamId, String teamName, List<MemberResponse> member) {
        return new TeamResponse(teamId, teamName, member);
    }
}
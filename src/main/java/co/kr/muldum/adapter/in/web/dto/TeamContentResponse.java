package co.kr.muldum.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "팀 정보")
@Getter
public class TeamContentResponse {

    @Schema(description = "팀 ID", example = "1")
    private final Long teamid;

    @Schema(description = "팀 이름", example = "올망졸망")
    private final String teamName;

    @Schema(description = "팀 멤버 목록")
    private final List<TeamMemberResponse> member;

    private TeamContentResponse(Long teamid, String teamName, List<TeamMemberResponse> member) {
        this.teamid = teamid;
        this.teamName = teamName;
        this.member = member;
    }

    public static TeamContentResponse of(Long teamid, String teamName, List<TeamMemberResponse> member) {
        return new TeamContentResponse(teamid, teamName, member);
    }
}
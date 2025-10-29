package co.kr.muldum.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "팀스페이스 응답")
@Getter
public class TeamSpaceResponse {

    @Schema(description = "팀 목록")
    private final List<TeamResponse> teams;

    private TeamSpaceResponse(List<TeamResponse> teams) {
        this.teams = teams;
    }

    public static TeamSpaceResponse of(List<TeamResponse> teams) {
        return new TeamSpaceResponse(teams);
    }
}
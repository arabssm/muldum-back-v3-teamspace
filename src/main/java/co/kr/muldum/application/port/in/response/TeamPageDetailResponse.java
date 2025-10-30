package co.kr.muldum.application.port.in.response;

import lombok.Getter;

@Getter
public class TeamPageDetailResponse {

    private final Long teamId;
    private final String content;

    private TeamPageDetailResponse(Long teamId, String content) {
        this.teamId = teamId;
        this.content = content;
    }

    public static TeamPageDetailResponse of(Long teamId, String content) {
        return new TeamPageDetailResponse(teamId, content);
    }
}
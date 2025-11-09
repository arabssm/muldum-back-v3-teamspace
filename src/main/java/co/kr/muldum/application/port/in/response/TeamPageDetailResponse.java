package co.kr.muldum.application.port.in.response;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TeamPageDetailResponse {

    private final UUID teamId;
    private final String content;

    private TeamPageDetailResponse(UUID teamId, String content) {
        this.teamId = teamId;
        this.content = content;
    }

    public static TeamPageDetailResponse of(UUID teamId, String content) {
        return new TeamPageDetailResponse(teamId, content);
    }
}

package co.kr.muldum.adapter.in.web.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TeamSpaceResponse {

    private final List<TeamResponse> teams;

    private TeamSpaceResponse(List<TeamResponse> teams) {
        this.teams = teams;
    }

    public static TeamSpaceResponse of(List<TeamResponse> teams) {
        return new TeamSpaceResponse(teams);
    }
}
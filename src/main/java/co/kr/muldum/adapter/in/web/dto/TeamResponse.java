package co.kr.muldum.adapter.in.web.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TeamResponse {

    private final Long teamId;
    private final String teamName;
    private final List<MemberResponse> member;

    private TeamResponse(Long teamId, String teamName, List<MemberResponse> member) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.member = member;
    }

    public static TeamResponse of(Long teamId, String teamName, List<MemberResponse> member) {
        return new TeamResponse(teamId, teamName, member);
    }
}
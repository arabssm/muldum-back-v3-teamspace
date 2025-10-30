package co.kr.muldum.application.port.in.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TeamSpaceListResponse {

    private final List<TeamInfo> teams;

    public static TeamSpaceListResponse of(List<TeamInfo> teams) {
        return new TeamSpaceListResponse(teams);
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TeamInfo {
        private final Long teamId;
        private final String teamName;
        private final List<MemberInfo> members;

        public static TeamInfo of(Long teamId, String teamName, List<MemberInfo> members) {
            return new TeamInfo(teamId, teamName, members);
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MemberInfo {
        private final Long userId;
        private final String studentName;

        public static MemberInfo of(Long userId, String studentName) {
            return new MemberInfo(userId, studentName);
        }
    }
}
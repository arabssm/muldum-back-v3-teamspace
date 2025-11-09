package co.kr.muldum.application.port.in.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TeamSpaceContentListResponse {

    private final List<TeamContentInfo> content;

    public static TeamSpaceContentListResponse of(List<TeamContentInfo> content) {
        return new TeamSpaceContentListResponse(content);
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TeamContentInfo {
        private final UUID teamId;
        private final String teamName;
        private final String teamType;
        private final List<MemberInfo> members;

        public static TeamContentInfo of(UUID teamId, String teamName, String teamType, List<MemberInfo> members) {
            return new TeamContentInfo(teamId, teamName, teamType, members);
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
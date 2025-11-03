package co.kr.muldum.domain.model;

import lombok.Getter;

@Getter
public class Member {

    private final Long memberId;
    private final Long teamId;
    private final Long userId;
    private final MemberRole role;

    private Member(Long memberId, Long teamId, Long userId, MemberRole role) {
        if (teamId == null) {
            throw new IllegalArgumentException("Team ID cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        this.memberId = memberId;
        this.teamId = teamId;
        this.userId = userId;
        this.role = role;
    }

    public static Member create(Long teamId, Long userId, MemberRole role) {
        return new Member(null, teamId, userId, role);
    }

    public static Member of(Long memberId, Long teamId, Long userId, MemberRole role) {
        return new Member(memberId, teamId, userId, role);
    }

    public Member withId(Long memberId) {
        return new Member(memberId, this.teamId, this.userId, this.role);
    }
}

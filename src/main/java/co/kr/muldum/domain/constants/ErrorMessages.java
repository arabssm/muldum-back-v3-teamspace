package co.kr.muldum.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {

    // Team related errors
    public static final String TEAM_NOT_FOUND = "팀 정보를 찾을 수 없습니다.";
    public static final String TEAM_NOT_FOUND_WITH_ID = "팀을 찾을 수 없습니다. (팀 ID: %d)";

    // User type errors
    public static final String UNKNOWN_USER_TYPE = "Unknown user type: %s";
    public static final String UNKNOWN_ENTITY_TYPE = "Unknown entity type: %s";
}
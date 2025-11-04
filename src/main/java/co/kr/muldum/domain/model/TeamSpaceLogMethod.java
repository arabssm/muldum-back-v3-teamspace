package co.kr.muldum.domain.model;

public enum TeamSpaceLogMethod {
    CREATE_TEAM("팀 생성"),
    UPDATE_TEAM("팀 수정"),
    DELETE_TEAM("팀 삭제"),
    INVITE_MEMBER("팀원 초대"),
    REMOVE_MEMBER("팀원 제거"),
    UPDATE_TEAM_PAGE("팀 페이지 수정"),
    UPDATE_BANNER("배너 수정"),
    UPDATE_ICON("아이콘 수정");

    private final String description;

    TeamSpaceLogMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static TeamSpaceLogMethod fromCode(String code) {
        try {
            return TeamSpaceLogMethod.valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid TeamSpaceLogMethod: " + code);
        }
    }
}
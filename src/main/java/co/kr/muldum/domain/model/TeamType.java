package co.kr.muldum.domain.model;

public enum TeamType {
    MAJOR_CLUB("전공동아리", "major"),
    NETWORK("네트워크", "network"),
    AUTONOMOUS_CLUB("자율동아리", "autonomous"),
    GREADUATION("졸업작품", "greaduation");


    private final String description;
    private final String code;

    TeamType(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return this.name();
    }

    public String getCode() {
        return code;
    }

    public static TeamType fromCode(String code) {
        for (TeamType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid team type code: " + code);
    }
}
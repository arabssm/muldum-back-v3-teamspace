package co.kr.muldum.domain.model;

public enum Role {
    STUDENT("student"),
    TEACHER("teacher"),
    MENTOR("mentor");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

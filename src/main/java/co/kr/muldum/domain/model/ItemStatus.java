package co.kr.muldum.domain.model;

public enum ItemStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected"),
    INTEMP("intemp");

    private final String value;

    ItemStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

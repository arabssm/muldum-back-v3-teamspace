package co.kr.muldum.domain.model;

public enum MonthReportStatus {
    DRAFT("draft"),
    SUBMIT("submit");

    private final String value;

    MonthReportStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

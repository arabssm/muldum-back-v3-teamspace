package co.kr.muldum.domain.model;

public class HistoryAward {
    private final Long awardId;
    private final Long historyId;
    private final String awardType;

    private HistoryAward(Long awardId, Long historyId, String awardType) {
        this.awardId = awardId;
        this.historyId = validateHistoryId(historyId);
        this.awardType = validateAwardType(awardType);
    }

    public static HistoryAward create(Long historyId, String awardType) {
        return new HistoryAward(null, historyId, awardType);
    }

    public static HistoryAward of(Long awardId, Long historyId, String awardType) {
        return new HistoryAward(awardId, historyId, awardType);
    }

    private Long validateHistoryId(Long historyId) {
        if (historyId == null) {
            throw new IllegalArgumentException("History ID cannot be null");
        }
        return historyId;
    }

    private String validateAwardType(String awardType) {
        if (awardType == null || awardType.trim().isEmpty()) {
            throw new IllegalArgumentException("Award type cannot be null or empty");
        }
        return awardType;
    }

    public HistoryAward withId(Long awardId) {
        return new HistoryAward(awardId, this.historyId, this.awardType);
    }

    public Long getAwardId() {
        return awardId;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public String getAwardType() {
        return awardType;
    }
}

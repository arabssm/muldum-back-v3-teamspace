package co.kr.muldum.domain.model;

public class HistoryContributor {
    private final Long contributorId;
    private final Long historyId;
    private final String name;
    private final String githubUrl;
    private final Integer sortOrder;

    private HistoryContributor(Long contributorId, Long historyId, String name,
                               String githubUrl, Integer sortOrder) {
        this.contributorId = contributorId;
        this.historyId = validateHistoryId(historyId);
        this.name = validateName(name);
        this.githubUrl = githubUrl;
        this.sortOrder = sortOrder != null ? sortOrder : 0;
    }

    public static HistoryContributor create(Long historyId, String name,
                                            String githubUrl, Integer sortOrder) {
        return new HistoryContributor(null, historyId, name, githubUrl, sortOrder);
    }

    public static HistoryContributor of(Long contributorId, Long historyId, String name,
                                        String githubUrl, Integer sortOrder) {
        return new HistoryContributor(contributorId, historyId, name, githubUrl, sortOrder);
    }

    private Long validateHistoryId(Long historyId) {
        if (historyId == null) {
            throw new IllegalArgumentException("History ID cannot be null");
        }
        return historyId;
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Contributor name cannot be null or empty");
        }
        return name;
    }

    public HistoryContributor withId(Long contributorId) {
        return new HistoryContributor(contributorId, this.historyId, this.name,
                this.githubUrl, this.sortOrder);
    }

    public Long getContributorId() {
        return contributorId;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public String getName() {
        return name;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }
}

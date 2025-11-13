package co.kr.muldum.adapter.out.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "history_contributors")
public class HistoryContributorJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "history_id", nullable = false)
    private Long historyId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "github_url", columnDefinition = "TEXT")
    private String githubUrl;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id", insertable = false, updatable = false)
    private HistoryJpaEntity history;

    protected HistoryContributorJpaEntity() {
    }

    public HistoryContributorJpaEntity(Long id, Long historyId, String name,
                                       String githubUrl, Integer sortOrder) {
        this.id = id;
        this.historyId = historyId;
        this.name = name;
        this.githubUrl = githubUrl;
        this.sortOrder = sortOrder;
    }

    public Long getId() {
        return id;
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

    public HistoryJpaEntity getHistory() {
        return history;
    }
}

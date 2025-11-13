package co.kr.muldum.adapter.out.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "history_awards")
public class HistoryAwardJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "history_id", nullable = false)
    private Long historyId;

    @Column(name = "award_type", nullable = false, columnDefinition = "TEXT")
    private String awardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id", insertable = false, updatable = false)
    private HistoryJpaEntity history;

    protected HistoryAwardJpaEntity() {
    }

    public HistoryAwardJpaEntity(Long id, Long historyId, String awardType) {
        this.id = id;
        this.historyId = historyId;
        this.awardType = awardType;
    }

    public Long getId() {
        return id;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public String getAwardType() {
        return awardType;
    }

    public HistoryJpaEntity getHistory() {
        return history;
    }
}

package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.domain.model.MonthReportStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "monthly_report")
public class MonthlyReportJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> report;

    @Column(name = "team_id", nullable = false, columnDefinition = "uuid")
    private UUID teamId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MonthReportStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    private TeamJpaEntity team;

    protected MonthlyReportJpaEntity() {
    }

    public MonthlyReportJpaEntity(Long id, Map<String, Object> report, UUID teamId,
                                  MonthReportStatus status, LocalDateTime createdAt,
                                  LocalDateTime updatedAt) {
        this.id = id;
        this.report = report;
        this.teamId = teamId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = MonthReportStatus.DRAFT;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Map<String, Object> getReport() {
        return report;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public MonthReportStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public TeamJpaEntity getTeam() {
        return team;
    }

    public void updateReport(Map<String, Object> report) {
        this.report = report;
    }

    public void updateStatus(MonthReportStatus status) {
        this.status = status;
    }
}

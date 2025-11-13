package co.kr.muldum.domain.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class MonthlyReport {
    private final Long reportId;
    private final Map<String, Object> report;
    private final UUID teamId;
    private final MonthReportStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private MonthlyReport(Long reportId, Map<String, Object> report, UUID teamId,
                          MonthReportStatus status, LocalDateTime createdAt,
                          LocalDateTime updatedAt) {
        this.reportId = reportId;
        this.report = report;
        this.teamId = validateTeamId(teamId);
        this.status = status != null ? status : MonthReportStatus.DRAFT;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MonthlyReport create(Map<String, Object> report, UUID teamId) {
        return new MonthlyReport(null, report, teamId, MonthReportStatus.DRAFT,
                LocalDateTime.now(), LocalDateTime.now());
    }

    public static MonthlyReport of(Long reportId, Map<String, Object> report, UUID teamId,
                                   MonthReportStatus status, LocalDateTime createdAt,
                                   LocalDateTime updatedAt) {
        return new MonthlyReport(reportId, report, teamId, status, createdAt, updatedAt);
    }

    private UUID validateTeamId(UUID teamId) {
        if (teamId == null) {
            throw new IllegalArgumentException("Team ID cannot be null");
        }
        return teamId;
    }

    public MonthlyReport withId(Long reportId) {
        return new MonthlyReport(reportId, this.report, this.teamId, this.status,
                this.createdAt, this.updatedAt);
    }

    public MonthlyReport withReport(Map<String, Object> report) {
        return new MonthlyReport(this.reportId, report, this.teamId, this.status,
                this.createdAt, LocalDateTime.now());
    }

    public MonthlyReport withStatus(MonthReportStatus status) {
        return new MonthlyReport(this.reportId, this.report, this.teamId, status,
                this.createdAt, LocalDateTime.now());
    }

    public MonthlyReport submit() {
        return withStatus(MonthReportStatus.SUBMIT);
    }

    public Long getReportId() {
        return reportId;
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

    public boolean isDraft() {
        return status == MonthReportStatus.DRAFT;
    }

    public boolean isSubmitted() {
        return status == MonthReportStatus.SUBMIT;
    }
}

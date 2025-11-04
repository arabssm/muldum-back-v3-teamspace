package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.domain.model.LogStatus;
import co.kr.muldum.domain.model.TeamSpaceLogMethod;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "teamspace_log")
public class TeamSpaceLogJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long logId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private LogStatus status;

    @Column(name = "logged_by", nullable = false)
    private Long loggedBy;

    @Column(name = "logged_at", nullable = false)
    private LocalDateTime loggedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false, length = 50)
    private TeamSpaceLogMethod method;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    protected TeamSpaceLogJpaEntity() {
    }

    public TeamSpaceLogJpaEntity(Long logId, LogStatus status, Long loggedBy, LocalDateTime loggedAt,
                                 TeamSpaceLogMethod method, String title, String content) {
        this.logId = logId;
        this.status = status;
        this.loggedBy = loggedBy;
        this.loggedAt = loggedAt;
        this.method = method;
        this.title = title;
        this.content = content;
    }

    public Long getLogId() {
        return logId;
    }

    public LogStatus getStatus() {
        return status;
    }

    public Long getLoggedBy() {
        return loggedBy;
    }

    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }

    public TeamSpaceLogMethod getMethod() {
        return method;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
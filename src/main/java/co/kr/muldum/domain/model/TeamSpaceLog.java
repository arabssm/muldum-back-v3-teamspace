package co.kr.muldum.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TeamSpaceLog {

    private final Long logId;
    private final LogStatus status;
    private final Long loggedBy;
    private final LocalDateTime loggedAt;
    private final TeamSpaceLogMethod method;
    private final String title;
    private final String content;

    private TeamSpaceLog(Long logId, LogStatus status, Long loggedBy, LocalDateTime loggedAt,
                         TeamSpaceLogMethod method, String title, String content) {
        this.logId = logId;
        this.status = status;
        this.loggedBy = loggedBy;
        this.loggedAt = loggedAt;
        this.method = method;
        this.title = title;
        this.content = content;
    }

    public static TeamSpaceLog create(LogStatus status, Long loggedBy, TeamSpaceLogMethod method,
                                      String title, String content) {
        return new TeamSpaceLog(null, status, loggedBy, LocalDateTime.now(), method, title, content);
    }

    public static TeamSpaceLog of(Long logId, LogStatus status, Long loggedBy, LocalDateTime loggedAt,
                                  TeamSpaceLogMethod method, String title, String content) {
        return new TeamSpaceLog(logId, status, loggedBy, loggedAt, method, title, content);
    }
}
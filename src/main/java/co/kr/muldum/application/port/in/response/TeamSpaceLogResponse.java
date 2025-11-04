package co.kr.muldum.application.port.in.response;

import co.kr.muldum.domain.model.LogStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TeamSpaceLogResponse {

    private final LogStatus status;
    private final Long loggedBy;
    private final LocalDateTime loggedAt;
    private final LogContents logContents;

    private TeamSpaceLogResponse(LogStatus status, Long loggedBy, LocalDateTime loggedAt, LogContents logContents) {
        this.status = status;
        this.loggedBy = loggedBy;
        this.loggedAt = loggedAt;
        this.logContents = logContents;
    }

    public static TeamSpaceLogResponse of(LogStatus status, Long loggedBy, LocalDateTime loggedAt,
                                          String title, String content) {
        return new TeamSpaceLogResponse(status, loggedBy, loggedAt, new LogContents(title, content));
    }

    @Getter
    public static class LogContents {
        private final String title;
        private final String content;

        public LogContents(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}
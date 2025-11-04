package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.domain.model.TeamSpaceLog;
import org.springframework.stereotype.Component;

@Component
public class TeamSpaceLogMapper {

    public TeamSpaceLogJpaEntity toEntity(TeamSpaceLog log) {
        return new TeamSpaceLogJpaEntity(
                log.getLogId(),
                log.getStatus(),
                log.getLoggedBy(),
                log.getLoggedAt(),
                log.getMethod(),
                log.getTitle(),
                log.getContent()
        );
    }

    public TeamSpaceLog toDomain(TeamSpaceLogJpaEntity entity) {
        return TeamSpaceLog.of(
                entity.getLogId(),
                entity.getStatus(),
                entity.getLoggedBy(),
                entity.getLoggedAt(),
                entity.getMethod(),
                entity.getTitle(),
                entity.getContent()
        );
    }
}
package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.application.port.out.LogManagementPort;
import co.kr.muldum.domain.model.TeamSpaceLog;
import co.kr.muldum.domain.model.TeamSpaceLogMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogPersistenceAdapter implements LogManagementPort {

    private final TeamSpaceLogJpaRepository logRepository;
    private final TeamSpaceLogMapper logMapper;

    public LogPersistenceAdapter(TeamSpaceLogJpaRepository logRepository, TeamSpaceLogMapper logMapper) {
        this.logRepository = logRepository;
        this.logMapper = logMapper;
    }

    @Override
    public TeamSpaceLog saveLog(TeamSpaceLog log) {
        TeamSpaceLogJpaEntity entity = logMapper.toEntity(log);
        TeamSpaceLogJpaEntity savedEntity = logRepository.save(entity);
        return logMapper.toDomain(savedEntity);
    }

    @Override
    public List<TeamSpaceLog> findAllLogs() {
        return logRepository.findAllByOrderByLoggedAtDesc().stream()
                .map(logMapper::toDomain)
                .toList();
    }

    @Override
    public List<TeamSpaceLog> findLogsByMethod(TeamSpaceLogMethod method) {
        return logRepository.findAllByMethodOrderByLoggedAtDesc(method).stream()
                .map(logMapper::toDomain)
                .toList();
    }
}
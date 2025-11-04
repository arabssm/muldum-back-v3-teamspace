package co.kr.muldum.application.service;

import co.kr.muldum.application.port.in.GetTeamSpaceLogsUseCase;
import co.kr.muldum.application.port.in.response.TeamSpaceLogResponse;
import co.kr.muldum.application.port.out.LogManagementPort;
import co.kr.muldum.domain.exception.InvalidParameterException;
import co.kr.muldum.domain.model.TeamSpaceLog;
import co.kr.muldum.domain.model.TeamSpaceLogMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetTeamSpaceLogsService implements GetTeamSpaceLogsUseCase {

    private final LogManagementPort logManagementPort;

    public GetTeamSpaceLogsService(LogManagementPort logManagementPort) {
        this.logManagementPort = logManagementPort;
    }

    @Override
    public List<TeamSpaceLogResponse> getTeamSpaceLogs(String method) {
        List<TeamSpaceLog> logs;

        if (method == null || method.trim().isEmpty()) {
            logs = logManagementPort.findAllLogs();
        } else {
            TeamSpaceLogMethod logMethod = validateMethod(method);
            logs = logManagementPort.findLogsByMethod(logMethod);
        }

        return logs.stream()
                .map(log -> TeamSpaceLogResponse.of(
                        log.getStatus(),
                        log.getLoggedBy(),
                        log.getLoggedAt(),
                        log.getTitle(),
                        log.getContent()
                ))
                .toList();
    }

    private TeamSpaceLogMethod validateMethod(String method) {
        try {
            return TeamSpaceLogMethod.fromCode(method.trim());
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("로그 메소드 타입", method);
        }
    }
}

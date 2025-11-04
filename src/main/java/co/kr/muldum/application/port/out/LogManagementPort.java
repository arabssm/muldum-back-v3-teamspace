package co.kr.muldum.application.port.out;

import co.kr.muldum.domain.model.TeamSpaceLog;
import co.kr.muldum.domain.model.TeamSpaceLogMethod;

import java.util.List;

public interface LogManagementPort {

    TeamSpaceLog saveLog(TeamSpaceLog log);

    List<TeamSpaceLog> findAllLogs();

    List<TeamSpaceLog> findLogsByMethod(TeamSpaceLogMethod method);
}
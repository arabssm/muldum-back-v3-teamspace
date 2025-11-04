package co.kr.muldum.application.port.in;

import co.kr.muldum.application.port.in.response.TeamSpaceLogResponse;

import java.util.List;

public interface GetTeamSpaceLogsUseCase {

    List<TeamSpaceLogResponse> getTeamSpaceLogs(String method);
}
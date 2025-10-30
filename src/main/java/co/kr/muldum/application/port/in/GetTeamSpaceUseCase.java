package co.kr.muldum.application.port.in;

import co.kr.muldum.application.port.in.response.TeamSpaceListResponse;

public interface GetTeamSpaceUseCase {
    TeamSpaceListResponse getTeamSpace();
}
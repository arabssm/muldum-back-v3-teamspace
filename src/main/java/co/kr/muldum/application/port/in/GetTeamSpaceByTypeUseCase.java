package co.kr.muldum.application.port.in;

import co.kr.muldum.application.port.in.response.TeamSpaceContentListResponse;

public interface GetTeamSpaceByTypeUseCase {
    TeamSpaceContentListResponse getTeamSpaceByType(String type);
}
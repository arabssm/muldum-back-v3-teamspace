package co.kr.muldum.application.port.in;

import co.kr.muldum.application.port.in.response.TeamPageDetailResponse;

public interface GetTeamPageUseCase {
    TeamPageDetailResponse getTeamPage(Long teamId);
}

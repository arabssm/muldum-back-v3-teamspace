package co.kr.muldum.application.port.in;

import co.kr.muldum.application.port.in.response.TeamPageDetailResponse;

import java.util.UUID;

public interface GetTeamPageUseCase {
    TeamPageDetailResponse getTeamPage(UUID teamId);
}

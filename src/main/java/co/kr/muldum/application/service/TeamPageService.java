package co.kr.muldum.application.service;

import co.kr.muldum.application.port.in.GetTeamPageUseCase;
import co.kr.muldum.application.port.in.response.TeamPageDetailResponse;
import co.kr.muldum.domain.exception.InvalidParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TeamPageService implements GetTeamPageUseCase {

    private static final Logger log = LoggerFactory.getLogger(TeamPageService.class);

    @Override
    public TeamPageDetailResponse getTeamPage(Long teamId) {
        log.info("Fetching team page for teamId: {}", teamId);

        if (teamId == null || teamId <= 0) {
            log.error("Invalid team ID: {}", teamId);
            throw new InvalidParameterException("팀 ID", String.valueOf(teamId));
        }

        String content = "우리 동아리는 전공동아리를 더 편리하게 사용할 수 있도록 하는 동아리입니다. 물품 신청, 공지, 역대 전공동아리 등을 ...";

        log.info("Successfully fetched team page for teamId: {}", teamId);
        return TeamPageDetailResponse.of(teamId, content);
    }
}

package co.kr.muldum.application.service;

import co.kr.muldum.application.port.in.GetTeamPageUseCase;
import co.kr.muldum.application.port.in.response.TeamPageDetailResponse;
import co.kr.muldum.domain.exception.InvalidParameterException;
import co.kr.muldum.domain.exception.TeamNotFoundException;
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

        // TODO: 실제 DB에서 팀 페이지 데이터를 조회하도록 수정 필요
        // 현재는 더미 데이터를 반환합니다.

        // 유효하지 않은 팀 ID인 경우 예외 발생
        if (teamId == null || teamId <= 0) {
            log.error("Invalid team ID: {}", teamId);
            throw new InvalidParameterException("팀 ID", String.valueOf(teamId));
        }

        // TODO: 실제로는 DB에서 팀이 존재하는지 확인하고, 존재하지 않으면 TeamNotFoundException 발생
        // Example: Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        // 더미 데이터 (실제 구현 시 DB에서 조회)
        String content = "우리 동아리는 전공동아리를 더 편리하게 사용할 수 있도록 하는 동아리입니다. 물품 신청, 공지, 역대 전공동아리 등을 ...";

        log.info("Successfully fetched team page for teamId: {}", teamId);
        return TeamPageDetailResponse.of(teamId, content);
    }
}
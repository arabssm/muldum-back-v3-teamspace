package co.kr.muldum.application.service;

import co.kr.muldum.application.port.in.UpdateTeamPageUseCase;
import co.kr.muldum.domain.exception.InvalidParameterException;
import co.kr.muldum.domain.model.TeamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateTeamPageService implements UpdateTeamPageUseCase {

    private static final Logger log = LoggerFactory.getLogger(UpdateTeamPageService.class);

    @Override
    public void updateTeamPage(Long teamId, String teamType, String content) {
        log.info("Updating team page for teamId: {}, teamType: {}", teamId, teamType);

        if (teamId == null || teamId <= 0) {
            log.error("Invalid team ID: {}", teamId);
            throw new InvalidParameterException("팀 ID", String.valueOf(teamId));
        }

        try {
            TeamType.fromCode(teamType);
        } catch (IllegalArgumentException e) {
            log.error("Invalid team type: {}", teamType);
            throw new InvalidParameterException("팀 타입", teamType);
        }

        if (content == null || content.trim().isEmpty()) {
            log.error("Content is empty for teamId: {}", teamId);
            throw new InvalidParameterException("팀 소개 내용은 비어있을 수 없습니다.");
        }

        log.info("Successfully updated team page for teamId: {}", teamId);
    }
}

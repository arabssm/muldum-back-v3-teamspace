package co.kr.muldum.application.service;

import co.kr.muldum.application.port.in.UpdateTeamIconUseCase;
import co.kr.muldum.application.port.out.TeamManagementPort;
import co.kr.muldum.domain.exception.InvalidParameterException;
import co.kr.muldum.domain.model.TeamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UpdateTeamIconService implements UpdateTeamIconUseCase {

    private static final Logger log = LoggerFactory.getLogger(UpdateTeamIconService.class);

    private final TeamManagementPort teamManagementPort;

    public UpdateTeamIconService(TeamManagementPort teamManagementPort) {
        this.teamManagementPort = teamManagementPort;
    }

    @Override
    public void updateTeamIcon(UUID teamId, String teamType, String iconUrl) {
        log.info("Updating team icon: teamId={}, teamType={}", teamId, teamType);

        if (teamId == null) {
            log.error("Invalid team ID: {}", teamId);
            throw new InvalidParameterException("팀 ID", String.valueOf(teamId));
        }

        TeamType type;
        try {
            type = TeamType.fromCode(teamType);
        } catch (IllegalArgumentException e) {
            log.error("Invalid team type: {}", teamType);
            throw new InvalidParameterException("팀 타입", teamType);
        }

        if (iconUrl == null || iconUrl.trim().isEmpty()) {
            log.error("Invalid icon URL for teamId: {}", teamId);
            throw new InvalidParameterException("아이콘 URL은 필수입니다.");
        }

        teamManagementPort.updateTeamIcon(teamId, type, iconUrl.trim());
        log.info("Successfully updated team icon for teamId: {}", teamId);
    }
}

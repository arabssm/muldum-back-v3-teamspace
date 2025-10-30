package co.kr.muldum.application.service;

import co.kr.muldum.application.port.in.UpdateBannerUseCase;
import co.kr.muldum.domain.exception.InvalidParameterException;
import co.kr.muldum.domain.model.TeamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateBannerService implements UpdateBannerUseCase {

    private static final Logger log = LoggerFactory.getLogger(UpdateBannerService.class);

    @Override
    public void updateBanner(Long teamId, String type, String url) {
        log.info("Updating banner for teamId: {}, type: {}", teamId, type);

        if (teamId == null || teamId <= 0) {
            log.error("Invalid team ID: {}", teamId);
            throw new InvalidParameterException("팀 ID", String.valueOf(teamId));
        }

        try {
            TeamType.fromCode(type);
        } catch (IllegalArgumentException e) {
            log.error("Invalid team type: {}", type);
            throw new InvalidParameterException("팀 타입", type);
        }

        if (url == null || url.trim().isEmpty()) {
            log.error("Invalid banner URL for teamId: {}", teamId);
            throw new InvalidParameterException("배너 이미지 URL은 필수입니다.");
        }

        log.info("Successfully updated banner for teamId: {}", teamId);
    }
}

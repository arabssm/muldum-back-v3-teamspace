package co.kr.muldum.application.port.in;

import java.util.UUID;

public interface UpdateTeamPageUseCase {
    void updateTeamPage(UUID teamId, String teamType, String content);
}

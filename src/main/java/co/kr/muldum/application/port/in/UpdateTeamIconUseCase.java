package co.kr.muldum.application.port.in;

import java.util.UUID;

public interface UpdateTeamIconUseCase {

    void updateTeamIcon(UUID teamId, String teamType, String iconUrl);
}

package co.kr.muldum.application.port.in;

public interface UpdateTeamIconUseCase {

    void updateTeamIcon(Long teamId, String teamType, String iconUrl);
}

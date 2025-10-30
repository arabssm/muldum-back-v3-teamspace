package co.kr.muldum.application.port.in;

public interface UpdateTeamPageUseCase {
    void updateTeamPage(Long teamId, String teamType, String content);
}

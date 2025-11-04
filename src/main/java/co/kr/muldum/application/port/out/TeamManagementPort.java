package co.kr.muldum.application.port.out;

import co.kr.muldum.domain.model.Member;
import co.kr.muldum.domain.model.Team;
import co.kr.muldum.domain.model.TeamType;

import java.util.List;
import java.util.Optional;

public interface TeamManagementPort {

    Optional<Team> findByNameAndType(String name, TeamType type);

    Team saveTeam(Team team);

    void replaceMembers(Long teamId, List<Member> members);

    void updateTeamIcon(Long teamId, TeamType expectedType, String iconUrl);
}

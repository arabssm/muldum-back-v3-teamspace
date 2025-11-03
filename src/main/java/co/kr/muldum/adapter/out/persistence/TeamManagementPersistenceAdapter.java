package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.application.port.out.TeamManagementPort;
import co.kr.muldum.domain.exception.TeamNotFoundException;
import co.kr.muldum.domain.model.Member;
import co.kr.muldum.domain.model.Team;
import co.kr.muldum.domain.model.TeamType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class TeamManagementPersistenceAdapter implements TeamManagementPort {

    private final TeamJpaRepository teamJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public TeamManagementPersistenceAdapter(TeamJpaRepository teamJpaRepository,
                                           MemberJpaRepository memberJpaRepository,
                                           UserJpaRepository userJpaRepository) {
        this.teamJpaRepository = teamJpaRepository;
        this.memberJpaRepository = memberJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Team> findByNameAndType(String name, TeamType type) {
        return teamJpaRepository.findByNameAndTeamType(name, type)
                .map(TeamMapper::toDomain);
    }

    @Override
    public Team saveTeam(Team team) {
        TeamJpaEntity entity = TeamMapper.toEntity(team);
        TeamJpaEntity saved = teamJpaRepository.save(entity);
        return TeamMapper.toDomain(saved);
    }

    @Override
    public void replaceMembers(Long teamId, List<Member> members) {
        TeamJpaEntity team = teamJpaRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        memberJpaRepository.deleteByTeamId(teamId);

        for (Member member : members) {
            UserJpaEntity userReference = userJpaRepository.getReferenceById(member.getUserId());
            MemberJpaEntity memberEntity = new MemberJpaEntity(
                    member.getMemberId(),
                    team,
                    userReference,
                    member.getRole()
            );
            memberJpaRepository.save(memberEntity);
        }
    }
}

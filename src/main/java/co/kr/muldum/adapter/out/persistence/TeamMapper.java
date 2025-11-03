package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.domain.model.Member;
import co.kr.muldum.domain.model.Team;

import java.util.List;
import java.util.stream.Collectors;

public final class TeamMapper {

    private TeamMapper() {
    }

    public static Team toDomain(TeamJpaEntity entity) {
        return Team.of(
                entity.getTeamId(),
                entity.getName(),
                entity.getTeamType(),
                entity.getReadme()
        );
    }

    public static TeamJpaEntity toEntity(Team team) {
        return new TeamJpaEntity(
                team.getTeamId(),
                team.getName(),
                team.getTeamType(),
                team.getReadme()
        );
    }

    public static Member toMemberDomain(MemberJpaEntity entity) {
        return Member.of(
                entity.getMemberId(),
                entity.getTeam().getTeamId(),
                entity.getUser().getUserId(),
                entity.getRole()
        );
    }

    public static List<Member> toMemberDomainList(List<MemberJpaEntity> entities) {
        return entities.stream()
                .map(TeamMapper::toMemberDomain)
                .collect(Collectors.toList());
    }
}

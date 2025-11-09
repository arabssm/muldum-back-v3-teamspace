package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.domain.model.TeamType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeamJpaRepository extends JpaRepository<TeamJpaEntity, UUID> {

    Optional<TeamJpaEntity> findByNameAndTeamType(String name, TeamType teamType);
}

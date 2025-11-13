package co.kr.muldum.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PageJpaRepository extends JpaRepository<PageJpaEntity, Long> {

    Optional<PageJpaEntity> findByTeamId(UUID teamId);
}

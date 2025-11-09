package co.kr.muldum.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM MemberJpaEntity m WHERE m.team.teamId = :teamId")
    void deleteByTeamId(@Param("teamId") UUID teamId);

    @Query("SELECT m FROM MemberJpaEntity m WHERE m.user.userId = :userId")
    Optional<MemberJpaEntity> findByUserId(@Param("userId") Long userId);
}

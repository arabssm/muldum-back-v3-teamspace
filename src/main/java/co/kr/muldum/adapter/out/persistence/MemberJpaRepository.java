package co.kr.muldum.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM MemberJpaEntity m WHERE m.team.teamId = :teamId")
    void deleteByTeamId(@Param("teamId") Long teamId);
}

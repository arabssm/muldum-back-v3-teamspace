package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.domain.model.TeamSpaceLogMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamSpaceLogJpaRepository extends JpaRepository<TeamSpaceLogJpaEntity, Long> {

    List<TeamSpaceLogJpaEntity> findAllByOrderByLoggedAtDesc();

    List<TeamSpaceLogJpaEntity> findAllByMethodOrderByLoggedAtDesc(TeamSpaceLogMethod method);
}
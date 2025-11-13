package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.domain.model.MonthReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MonthlyReportJpaRepository extends JpaRepository<MonthlyReportJpaEntity, Long> {

    List<MonthlyReportJpaEntity> findByTeamId(UUID teamId);

    List<MonthlyReportJpaEntity> findByStatus(MonthReportStatus status);

    Optional<MonthlyReportJpaEntity> findByTeamIdAndStatus(UUID teamId, MonthReportStatus status);
}

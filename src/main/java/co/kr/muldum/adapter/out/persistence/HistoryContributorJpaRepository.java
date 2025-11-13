package co.kr.muldum.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryContributorJpaRepository extends JpaRepository<HistoryContributorJpaEntity, Long> {

    List<HistoryContributorJpaEntity> findByHistoryIdOrderBySortOrderAsc(Long historyId);
}

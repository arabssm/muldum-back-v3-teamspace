package co.kr.muldum.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryAwardJpaRepository extends JpaRepository<HistoryAwardJpaEntity, Long> {

    List<HistoryAwardJpaEntity> findByHistoryId(Long historyId);
}

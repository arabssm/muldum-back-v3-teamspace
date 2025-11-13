package co.kr.muldum.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryJpaRepository extends JpaRepository<HistoryJpaEntity, Long> {

    List<HistoryJpaEntity> findByGeneration(int generation);

    Optional<HistoryJpaEntity> findByNameAndGeneration(String name, int generation);

    List<HistoryJpaEntity> findAllByOrderByGenerationDesc();
}

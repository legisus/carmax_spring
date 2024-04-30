package carmax.version001.repository;

import carmax.version001.model.Estimation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstimationRepository extends JpaRepository<Estimation, Long> {
    void delete(Estimation estimation);
    Optional<Estimation> findById(Long id);
}

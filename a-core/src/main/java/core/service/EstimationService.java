package core.service;

import core.model.Estimation;

import java.util.Optional;

public interface EstimationService {

    boolean addEstimation(Estimation estimation);

    boolean deleteEstimation(Long id);

    Optional<Estimation> getEstimationById(Long id);

    boolean updateEstimation(Estimation estimation);
}

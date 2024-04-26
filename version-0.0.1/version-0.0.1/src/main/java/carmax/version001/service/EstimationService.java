package carmax.version001.service;

import carmax.version001.model.Estimation;

import java.util.Optional;

public interface EstimationService {

    boolean addEstimation(Estimation estimation);

    boolean deleteEstimation(Long id);

    Optional<Estimation> getEstimationById(Long id);

    boolean updateEstimation(Estimation estimation);
}

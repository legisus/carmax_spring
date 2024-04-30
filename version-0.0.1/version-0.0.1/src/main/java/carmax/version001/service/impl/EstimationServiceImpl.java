package carmax.version001.service.impl;

import carmax.version001.model.Estimation;
import carmax.version001.repository.EstimationRepository;
import carmax.version001.service.EstimationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstimationServiceImpl implements EstimationService {

    private final EstimationRepository estimationRepository;

    @Autowired
    public EstimationServiceImpl(EstimationRepository estimationRepository) {
        this.estimationRepository = estimationRepository;
    }

    @Override
    public boolean addEstimation(Estimation estimation) {
        estimationRepository.save(estimation);
        return true;
    }

    @Override
    public boolean deleteEstimation(Long id) {
        estimationRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Estimation> getEstimationById(Long id) {
        return estimationRepository.findById(id);
    }

    @Override
    public boolean updateEstimation(Estimation estimation) {
        estimationRepository.save(estimation);
        return true;
    }
}

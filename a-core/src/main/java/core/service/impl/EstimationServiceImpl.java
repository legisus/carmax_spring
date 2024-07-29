package core.service.impl;

import core.model.Estimation;
import core.repository.EstimationRepository;
import core.service.EstimationService;
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
    public Estimation addOrUpdateEstimation(Estimation estimation) {
        if (estimation.getId() != null) {
            Optional<Estimation> existingEstimation = estimationRepository.findById(estimation.getId());
            if (existingEstimation.isPresent()) {
                Estimation updatedEstimation = mergeEstimations(existingEstimation.get(), estimation);
                return estimationRepository.save(updatedEstimation);
            }
        }
        return estimationRepository.save(estimation);
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


    private Estimation mergeEstimations(Estimation existing, Estimation newEstimation) {
        if (newEstimation.getEstimationJDPower() != null) {
            existing.setEstimationJDPower(newEstimation.getEstimationJDPower());
        }
        if (newEstimation.getEstimationKBBPrivateParty() != null) {
            existing.setEstimationKBBPrivateParty(newEstimation.getEstimationKBBPrivateParty());
        }
        if (newEstimation.getEstimationKBBDealerRetail() != null) {
            existing.setEstimationKBBDealerRetail(newEstimation.getEstimationKBBDealerRetail());
        }
        if (newEstimation.getEstimationManheimMMR() != null) {
            existing.setEstimationManheimMMR(newEstimation.getEstimationManheimMMR());
        }
        if (newEstimation.getEstimatedRetailValue() != null) {
            existing.setEstimatedRetailValue(newEstimation.getEstimatedRetailValue());
        }
        return existing;
    }
}

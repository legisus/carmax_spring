package scanner.dispetchers.external;

import core.model.Estimation;
import core.repository.EstimationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scanner.defenitionSteps.MainheimScraper;

import java.util.List;

@Slf4j
@Component
public class MmrRunDisp {
    private final MainheimScraper mmr;
    private final EstimationRepository estimationRepository;  // Inject the repository

    @Autowired
    public MmrRunDisp(MainheimScraper mmr, EstimationRepository estimationRepository) {
        this.mmr = mmr;
        this.estimationRepository = estimationRepository;
    }

    @Transactional  // Ensure transactional integrity
    public void run() {
        List<Estimation> estimations = estimationRepository.findAll();  // Fetch all estimations
        for (Estimation estimation : estimations) {
            updateMmrAndRetailEstimation(estimation);  // Update logic
            estimationRepository.save(estimation);  // Save updated estimation
        }
    }

    private void updateMmrAndRetailEstimation(Estimation estimation) {
//        // Example update logic, adjust according to how you get new MMR data
//        Integer newMmrValue = mmr.fetchMmrValue();  // Hypothetical method to fetch new MMR value
//        estimation.setEstimationManheimMMR(newMmrValue);
//        log.info("Updated MMR for estimation ID {}: {}", estimation.getId(), newMmrValue);
    }
}

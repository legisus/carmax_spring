package scanner.dispetchers.external;

import core.model.Car;
import core.model.Estimation;
import core.service.CarService;
import core.service.EstimationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scanner.defenitionSteps.JdpScraper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JdpRunDisp {
    private final JdpScraper jdp;
    private final CarService carService;
    private final EstimationService estimationService;

    @Autowired
    public JdpRunDisp(JdpScraper jdp, CarService carService, EstimationService estimationService) {
        this.jdp = jdp;
        this.carService = carService;
        this.estimationService = estimationService;
    }

    public void run() {
        List<Car> carsWithoutJdpEstimation = carService.getAll().stream()
                .filter(car -> car.getEstimation() == null || car.getEstimation().getEstimationJDPower() == null || car.getEstimation().getEstimationJDPower() < 1)
                .collect(Collectors.toList());

        if (carsWithoutJdpEstimation.isEmpty()) {
            log.info("No JDP estimations need updating.");
            return;
        }

        log.info("Updating JDP estimations for {} cars.", carsWithoutJdpEstimation.size());

        jdp.goToJdpEstimatePage();
        jdp.switchIframe();

        for (Car car : carsWithoutJdpEstimation) {
            try {
                String roughValueString = jdp.enterJdpEstimateData(car.getVin(), String.valueOf(car.getMileage()));
                Integer roughValue = Integer.parseInt(roughValueString.replaceAll("[^\\d]", ""));

                Estimation estimation = car.getEstimation();
                if (estimation == null) {
                    estimation = new Estimation();
                    car.setEstimation(estimation);
                }

                estimation.setEstimationJDPower(roughValue);

                estimationService.addOrUpdateEstimation(estimation);
                carService.addOrUpdateCar(car);

                log.info("Successfully updated JDP estimation for VIN: {}", car.getVin());
            } catch (Exception e) {
                log.error("Failed to update JDP estimation for VIN: {}", car.getVin(), e);
            }
        }

        jdp.switchIframeDefault();
    }
}
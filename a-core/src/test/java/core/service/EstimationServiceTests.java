package core.service;

import core.model.Estimation;
import core.repository.EstimationRepository;
import core.service.impl.EstimationServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EstimationServiceTests {

    @Mock
    private EstimationRepository estimationRepository;

    @InjectMocks
    private EstimationServiceImpl estimationService;

    @Test
    @Order(1)
    void testAddEstimation() {
        Estimation estimation = new Estimation();
        when(estimationRepository.save(estimation)).thenReturn(estimation);
        boolean result = estimationService.addEstimation(estimation);
        Assertions.assertTrue(result);
    }

    @Test
    @Order(2)
    void testDeleteEstimation() {
        Long id = 1L;
        doNothing().when(estimationRepository).deleteById(id);
        boolean result = estimationService.deleteEstimation(id);
        Assertions.assertTrue(result);
    }

    @Test
    @Order(3)
    void testGetEstimationById() {
        Long id = 1L;
        Estimation estimation = new Estimation();
        when(estimationRepository.findById(id)).thenReturn(Optional.of(estimation));
        Optional<Estimation> result = estimationService.getEstimationById(id);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(estimation, result.get());
    }

    @Test
    @Order(4)
    void testUpdateEstimation() {
        Estimation estimation = new Estimation();
        when(estimationRepository.save(estimation)).thenReturn(estimation);
        boolean result = estimationService.updateEstimation(estimation);
        Assertions.assertTrue(result);
    }
}
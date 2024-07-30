package core.service;

import core.model.User;
import core.model.Balance;
import core.repository.BalanceRepository;
import core.service.impl.BalanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class BalanceServiceTests {

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private BalanceServiceImpl balanceService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Balance balance = new Balance();
        when(balanceRepository.save(any(Balance.class))).thenReturn(balance);

        balanceService.save(balance);

        verify(balanceRepository, times(1)).save(balance);
    }

    @Test
    void testFindById() {
        Balance balance = new Balance();
        when(balanceRepository.findById(any(Long.class))).thenReturn(Optional.of(balance));

        balanceService.findById(1L);

        verify(balanceRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteById() {
        doNothing().when(balanceRepository).deleteById(any(Long.class));

        balanceService.deleteById(1L);

        verify(balanceRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByUser() {
        User user = new User();
        Balance balance = new Balance();
        balance.setUser(user);
        when(balanceRepository.findByUser(any(User.class))).thenReturn(Optional.of(balance));

        balanceService.findByUser(user);

        verify(balanceRepository, times(1)).findByUser(user);
    }
}

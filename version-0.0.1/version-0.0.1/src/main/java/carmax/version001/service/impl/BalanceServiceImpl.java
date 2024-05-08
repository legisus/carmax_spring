package carmax.version001.service.impl;

import carmax.version001.model.Balance;
import carmax.version001.model.User;
import carmax.version001.repository.BalanceRepository;
import carmax.version001.service.BalanceService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceServiceImpl(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public Balance save(Balance balance) {
        return balanceRepository.save(balance);
    }

    @Override
    public Optional<Balance> findById(Long id) {
        return balanceRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        balanceRepository.deleteById(id);
    }

    @Override
    public Optional<Balance> findByUser(User user) {
        return balanceRepository.findByUser(user);
    }
}
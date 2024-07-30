package core.service.impl;

import core.model.User;
import core.model.Balance;
import core.repository.BalanceRepository;
import core.service.BalanceService;
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
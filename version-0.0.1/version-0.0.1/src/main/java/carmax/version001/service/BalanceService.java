package carmax.version001.service;

import carmax.version001.model.Balance;
import carmax.version001.model.User;

import java.util.Optional;

public interface BalanceService {
    Balance save(Balance balance);
    Optional<Balance> findById(Long id);
    void deleteById(Long id);
    Optional<Balance> findByUser(User user);
}

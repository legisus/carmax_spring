package core.service;

import core.model.Balance;
import core.model.User;

import java.util.Optional;

public interface BalanceService {
    Balance save(Balance balance);
    Optional<Balance> findById(Long id);
    void deleteById(Long id);
    Optional<Balance> findByUser(User user);
}

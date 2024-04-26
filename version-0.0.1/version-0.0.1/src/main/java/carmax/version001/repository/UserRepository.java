package carmax.version001.repository;

import carmax.version001.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    User update(User user);

    void delete(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);


}

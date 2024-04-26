package carmax.version001.service.impl;

import carmax.version001.model.User;
import carmax.version001.repository.UserRepository;
import carmax.version001.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            return null;
        } else {
            return userRepository.save(user);
        }
    }

    @Override
    public User update(User user) {
        String email = user.getEmail();
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            if (Objects.equals(existingUser.get().getEmail(), user.getEmail())) {
                return userRepository.save(user);
            } else {
                return create(user);
            }
        } else {
            return create(user);
        }
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User readById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User readByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}

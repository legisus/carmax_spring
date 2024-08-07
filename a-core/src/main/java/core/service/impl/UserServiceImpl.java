package core.service.impl;

import core.model.User;
import core.service.UserService;
import core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
//@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.findByEmail(user.getEmail())
                .orElseGet(() -> userRepository.save(user));
    }

    @Override
    public User update(User user) {
        return userRepository.findById(user.getId())
                .map(existingUser -> {
                    if (!existingUser.getEmail().equals(user.getEmail()) && userRepository.findByEmail(user.getEmail()).isPresent()) {
                        throw new RuntimeException("Email already in use by another user.");
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + user.getId()));
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

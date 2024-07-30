package core.service;

import core.model.User;

import java.util.List;

public interface UserService {

    User create(User user);

    User update(User user);

    void delete(Long id);

    List<User> getAll();

    User readById(long id);

    User readByEmail(String email);
}

package core.service;

import core.model.Role;
import core.model.User;
import core.service.RoleService;
import core.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTests {

    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public UserServiceTests(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Test
    @Order(1)
    void createRoleTest() {
        Role role = new Role();
        role.setName("USER");
        roleService.create(role);
        Role retrievedRole = roleService.readById(1);
        assertEquals(role, retrievedRole, "Created role should match retrieved role");
    }

    @Test
    @Order(2)
    void createUserTest() {

        User newUser = new User();
        newUser.setRole(roleService.readById(1));
        newUser.setFirstName("Mykola");
        newUser.setLastName("Bielousov");
        newUser.setEmail("mykola@gmail.com");
        newUser.setPassword("H1b3rn14n!");
        userService.create(newUser);
        User retrievedUser = userService.readByEmail("mykola@gmail.com");
        assertEquals(newUser, retrievedUser, "Created user should match retrieved user");
    }

    @Test
    @Order(3)
    void getAllUsersTest() {
        List<User> users = userService.getAll();
        assertEquals(users.size(), 1);
    }

    @Test
    @Order(4)
    void updateUserTest() {
        User userToUpdate = userService.readByEmail("mykola@gmail.com");
        long id = userToUpdate.getId();
        userToUpdate.setFirstName("Vasyl");
        userService.update(userToUpdate);
        User updatedUser = userService.readById(id);
        assertEquals("Vasyl", updatedUser.getFirstName(), "User's first name should be updated");
    }

    @Test
    @Order(5)
    void deleteUserTest() {
        User userToDelete = userService.readByEmail("mykola@gmail.com");
        if (userToDelete != null) {
            long id = userToDelete.getId();
            userService.delete(id);
        }
        Assertions.assertNull(userService.readByEmail("mykola@gmail.com"), "Deleted user should not exist");
    }
}

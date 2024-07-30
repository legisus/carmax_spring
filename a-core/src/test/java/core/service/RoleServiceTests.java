package core.service;

import core.model.Role;
import core.repository.RoleRepository;
import core.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleServiceTests {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void testCreateRole() {
        Role role = new Role();
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.empty());
        when(roleRepository.save(role)).thenReturn(role);
        Role result = roleService.create(role);
        Assertions.assertEquals(role, result);
    }

    @Test
    void testReadRoleById() {
        Long id = 1L;
        Role role = new Role();
        when(roleRepository.findById(id)).thenReturn(Optional.of(role));
        Role result = roleService.readById(id);
        Assertions.assertEquals(role, result);
    }

    @Test
    void testUpdateRole() {
        Role role = new Role();
        role.setName("Role Name");
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.empty());
        when(roleRepository.save(role)).thenReturn(role);
        Role result = roleService.update(role);
        Assertions.assertEquals(role, result);
    }

    @Test
    void testDeleteRole() {
        Long id = 1L;
        doNothing().when(roleRepository).deleteById(id);
        roleService.delete(id);
        verify(roleRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetAllRoles() {
        Role role1 = new Role();
        Role role2 = new Role();
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));
        List<Role> result = roleService.getAll();
        Assertions.assertEquals(2, result.size());
    }
}

package core.service.impl;


import core.model.Role;
import core.service.RoleService;
import core.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role role) {
        String name = role.getName();
        Optional<Role> findByNameOpt = roleRepository.findByName(name);
        if (findByNameOpt.isEmpty()) {
            roleRepository.save(role);
        } else {
            return role;
        }
        return role;
    }

    @Override
    public Role readById(long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role update(Role role) {
        Optional<Role> existRoleOpt = roleRepository.findById(role.getId());
        if (existRoleOpt.isPresent()) {
            Role existingRole = existRoleOpt.get();
            if (existingRole.getName() != null && !existingRole.getName().equals(role.getName())) {
                Optional<Role> roleWithSameName = roleRepository.findByName(role.getName());
                if (!roleWithSameName.isPresent()) {
                    return roleRepository.save(role);
                }
            }
        }
        return existRoleOpt.orElseGet(() -> create(role));
    }

    @Override
    public void delete(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}

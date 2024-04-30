package carmax.version001.service.impl;


import carmax.version001.model.Role;
import carmax.version001.repository.RoleRepository;
import carmax.version001.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    @Override
    public Role create(Role role) {
        String name = role.getName();
        Optional<Role> findByNameOpt = roleRepository.findByName(name);
        if (!findByNameOpt.isPresent()) {
            roleRepository.save(role);
        } else {
            return role;
        }
        return role;
    }

    @Transactional
    @Override
    public Role readById(long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Role update(Role role) {
        Optional<Role> existRoleOpt = roleRepository.findById(role.getId());
        if (existRoleOpt.isPresent() && !existRoleOpt.get().getName().equals(role.getName())) {
            Optional<Role> roleWithSameName = roleRepository.findByName(role.getName());
            if (!roleWithSameName.isPresent()) {
                return roleRepository.save(role);
            }
        }
        return existRoleOpt.orElseGet(() -> create(role));
    }

    @Transactional
    @Override
    public void delete(long id) {
        roleRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}

package com.laurarojas.tablerointeractivoapi.config;

import com.laurarojas.tablerointeractivoapi.entity.RoleEntity;
import com.laurarojas.tablerointeractivoapi.enums.Status;
import com.laurarojas.tablerointeractivoapi.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> defaultRoles = List.of("admin", "member");

        for (String roleName : defaultRoles) {
            roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        RoleEntity role = new RoleEntity();
                        role.setName(roleName);
                        role.setStatus(Status.ACTIVE);
                        return roleRepository.save(role);
                    });
        }
    }
}

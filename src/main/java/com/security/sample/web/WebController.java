package com.security.sample.web;

import com.security.sample.dto.RolDto;
import com.security.sample.entities.Capability;
import com.security.sample.entities.Role;
import com.security.sample.entities.User;
import com.security.sample.repositories.CapabilityRepository;
import com.security.sample.repositories.RoleRepository;
import com.security.sample.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1")
public class WebController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CapabilityRepository capabilityRepository;

    @Autowired
    public WebController(RoleRepository roleRepository, UserRepository userRepository, CapabilityRepository capabilityRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.capabilityRepository = capabilityRepository;
    }


    @GetMapping(value = "/roles")
    public ResponseEntity<List<RolDto>> getUserRoles(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
        List<Role> roleList = roleRepository.findAllByUsersIn(Collections.singletonList(user));

        List<RolDto> rolDto = roleList
                .stream()
                .map(Role::getName)
                .map(RolDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(rolDto);
    }

    @GetMapping(value = "/capabilities")
    public ResponseEntity<List<RolDto>> getUserCapabilities(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
        List<Role> roleList = roleRepository.findAllByUsersIn(Collections.singletonList(user));

        Set<Capability> capabilities = new HashSet<>();
        capabilities.addAll(capabilityRepository.findAllByRolesIn(roleList));
        capabilities.addAll(capabilityRepository.findAllByUsersIn(Collections.singletonList(user)));

        List<RolDto> rolDto = capabilities
                .stream()
                .map(Capability::getName)
                .map(RolDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(rolDto);
    }
}

package com.security.sample.security;

import com.security.sample.entities.Capability;
import com.security.sample.entities.Role;
import com.security.sample.entities.User;
import com.security.sample.repositories.CapabilityRepository;
import com.security.sample.repositories.RoleRepository;
import com.security.sample.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CapabilityRepository capabilityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository,
                                    RoleRepository roleRepository,
                                    CapabilityRepository capabilityRepository,
                                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.capabilityRepository = capabilityRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));

        List<Role> userRoles = roleRepository.findAllByUsersIn(Collections.singletonList(user));
        List<Capability> capabilitiesOfRoles = capabilityRepository.findAllByRolesIn(userRoles);
        List<Capability> userCapabilities = capabilityRepository.findAllByUsersIn(Collections.singletonList(user));

        Set<GrantedAuthority> authorities = new HashSet<>();

        userRoles
                .stream()
                .map(Role::getName)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .forEach(authorities::add);

        capabilitiesOfRoles
                .stream()
                .map(Capability::getName)
                .map(SimpleGrantedAuthority::new)
                .forEach(authorities::add);

        userCapabilities
                .stream()
                .map(Capability::getName)
                .map(SimpleGrantedAuthority::new)
                .forEach(authorities::add);

        return new CustomUserDetails(
                passwordEncoder.encode(user.getPassword()),
                user.getUsername(),
                authorities,
                true,
                true,
                true,
                true
        );
    }
}

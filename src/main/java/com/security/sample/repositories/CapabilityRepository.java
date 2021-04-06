package com.security.sample.repositories;

import com.security.sample.entities.Capability;
import com.security.sample.entities.Role;
import com.security.sample.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CapabilityRepository extends JpaRepository<Capability, Long> {

    List<Capability> findAllByRolesIn(Collection<Role> roles);

    List<Capability> findAllByUsersIn(Collection<User> user);
}

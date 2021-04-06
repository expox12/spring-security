package com.security.sample.repositories;

import com.security.sample.entities.Role;
import com.security.sample.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByUsersIn(Collection<User> users);
}

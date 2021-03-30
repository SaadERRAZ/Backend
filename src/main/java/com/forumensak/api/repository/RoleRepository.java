package com.forumensak.api.repository;

import com.forumensak.api.model.Role;
import com.forumensak.api.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
    Optional<Role> findById(long id);
}

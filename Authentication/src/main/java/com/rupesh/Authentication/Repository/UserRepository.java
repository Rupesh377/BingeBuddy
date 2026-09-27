package com.rupesh.Authentication.Repository;

import com.rupesh.Authentication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User , UUID> {

    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);

    Boolean ExistsById(UUID id);

}

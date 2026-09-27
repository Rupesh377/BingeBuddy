package com.rupesh.Authentication.Repository;

import com.rupesh.Authentication.Entity.RefreshToken;
import com.rupesh.Authentication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken , UUID> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

    Optional<RefreshToken> findByUser(User user);
}

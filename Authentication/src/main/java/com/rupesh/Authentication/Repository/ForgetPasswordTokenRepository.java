package com.rupesh.Authentication.Repository;

import com.rupesh.Authentication.Entity.ForgetPasswordToken;
import com.rupesh.Authentication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ForgetPasswordTokenRepository extends JpaRepository<ForgetPasswordToken, UUID> {

    Optional<ForgetPasswordToken> findByToken(String token);

    void deleteByUser(User user);
}

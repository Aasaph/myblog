package com.myblog.repository;

import com.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//import org.springframework.security.core.userdetails.User;//Be careful not to import wrong package

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}

package com.smartcontactmanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcontactmanager.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
// extra methods db related opertions, custom query methods, custom finder methods

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailToken(String emailToken);
}

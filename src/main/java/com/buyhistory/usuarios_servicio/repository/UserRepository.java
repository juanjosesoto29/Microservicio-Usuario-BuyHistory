package com.buyhistory.usuarios_servicio.repository;

import com.buyhistory.usuarios_servicio.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailIgnoreCase(String email);
}

package com.buyhistory.usuarios_servicio.service;

import com.buyhistory.usuarios_servicio.model.User;

import java.util.List;

public interface UserService {

    User login(String email, String rawPassword);

    User register(String name, String email, String rawPassword);

    List<User> findAll();

    void deleteById(String id);

    User updateRole(String id, String role);
}

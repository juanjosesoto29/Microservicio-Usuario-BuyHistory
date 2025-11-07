package com.buyhistory.usuarios_servicio.service;

import com.buyhistory.usuarios_servicio.dto.LoginRequest;
import com.buyhistory.usuarios_servicio.dto.RegisterRequest;
import com.buyhistory.usuarios_servicio.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto register(RegisterRequest request);

    UserDto login(LoginRequest request);
    
    UserDto updateRole(Long id, String role);

    void delete(Long id);
}

package com.buyhistory.usuarios_servicio.service;

import com.buyhistory.usuarios_servicio.dto.*;
import java.util.List;

public interface UserService {

    UserDto login(LoginRequest request);

    ApiResponse register(RegisterRequest request);

    List<UserDto> findAll();

    UserDto updateRole(String id, String role);
}

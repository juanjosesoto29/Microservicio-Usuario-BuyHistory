package com.buyhistory.usuarios_servicio.dto;

import lombok.Data;

@Data
public class UpdateRoleRequest {
    private String role; // "ADMIN" o "CLIENTE"
}

package com.buyhistory.usuarios_servicio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean ok;
    private String message;
    private T data;
}

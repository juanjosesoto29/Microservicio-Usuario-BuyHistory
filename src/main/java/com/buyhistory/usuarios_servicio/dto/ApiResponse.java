package com.buyhistory.usuarios_servicio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private String message;
    private int status;       // 200, 400, 404, etc.
    private boolean success;  // true / false
}

package com.puenteblanco.pb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VeterinarianResponseDto {
    private Long id;
    private String nombreCompleto;
    private String especialidad;
}

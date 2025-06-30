package com.puenteblanco.pb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReportHistorialResponseDto {

    private String tipoReporte;
    private String parametros;
    private LocalDateTime fechaGeneracion;
    private String urlDescarga; // Ejemplo: /storage/reportes/reporte-12345.pdf
}
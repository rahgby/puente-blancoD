package com.puenteblanco.pb.dto.request;

import lombok.Data;

@Data
public class AttendAppointmentRequestDto {

    private String observacionesClinicas;

    private String diagnostico;

    private String tratamiento;

    private String prescripcion;
}

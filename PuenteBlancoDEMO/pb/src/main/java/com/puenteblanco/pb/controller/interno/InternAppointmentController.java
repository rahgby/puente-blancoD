package com.puenteblanco.pb.controller.interno;

import com.puenteblanco.pb.dto.response.InternAppointmentResponseDto;
import com.puenteblanco.pb.dto.response.InternCitaValidadaResponseDto;
import com.puenteblanco.pb.entity.User;
import com.puenteblanco.pb.repository.UserRepository;
import com.puenteblanco.pb.services.interfaces.InternAppointmentService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/intern/appointments")
public class InternAppointmentController {

    private final InternAppointmentService internAppointmentService;
    private final UserRepository userRepository;

    public InternAppointmentController(InternAppointmentService internAppointmentService,
                                       UserRepository userRepository) {
        this.internAppointmentService = internAppointmentService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<InternAppointmentResponseDto> getCitasDerivadas(Principal principal) {
        String correo = principal.getName();
        User intern = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return internAppointmentService.getCitasDerivadasDelInterno(intern.getId());
    }

    @GetMapping("/aprobadas")
    public ResponseEntity<List<InternCitaValidadaResponseDto>> obtenerCitasValidadas(Principal principal) {
        String correo = principal.getName();
        User intern = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<InternCitaValidadaResponseDto> citas = internAppointmentService.getCitasValidadasPorIntern(intern.getId());
        return ResponseEntity.ok(citas);
    }
}

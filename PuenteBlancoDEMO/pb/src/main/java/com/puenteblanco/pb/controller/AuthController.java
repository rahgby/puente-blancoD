package com.puenteblanco.pb.controller;

import com.puenteblanco.pb.dto.request.LoginRequestDto;
import com.puenteblanco.pb.dto.request.RegisterInternDto;
import com.puenteblanco.pb.dto.request.RegisterUserDto;
import com.puenteblanco.pb.dto.request.RegisterVeterinarianDto;
import com.puenteblanco.pb.dto.response.LoginResponseDto;
import com.puenteblanco.pb.services.interfaces.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin 
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/client")
    public ResponseEntity<String> registerClient(@RequestBody @Valid RegisterUserDto dto) {
        authService.registerClient(dto);
        return ResponseEntity.ok("Cliente registrado exitosamente.");
    }

    @PostMapping("/register/vet")
    public ResponseEntity<String> registerVeterinarian(@RequestBody @Valid RegisterVeterinarianDto dto) {
        authService.registerVeterinarian(dto);
        return ResponseEntity.ok("Veterinario registrado exitosamente.");
    }

    @PostMapping("/register/intern")
    public ResponseEntity<String> registerIntern(@RequestBody @Valid RegisterInternDto dto) {
        authService.registerIntern(dto);
        return ResponseEntity.ok("Practicante registrado exitosamente.");
    }

    @PostMapping("/login")
public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto,
                                              HttpServletResponse response) {
    LoginResponseDto loginResponse = authService.login(dto); // contiene el token

    // Crea una cookie segura
    ResponseCookie cookie = ResponseCookie.from("jwt", loginResponse.getToken())
            .httpOnly(true)
            .path("/")
            .maxAge(3600) // 1 hora
            .sameSite("Lax")
            .build();

    // Agrega la cookie a la respuesta
    response.addHeader("Set-Cookie", cookie.toString());

    // Retorna la respuesta al cliente (opcional incluir token o usuario)
    return ResponseEntity.ok(loginResponse);
}

@GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expira de inmediato
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true si usas HTTPS
        cookie.setDomain("localhost"); // o tu dominio real si está en producción
        response.addCookie(cookie);

        return "redirect:/"; // Redirige a login o index.html
    }


}

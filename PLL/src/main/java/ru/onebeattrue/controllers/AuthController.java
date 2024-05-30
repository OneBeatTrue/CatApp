package ru.onebeattrue.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.onebeattrue.dto.AuthResponseDTO;
import ru.onebeattrue.dto.SignInDTO;
import ru.onebeattrue.dto.SignUpDTO;
import ru.onebeattrue.services.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@ComponentScan("Services")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public AuthResponseDTO signUp(@RequestBody @Valid SignUpDTO request) {
        return authService.signUp(request);
    }

    @PostMapping("/sign-up-as-admin")
    public AuthResponseDTO signUpAsAdmin(@RequestBody @Valid SignUpDTO request) {
        return authService.signUpAsAdmin(request);
    }

    @PostMapping("/sign-in")
    public AuthResponseDTO signIn(@RequestBody @Valid SignInDTO request) {
        return authService.signIn(request);
    }
}
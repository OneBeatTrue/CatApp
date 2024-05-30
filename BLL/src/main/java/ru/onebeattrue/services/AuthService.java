package ru.onebeattrue.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.onebeattrue.dto.*;
import ru.onebeattrue.exceptions.BusyUsernameException;
import ru.onebeattrue.models.Role;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final MasterService masterService;
    private final JwtService    jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO signUp(SignUpDTO signUpDTO) {
        if (userService.existsByUsername(signUpDTO.username())) {
            throw new BusyUsernameException(signUpDTO.username());
        }
        var master = masterService.create(new MasterDTO(signUpDTO.name(), signUpDTO.birthDate()));
        var user = userService.create(new UserDTO(signUpDTO.username(), passwordEncoder.encode(signUpDTO.password()), master.id(), Role.ROLE_USER));
        var jwt = jwtService.generateToken(userService.getUserEntityByUsername(user.username()));
        return new AuthResponseDTO(jwt);
    }

    public AuthResponseDTO signUpAsAdmin(SignUpDTO signUpDTO) {
        if (userService.existsByUsername(signUpDTO.username())) {
            throw new BusyUsernameException(signUpDTO.username());
        }
        var master = masterService.create(new MasterDTO(signUpDTO.name(), signUpDTO.birthDate()));
        var user = userService.create(new UserDTO(signUpDTO.username(), passwordEncoder.encode(signUpDTO.password()), master.id(), Role.ROLE_ADMIN));
        var jwt = jwtService.generateToken(userService.getUserEntityByUsername(user.username()));
        return new AuthResponseDTO(jwt);
    }

    public AuthResponseDTO signIn(SignInDTO signInDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDTO.username(),
                signInDTO.password()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(signInDTO.username());

        var jwt = jwtService.generateToken(user);

        return new AuthResponseDTO(jwt);
    }
}
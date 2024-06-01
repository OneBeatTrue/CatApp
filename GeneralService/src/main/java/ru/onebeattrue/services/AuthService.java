package ru.onebeattrue.services;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.onebeattrue.dto.*;
import ru.onebeattrue.enums.Role;
import ru.onebeattrue.exceptions.BusyUsernameException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RabbitTemplate rabbitTemplate;
    private final UserService userService;
    private final JwtService    jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO signUp(SignUpDTO signUpDTO) {
        if (userService.existsByUsername(signUpDTO.username())) {
            throw new BusyUsernameException(signUpDTO.username());
        }
        var master = (MasterDTO) rabbitTemplate.convertSendAndReceive("createMasterQueue", new MasterDTO(signUpDTO.name(), signUpDTO.birthDate()));
        var user = userService.create(new UserDTO(signUpDTO.username(), passwordEncoder.encode(signUpDTO.password()), master.id(), Role.ROLE_USER));
        var jwt = jwtService.generateToken(userService.getUserEntityByUsername(user.username()));
        return new AuthResponseDTO(jwt);
    }

    public AuthResponseDTO signUpAsAdmin(SignUpDTO signUpDTO) {
        if (userService.existsByUsername(signUpDTO.username())) {
            throw new BusyUsernameException(signUpDTO.username());
        }
        var master = (MasterDTO) rabbitTemplate.convertSendAndReceive("createMasterQueue", new MasterDTO(signUpDTO.name(), signUpDTO.birthDate()));
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
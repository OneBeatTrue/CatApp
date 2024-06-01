package ru.onebeattrue.services;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.onebeattrue.dto.MasterDTO;
import ru.onebeattrue.dto.UserDTO;
import ru.onebeattrue.entities.user.User;
import ru.onebeattrue.exceptions.BusyUsernameException;
import ru.onebeattrue.exceptions.NotFoundException;
import ru.onebeattrue.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;

    public UserDTO create(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.username())) {
            throw new BusyUsernameException(userDTO.username());
        }

        var user = User
                .builder()
                .username(userDTO.username())
                .password(userDTO.password())
                .master(((MasterDTO) rabbitTemplate.convertSendAndReceive("getMasterByIdMasterQueue", userDTO.master())).id())
                .role(userDTO.role())
                .build();
        return new UserDTO(userRepository.save(user));
    }

    public UserDTO getUserById(Long userId) {
        return new UserDTO(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User " + userId + " ")));
    }
    public UserDTO getUserByUsername(String username) {
        return new UserDTO(userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User " + username + " ")));
    }
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    public User getUserEntityByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User " + username + " "));
    }

    public UserDetailsService userDetailsService() {
        return this::getUserEntityByUsername;
    }
}


package ru.onebeattrue.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.onebeattrue.dto.MasterDTO;
import ru.onebeattrue.entities.user.User;
import ru.onebeattrue.enums.Role;
import ru.onebeattrue.services.JwtService;
import ru.onebeattrue.services.UserService;

import java.io.IOException;

@Component
@ComponentScan("Services")
@RequiredArgsConstructor
public class MasterFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String url = request.getRequestURL().toString();
        if (!url.matches(".+/master/retrieve/\\d+") && !url.matches(".+/master/cats/\\d+")) {
            filterChain.doFilter(request, response);
            return;
        }

        var authHeader = request.getHeader(HEADER_NAME);
        if (ObjectUtils.isEmpty(authHeader) || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(BEARER_PREFIX.length());
        String username = jwtService.extractUserName(jwt);
        User user = userService.getUserEntityByUsername(username);

        String[] particles = url.split("/");
        Long masterId = Long.parseLong(particles[particles.length - 1]);
        MasterDTO master = (MasterDTO) rabbitTemplate.convertSendAndReceive("getMasterByIdMasterQueue", masterId);

        if (!master.id().equals(user.getMaster()) && (user.getRole() != Role.ROLE_ADMIN)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        filterChain.doFilter(request, response);
    }
}

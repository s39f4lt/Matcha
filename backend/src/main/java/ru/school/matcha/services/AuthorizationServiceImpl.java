package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.exceptions.JwtAuthenticationException;
import ru.school.matcha.security.jwt.JwtTokenProvider;
import ru.school.matcha.services.interfaces.AuthorizationService;

import static java.util.Objects.isNull;

@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final JwtTokenProvider jwtTokenProvider;

    static {
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Override
    public boolean authorize(String token) throws JwtAuthenticationException {
        log.info("Authorize user with token: {}", token);
        token = jwtTokenProvider.resolveToken(token);
        if (isNull(token)) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
        return jwtTokenProvider.validateToken(token);
    }

}

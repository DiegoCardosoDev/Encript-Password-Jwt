package com.diego.criptopassword.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.diego.criptopassword.data.DetaiilsUserData;
import com.diego.criptopassword.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAutenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRE= 600_000;
    public static final String TOKEN_PASS = "13559409-d463-4a2c-b013-7173f9f49ef7";

    private final AuthenticationManager manager;

    public JWTAutenticationFilter(AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            UserModel userModel = new ObjectMapper()
                    .readValue(request.getInputStream(), UserModel.class);

            return manager.authenticate(new UsernamePasswordAuthenticationToken(
                    userModel.getLogin(),
                    userModel.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException("falha na autenticação", e);

        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        DetaiilsUserData userData = (DetaiilsUserData) authResult.getPrincipal();
        String token = JWT.create().
                withSubject(userData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRE))
                .sign(Algorithm.HMAC512(TOKEN_PASS));
        response.getWriter().write(token);
        response.getWriter().flush();
    }
}

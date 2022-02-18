package com.diego.criptopassword.security;

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

public class JWTAutenticationFilter extends UsernamePasswordAuthenticationFilter {

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
        super.successfulAuthentication(request, response, chain, authResult);
    }
}

package com.emeka.delivery.Security;

import java.util.HashMap;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
import org.springframework.http.MediaType;
import java.util.Map;
@Component
public class JwtAuthEntryPoint  implements AuthenticationEntryPoint {
      @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Credenciales erroneas");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> error = new HashMap<>();
        error.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        error.put("error", "Unauthorized");
        error.put("message", "Acceso no autorizado al recurso: " + authException.getMessage());
        error.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), error);


    }
}

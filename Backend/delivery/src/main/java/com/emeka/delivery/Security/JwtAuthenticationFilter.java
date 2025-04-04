package com.emeka.delivery.Security;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.emeka.delivery.Services.imple.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtGenerator jwtGenerator;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtGenerator jwtGenerator, UserDetailsServiceImpl userDetailsService) {
        this.jwtGenerator = jwtGenerator;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request,
                                    @SuppressWarnings("null") HttpServletResponse response,
                                    @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJwtFromRequest(request);

            if (StringUtils.hasText(token) && jwtGenerator.validateToken(token)) {
                String username = jwtGenerator.getUsernameFromJWT(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception ex) {
            // Si el token es inválido o hay un problema, podemos registrar el error y continuar sin interrumpir la ejecución
            System.err.println("Error en la autenticación JWT: " + ex.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

package com.eazytest.eazytest.config;

import com.eazytest.eazytest.repository.security.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//@AllArgsConstructor
@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * the request, the response, consider filter chain like a pointer to the next filter in the chain
     * extract the authorization details from the request.
     * check whether the details is null or does not start with bearer.
     * if either of the above conditions, go to the next filter in the chain.
     * if both are fine,
     * get the token from the authorization header.
     * from the token, get the username. if username is not null && the user has not already been authenticated,
     * perform the following steps:
     * obtain the user details from the custom userServiceDetails class.
     * check if the token is valid (remember token expiration and that the user matches it)
     * if valid.
     * create an authentication object.
     * reinforce the details of the authentication object
     * set the security context holder with this authentication object
     * pass it to the next step in the filter chain.
     */

    private JWTService jwtService;
    private CustomUserDetailsService userDetailsService;
    private TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String username = jwtService.getUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            Boolean isValid = tokenRepository.findByToken(jwt).map(t -> !t.getRevoked() && !t.getExpired()).orElse(false);


            if (jwtService.isTokenValid(jwt, userDetails) && isValid){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}

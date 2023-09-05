package procentaurus.projects.ReservationSystem.Configuration.Auth.Jwt;

import java.io.IOException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    private void handleAuthenticationFailure(HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try {
            response.getWriter().write("An error occurred. Contact admin.");
        } catch (IOException e) {
            return;
        }
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain){

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String userEmail = null;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            try {
                filterChain.doFilter(request, response);
            }catch(IOException | ServletException e){
                handleAuthenticationFailure(response);
            }
            return;
        }

        jwt = authHeader.substring(7);

        try {
            userEmail = jwtService.extractUsername(jwt);
        }catch(ExpiredJwtException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }catch (MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        }

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }else response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }else response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        try {
            filterChain.doFilter(request, response);
        }catch(IOException | ServletException e){
            handleAuthenticationFailure(response);
        }
    }
}
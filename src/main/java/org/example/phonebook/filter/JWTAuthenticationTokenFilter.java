package org.example.phonebook.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.phonebook.util.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Security filter that intercepts incoming HTTP requests to validate JWT authentication tokens.
 *
 * <p>Skips filtering for public endpoints such as registration, login, and API documentation.
 * For other requests, extracts the JWT token from the Authorization header, validates it,
 * and sets the authentication in the security context if valid.
 */
@Component
@RequiredArgsConstructor
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {


    private final JwtTokenUtil tokenUtil;

    private final UserDetailsService userDetailsService;

    /**
     * Filters each HTTP request to process and validate the JWT token.
     *
     * @param httpServletRequest  the HTTP request
     * @param httpServletResponse the HTTP response
     * @param filterChain         the filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {


        String path = httpServletRequest.getRequestURI();
        if (path.startsWith("/user/register") ||
                path.startsWith("/user/login") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui")


        ) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String requestHeader = httpServletRequest.getHeader("Authorization");

        String username = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            try {
                username = tokenUtil.getUsernameFromToken(authToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (tokenUtil.validateToken(authToken, userDetails.getUsername())) {

                List<String> roles = tokenUtil.getRolesFromToken(authToken);
                List<GrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());


                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);


            }
        }


        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}

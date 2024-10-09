package com.e5.employeemanagement.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.e5.employeemanagement.helper.AuthenticateException;
import com.e5.employeemanagement.service.AuthenticationDetailsService;
import com.e5.employeemanagement.util.JwtUtil;

/**
 * <p>
 * A JWT filter that authenticates and authorizes requests.
 * </p>
 */
public class JwtFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver exceptionResolver;
    @Autowired
    AuthenticationDetailsService userDetailsService;

    public JwtFilter(HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    /**
     * <p>
     * Performs the filtering operation.
     * This method is called for each incoming request and is responsible for authenticating and
     * authorizing the request.
     * </p>
     *
     * @param request the HTTP request for validate the token, and it contains details of the token.
     * @param response the HTTP response to do the other filters.
     * @param filterChain the filter chain to do the other filters.
     * @throws ServletException if a servlet exception occurs.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().contains("/v1/users")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String jwtToken;
            String username;
            jwtToken = validateHeader(authorizationHeader);
            username = JwtUtil.extractUserName(jwtToken);
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            validateToken(jwtToken, userDetails);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(token);
            filterChain.doFilter(request, response);
        } catch (AuthenticateException e) {
            exceptionResolver.resolveException(request, response, null, e);
        }
    }

    /**
     * <p>
     * Validates the authorization header.
     * This method checks if the Authorization header is present and if it starts with "Bearer ".
     * </p>
     *
     * @param authorizationHeader it contains token details, to get the JWT Token.
     * @return {@link String} JWT token if the authorizationHeader is starts with Bearer.
     * @throws AuthenticateException if the authorizationHeader is null or did not start with Bearer.
     */
    private String validateHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new AuthenticateException("Header is invalid");
        }
        return authorizationHeader.substring(7);
    }

    /**
     * <p>
     * Validates the JWT token.
     * This method checks if the JWT token is valid by calling the JwtUtil.validateToken method.
     * </p>
     *
     * @param jwtToken to validate the JWT Token.
     * @param userDetails to verify the token username.
     * @throws AuthenticateException if the token is invalid.
     */
    private void validateToken(String jwtToken, UserDetails userDetails) {
        if (!JwtUtil.validateToken(jwtToken, userDetails)) {
            throw new AuthenticateException("Invalid token");
        }
    }
}

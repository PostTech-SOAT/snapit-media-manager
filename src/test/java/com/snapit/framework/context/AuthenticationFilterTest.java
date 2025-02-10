package com.snapit.framework.context;

import com.snapit.application.util.exception.BadCredentialsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthenticationFilterTest {

    private AuthenticationFilter authenticationFilter;
    private HttpServletRequest request;
    private ServletResponse response;
    private FilterChain chain;

    @BeforeEach
    void setUp() {
        authenticationFilter = new AuthenticationFilter();
        request = mock(HttpServletRequest.class);
        response = mock(ServletResponse.class);
        chain = mock(FilterChain.class);
    }

    @Test
    void testDoFilterWithValidToken() throws IOException, ServletException {
        String email = "test@example.com";
        String token = createToken(email);
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(request.getRequestURI()).thenReturn("/api/v1/process");

        authenticationFilter.doFilter(request, response, chain);

        assertEquals(email, ContextHolder.getEmail());
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void testDestroy() throws IOException, ServletException {
        String email = "test@example.com";
        String token = createToken(email);
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(request.getRequestURI()).thenReturn("/api/v1/process");

        authenticationFilter.doFilter(request, response, chain);

        assertEquals(email, ContextHolder.getEmail());

        authenticationFilter.destroy();

        assertEquals("empty", ContextHolder.getEmail());

        verify(chain, times(1)).doFilter(request, response);
        verify(request, times(1)).getHeader("Authorization");
    }

    @Test
    void testDoFilterWithInvalidToken() {
        when(request.getHeader("Authorization")).thenReturn("invalidToken");
        when(request.getRequestURI()).thenReturn("/api/v1/process");

        assertThrows(BadCredentialsException.class, () -> authenticationFilter.doFilter(request, response, chain));
    }

    @Test
    void testDoFilterWithoutAuthorizationHeader() {
        when(request.getHeader("Authorization")).thenReturn(null);
        when(request.getRequestURI()).thenReturn("/api/v1/process");

        assertThrows(BadCredentialsException.class, () -> authenticationFilter.doFilter(request, response, chain));
    }

    @Test
    void testDoFilterWithoutUsername() {
        String token = createTokenWithoutUsername();

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(request.getRequestURI()).thenReturn("/api/v1/process");

        assertThrows(BadCredentialsException.class, () -> authenticationFilter.doFilter(request, response, chain));
    }

    @Test
    void shouldIgnoreHealthCheckEndpoints() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/actuator/health/readiness");

        authenticationFilter.doFilter(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
    }

    private String createToken(String email) {
        JSONObject payload = new JSONObject();
        payload.put("username", email);
        String encodedPayload = Base64.getUrlEncoder().encodeToString(payload.toString().getBytes());
        return "header." + encodedPayload + ".signature";
    }

    private String createTokenWithoutUsername() {
        JSONObject payload = new JSONObject();
        String encodedPayload = Base64.getUrlEncoder().encodeToString(payload.toString().getBytes());
        return "header." + encodedPayload + ".signature";
    }

}
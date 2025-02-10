package com.snapit.framework.context;

import java.io.IOException;
import java.util.Base64;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.snapit.application.util.exception.BadCredentialsException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthenticationFilter implements Filter {

    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String authorization = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/actuator/health/readiness") || requestURI.equals("/actuator/health/liveness") || requestURI.equals("/actuator/prometheus")) {
            chain.doFilter(request, response);
            return;
        }
        if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
            String token = authorization.substring(BEARER_PREFIX.length());
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String[] chunks = token.split("\\.");
            String payload = new String(decoder.decode(chunks[1]));
            JSONObject jsonObject = new JSONObject(payload);
            if (!jsonObject.isNull("username")) {
                ContextHolder.setEmail(jsonObject.getString("username"));
            }
            else {
                invalidApiKey();
            }
        } else {
            invalidApiKey();
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        ContextHolder.removeEmail();
    }

    private static void invalidApiKey() {
        throw new BadCredentialsException("Invalid Credentials");
    }

}

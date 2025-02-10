package com.snapit.framework.context;

import com.snapit.application.util.exception.BadCredentialsException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

import static java.util.Arrays.stream;

@Component
@AllArgsConstructor
public class AuthenticationFilter implements Filter {

    private static final String BEARER_PREFIX = "Bearer ";

    private static final String[] IGNORED_PATHS = {"/actuator/health/readiness", "/actuator/health/liveness", "/actuator/prometheus"};

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String authorization = request.getHeader("Authorization");

        String requestURI = request.getRequestURI();
        if (stream(IGNORED_PATHS).anyMatch(requestURI::contains)) {
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

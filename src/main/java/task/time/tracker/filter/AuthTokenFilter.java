package task.time.tracker.filter;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import task.time.tracker.service.UserPrincipal;
import task.time.tracker.utils.JwtUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserPrincipal userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		Map<String, Object> responseData = new HashMap<>();
		final ObjectMapper objectMapper = new ObjectMapper();

		responseData.put("timestamp", Instant.now().toEpochMilli());
		responseData.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		responseData.put("path", request.getRequestURI());

		// Check if the Authorization header is present and starts with "Bearer "
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String jwtToken = authHeader.substring(7); // Extract token without "Bearer "

			try {
				String username = jwtUtils.extractUsernameFromToken(jwtToken); // Ensure method name is correct

				// Check if username is extracted and no authentication exists in the context
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);

					// Validate the token
					if (jwtUtils.tokenValid(jwtToken, username)) {
						// Create authentication token
						UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
								null, userDetails.getAuthorities());

						auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

						// Set authentication in context
						SecurityContextHolder.getContext().setAuthentication(auth);
					}
				}
			} catch (SignatureException e) {
				logger.error("Invalid JWT signature: {}", e.getMessage());
				writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT signature", objectMapper,
						responseData);
				return;
			} catch (MalformedJwtException e) {
				logger.error("Invalid JWT token: {}", e.getMessage());
				writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token", objectMapper,
						responseData);
				return;
			} catch (ExpiredJwtException e) {
				logger.error("JWT token is expired: {}", e.getMessage());
				writeErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "JWT token is expired", objectMapper,
						responseData);
				return;
			} catch (UnsupportedJwtException e) {
				logger.error("JWT token is unsupported: {}", e.getMessage());
				writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "JWT token is unsupported",
						objectMapper, responseData);
				return;
			} catch (IllegalArgumentException e) {
				logger.error("JWT claims string is empty: {}", e.getMessage());
				writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "JWT claims string is empty",
						objectMapper, responseData);
				return;
			} catch (Exception e) {
				logger.error("An error occurred: {}", e.getMessage());
				writeErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred",
						objectMapper, responseData);
				return;
			}
		}

		// Proceed with the filter chain
		filterChain.doFilter(request, response);
	}

	private void writeErrorResponse(HttpServletResponse response, int status, String message, ObjectMapper objectMapper,
			Map<String, Object> responseData) throws IOException {
		responseData.put("message", message);
		response.setStatus(status);
		response.setContentType("application/json");
		response.getWriter().write(objectMapper.writeValueAsString(responseData));
		response.getWriter().flush();
	}

}

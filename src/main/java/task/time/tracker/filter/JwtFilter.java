package task.time.tracker.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import task.time.tracker.utils.JwtUtils;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsService userService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		String jwtToken;
		if (authHeader == null || authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		jwtToken = authHeader.substring(7);
		String username=jwtUtils.extractUsernameFromToken(jwtToken);
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			 UserDetails userDetails = userService.loadUserByUsername(username);
			if(jwtUtils.tokenValid(jwtToken, username))
			{
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		filterChain.doFilter(request, response);
	}

}

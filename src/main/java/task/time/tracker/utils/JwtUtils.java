package task.time.tracker.utils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	public static final String SECRET_KEY = "yv+/52FoFMRh2w1zTV/22HmiNmLt6FddAMLKdqwTq3VDthEtxcr541OdjPu47dAz\r\n"
			+ "";

	public String extractUsernameFromToken(String jwtToken) {

		return extractClam(jwtToken, Claims::getSubject);

	}

	public String generateJwtToken(String email) {
		return Jwts.builder().setIssuer("Deepak").setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis())).claim(email, email)
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();
	}

	public <T> T extractClam(String token, Function<Claims, T> claimResover) {
		Claims claims = extractAllClaims(token);
		return claimResover.apply(claims);

	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
	}

	public Key getKey() {
		byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(bytes);
	}

	public Boolean tokenValid(String token, String username) {
		String email = extractUsernameFromToken(token);
		return ((username.equals(email)) && !TokenExpiration(token));
	}

	public Boolean TokenExpiration(String token) {
		return exractTokenExpiration(token).before(new Date(System.currentTimeMillis()));
	}

	public Date exractTokenExpiration(String token) {
		Date date = extractClam(token, Claims::getExpiration);
		return date;
	}

}

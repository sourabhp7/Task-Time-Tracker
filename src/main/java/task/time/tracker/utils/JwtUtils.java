package task.time.tracker.utils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import task.time.tracker.model.UserEntity;
import task.time.tracker.repository.UserEntityRepository;

@Component
public class JwtUtils {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(JwtUtils.class);

    // Ensure the SECRET_KEY is valid Base64 and at least 32 bytes when decoded
    public static final String SECRET_KEY = "yv52FoFMRh2w1zTV22HmiNmLt6FddAMLKdqwTq3VDthEtxcr541OdjPu47dAz22"; // Example 32-byte key

    @Autowired
    private UserEntityRepository userEntityRepository;

    private static final long JWT_EXPIRATION_MS = 3600000; // 1 hour

    public String extractUsernameFromToken(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public String generateJwtToken(String userName) {
        Optional<UserEntity> user = userEntityRepository.findByUserName(userName);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        return Jwts.builder()
                .setIssuer("elearning-service")
                .setSubject(user.get().getUserName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS)) // Set expiration to future date
                .claim("username", user.get().getUserName())
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean tokenValid(String token, String username) {
        String extractedUsername = extractUsernameFromToken(token);
        return (username.equals(extractedUsername) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token) {
        return extractTokenExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public Date extractTokenExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
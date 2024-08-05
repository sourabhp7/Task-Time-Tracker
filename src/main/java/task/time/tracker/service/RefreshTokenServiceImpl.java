package task.time.tracker.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import task.time.tracker.exception.TokenRefreshException;
import task.time.tracker.model.RefreshToken;
import task.time.tracker.model.UserEntity;
import task.time.tracker.repository.RefreshTokenRepository;
import task.time.tracker.repository.UserEntityRepository;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Value("${app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	@Override
	public RefreshToken createRefreshToken(String username) {
		RefreshToken refreshToken = new RefreshToken();
		Optional<UserEntity> user = userEntityRepository.findByUserName(username);
		if (user.isPresent()) {
			final UserEntity userEntity = user.get();
			refreshToken.setUserEntity(userEntity);
			refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
			refreshToken.setToken(UUID.randomUUID().toString());
			refreshToken.setCreatedBy(userEntity.getUserName());
			refreshToken.setCreatedBy(userEntity.getUserName());
			refreshToken = refreshTokenRepository.save(refreshToken);
		}
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}

		return token;
	}

	@Transactional
	public int deleteByUserId(Long userId) {
		return refreshTokenRepository.deleteByUserEntity(userEntityRepository.findById(userId).get());
	}
}

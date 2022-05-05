package javaGuides.duc.Service;

import java.time.Duration;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javaGuides.duc.Entity.User;
import javaGuides.duc.Repository.UserRepository;

@Service
public class AccountService {
	private static final int EXPIRE_TOKEN = 10;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private EmailService emailService;
	public AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder,EmailService emailService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailService=emailService;
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Boolean existByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public Boolean existByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public String forgotPassword(String email) throws Exception {
		User user = userRepository.findByEmail(email);
		if (user == null)
			return "not found in system";
		String token = generateToken();
		user.setTokenForgotPassword(token);
		user.setTimeCreation(Instant.now());
		userRepository.save(user);
		try {
			String message=emailService.sendEmail(token, email);
			return message;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public String resetPassword(String token, String password) {
		if (token == null)
			return "token is null";
		if (password == null)
			return "new password is null";
		User user = userRepository.findByTokenForgotPassword(token);
		if (user == null)
			return "not found in system";
		if (isexpire(user.getTimeCreation()))
			return "expire token";
		String newpasswordString = passwordEncoder.encode(password);
		user.setPassword(newpasswordString);
		userRepository.save(user);
		return "reset password successfully";

	}

	private Boolean isexpire(Instant timeCreation) {
		Instant now = Instant.now();
		Duration diff = Duration.between(timeCreation, now);

		return diff.toMinutes() >= EXPIRE_TOKEN;

	}

	private String generateToken() {
		StringBuilder token = new StringBuilder();
		token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString());
		return token.toString();
	}

}

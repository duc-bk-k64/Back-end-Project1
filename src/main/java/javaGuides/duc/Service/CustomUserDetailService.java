package javaGuides.duc.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javaGuides.duc.Config.JWT.CustomUserDetail;
import javaGuides.duc.Entity.User;
import javaGuides.duc.Repository.UserRepository;

@Service

public class CustomUserDetailService implements UserDetailsService {
	private UserRepository userRepository;

	public CustomUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		return  CustomUserDetail.createCustomUserDetails(user);
	}

	
}

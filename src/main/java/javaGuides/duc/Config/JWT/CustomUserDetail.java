package javaGuides.duc.Config.JWT;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javaGuides.duc.Entity.User;


public class CustomUserDetail implements UserDetails{

	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> roles;
	

	public static CustomUserDetail createCustomUserDetails(User account) {
		if (account != null) {
			List<GrantedAuthority> roles = account.getRoles().stream()//.filter(Objects::nonNull)
					.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
			

			CustomUserDetail customUserDetails = new CustomUserDetail();
			//System.out.println("jwt*************");
			customUserDetails.setUsername(account.getUsername());
			customUserDetails.setPassword(account.getPassword());
			customUserDetails.setRoles(roles);
		
			return customUserDetails;

		}
		return null;

	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return username;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }

   

}

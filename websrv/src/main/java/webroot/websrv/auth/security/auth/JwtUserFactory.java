package webroot.websrv.auth.security.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import webroot.websrv.auth.entity.Role;
import webroot.websrv.auth.entity.User;

public class JwtUserFactory {
	 private JwtUserFactory() {}

	    public static JwtUser create(User user) {
	        return new JwtUser(
	                user.getId(),
	                user.getFirstname(),
	                user.getLastname(),
	                user.getUsername(),
	                user.getEmail(),
	                user.getPassword(),
	                mapToGrantedAuthorities(user.getRole())
	        );
	    }

	    private static List<GrantedAuthority> mapToGrantedAuthorities(Role role) {
	        List<GrantedAuthority> authorities = new ArrayList<>();
	        authorities.add(new SimpleGrantedAuthority(role.getLabel()));
	        return authorities;
	    }
}

package webroot.websrv.auth.controller;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webroot.websrv.auth.entity.Role;
import webroot.websrv.auth.entity.User;
import webroot.websrv.auth.exception.InvalidPasswordException;
import webroot.websrv.auth.exception.UserNotFoundException;
import webroot.websrv.auth.security.auth.JwtAuthenticationRequest;
import webroot.websrv.auth.security.auth.JwtAuthenticationResponse;
import webroot.websrv.auth.security.auth.JwtUser;
import webroot.websrv.auth.security.auth.JwtUtil;
import webroot.websrv.auth.service.UserService;


@RestController
public class AuthController extends BaseController {

	@Value("${auth.header}")
	private String tokenHeader;

	public final static String SIGNUP_URL = "/api/auth/signup";
	public final static String LOGIN_URL = "/api/auth/login";
	public final static String REFRESH_TOKEN_URL = "/api/auth/token/refresh";

	private AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;
	private UserDetailsService userDetailsService;
	private UserService userService;

	/**
	 * Injects AuthenticationManager instance
	 * 
	 * @param authenticationManager
	 *            to inject
	 */
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * Injects JwtUtil instance
	 * 
	 * @param jwtUtil
	 *            to inject
	 */
	@Autowired
	public void setJwtTokenUtil(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	/**
	 * Injects UserDetailsService instance
	 * 
	 * @param userDetailsService
	 *            to inject
	 */
	@Autowired
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Injects UserService instance
	 * 
	 * @param userService
	 *            to inject
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Bean
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Adds new user and returns authentication token
	 * 
	 * @param authenticationRequest
	 *            request with username, email and password fields
	 * @return generated JWT
	 * @throws AuthenticationException
	 */
	@PostMapping(SIGNUP_URL)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
			throws AuthenticationException {	

		String lastName = authenticationRequest.getLastname();
		String firstName = authenticationRequest.getFirstname();
		String username = authenticationRequest.getUsername();
		String email = authenticationRequest.getEmail();
		String password = authenticationRequest.getPassword();
		LOG.info("[POST] CREATING TOKEN FOR User " + username);
		Role role = new Role(1L, "USER");
		userService.save(new User(0L, firstName,lastName,username, email, password, role));
		JwtUser userDetails;

		try {
			userDetails = (JwtUser) userDetailsService.loadUserByUsername(username);
		} catch (UsernameNotFoundException ex) {
			LOG.error(ex.getMessage());
			throw new UserNotFoundException();
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}

	/**
	 * Returns authentication token for given user
	 * 
	 * @param authenticationRequest
	 *            with username and password
	 * @return generated JWT
	 * @throws AuthenticationException
	 */
	@CrossOrigin
	@PostMapping(LOGIN_URL)
	public ResponseEntity getAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
			throws AuthenticationException {
		System.out.println("on login server side");
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();
		LOG.info("[POST] GETTING TOKEN FOR User " + username);
		JwtUser userDetails;

		try {
			userDetails = (JwtUser) userDetailsService.loadUserByUsername(username);
		} catch (UsernameNotFoundException | NoResultException ex) {
			LOG.error(ex.getMessage());
			throw new UserNotFoundException();
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
		}

		if (!passwordEncoder().matches(password, userDetails.getPassword())) {
			throw new InvalidPasswordException();
		} 

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		final String token = jwtUtil.generateToken(userDetails);
		
		//update token on the user
		
		userService.updateToken(username,token);
		
		LOG.info("[POST] USER TOKEN saved for user " + username);
		
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}

	/**
	 * Refreshes token
	 * 
	 * @param request
	 *            with old JWT
	 * @return Refreshed JWT
	 */
	@PostMapping(REFRESH_TOKEN_URL)
	public ResponseEntity refreshAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
		LOG.info("[POST] REFRESHING TOKEN");
		String refreshedToken = jwtUtil.refreshToken(token);
		return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
	}

}

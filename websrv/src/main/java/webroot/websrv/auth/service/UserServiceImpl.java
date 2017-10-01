package webroot.websrv.auth.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import webroot.websrv.auth.entity.User;
import webroot.websrv.auth.exception.UserNotFoundException;
import webroot.websrv.auth.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	@PersistenceContext
	public EntityManager entityManager;
	
	
	private UserRepository userRepository;

	/**
	 * Injects UserRepository instance
	 * @param userRepository to inject
	 */
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Bean
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Returns all users
	 * @return List of users
	 */
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * Returns user with given id
	 * @param id to look for
	 * @return user with given id
	 * @throws UserNotFoundException if user with given id does not exists
	 */
	@Override
	public User findById(Long id) {
//		if(userRepository.existsById(id)) {
			return userRepository.getOne(id);
//		} else {
//			throw new UserNotFoundException();
//		}
	}

	/**
	 * Returns user with given email
	 * @param email to look for
	 * @return user with given email
	 * @throws UserNotFoundException if user with given email does not exists
	 */
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * Returns user with given username
	 * @param username to look for
	 * @return username with given email
	 * @throws UserNotFoundException if user with given username does not exists
	 */
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public List<User> findByAnyName(String name) {
		return userRepository.findByAnyname(name);
	}

	/**
	 * Adds or updates user.
	 * If user with following id already exists it will be updated elsewhere added as the new one.
	 * @param user to add or update
	 * @return Added or updated user
	 */
	@Override
	public User save(User user) {
		System.out.println(user.toString());
		if(!userRepository.existsById(user.getId())) {
			user.setPassword(passwordEncoder().encode(user.getPassword()));
		}
		return userRepository.save(user);
	}

	/**
	 * Deletes user by given id
	 * @param id to look for
	 * @throws UserNotFoundException if user with given id does not exists
	 */
	@Override
	public void delete(Long id) {
//		if(userRepository.existsById(id)) {
//			userRepository.deleteById(id);
//		} else {
			throw new UserNotFoundException();
//		}
	}

	@Override
	public User findUserByToken(String token) {
		return userRepository.getOneByToken(token);
	}
	
	public void updateToken(String username, String token) {
		userRepository.saveToken(username,token);
	}

}


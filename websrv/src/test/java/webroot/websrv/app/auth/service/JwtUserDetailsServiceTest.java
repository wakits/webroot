package webroot.websrv.app.auth.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static webroot.websrv.app.auth.util.DummyDataGenerator.getUsers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import webroot.websrv.BaseTest;
import webroot.websrv.auth.entity.User;
import webroot.websrv.auth.repository.UserRepository;

public class JwtUserDetailsServiceTest extends BaseTest {

    @Mock
    private UserRepository userRepository;
    
	@InjectMocks
	@Autowired
	private UserDetailsService jwtUserDetailsService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(timeout = 3000, expected = UsernameNotFoundException.class)
    public void loadUserWhichNotExistsTest() {
        when(userRepository.findByUsername(anyString())).thenThrow(UsernameNotFoundException.class);
        jwtUserDetailsService.loadUserByUsername("random name");
        verify(userRepository, times(1)).findByUsername(anyString());
        verifyNoMoreInteractions();
    }

	@Test(timeout = 3000)
	public void loadUserByUsernameTest() {
	    User user = getUsers(1).get(0);
	    when(userRepository.findByUsername(anyString())).thenReturn(user);
	    UserDetails fetchedUserDetails = jwtUserDetailsService.loadUserByUsername("random name");
	
	    verify(userRepository, times(1)).findByUsername(anyString());
	    assertNotNull("Fetched user details shouldn't be NULL", fetchedUserDetails);
	    assertEquals("Should return appropriate username",
	            user.getUsername(), fetchedUserDetails.getUsername());
	    assertEquals("Should return appropriate password",
	            user.getPassword(), fetchedUserDetails.getPassword());
	}


}

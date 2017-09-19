package webroot.webcli.app.auth.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import webroot.webcli.app.BaseTest;
import webroot.websrv.auth.entity.User;
import webroot.websrv.auth.repository.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static webroot.webcli.app.auth.util.DummyDataGenerator.*;

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

    @Test(timeout = 3000)
    public void loadUserByUsernameTest() {
        User user = getUsers(1).get(0);
        when(userRepository.findByName(anyString())).thenReturn(user);
        UserDetails fetchedUserDetails = jwtUserDetailsService.loadUserByUsername("random name");

        verify(userRepository, times(1)).findByName(anyString());
        assertNotNull("Fetched user details shouldn't be NULL", fetchedUserDetails);
        assertEquals("Should return appropriate username",
                user.getName(), fetchedUserDetails.getUsername());
        assertEquals("Should return appropriate password",
                user.getPassword(), fetchedUserDetails.getPassword());
    }

    @Test(timeout = 3000, expected = UsernameNotFoundException.class)
    public void loadUserWhichNotExistsTest() {
        when(userRepository.findByName(anyString())).thenThrow(UsernameNotFoundException.class);
        jwtUserDetailsService.loadUserByUsername("random name");
        verify(userRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions();
    }


}

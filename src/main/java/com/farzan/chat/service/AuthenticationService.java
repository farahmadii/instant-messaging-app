package com.farzan.chat.service;


import com.farzan.chat.mapper.UserMapper;
import com.farzan.chat.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {

    private UserMapper userMapper;
    private HashService hashService;

    public AuthenticationService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    // by overriding these methods we provide the hooks for spring to get them on run time
    // getting the authentication object and figure out if the provided credentials are actually correct
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // the credentials provided by user and passed to controller
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userMapper.getUser(username);
        if (user != null){
            String encodedSalt = user.getSalt();
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);
            // compare the hashed version of the provided password with the user's actual hashed password in DB
            if (user.getPassword().equals(hashedPassword)){
                // an empty list for granted authorities along with user pass will be passed to the constructor
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }
        return null;
    }

    /*
     telling the spring that we're going to use (this class handles this authentication scheme)
     username password authentication scheme, from available alternatives.
     */
    @Override
    public boolean supports(Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

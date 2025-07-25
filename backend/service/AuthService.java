package com.shouldibunk.backend.service;

import com.shouldibunk.backend.domain.User;
import com.shouldibunk.backend.dto.LoginRequest;
import com.shouldibunk.backend.dto.LoginResponse;
import com.shouldibunk.backend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // Spring automatically injects the beans as we need(DI)
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @Transactional // ensures the method runs within a database transaction
    public User registerUser(User user){
        // 1. Check if user with given email already exists
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalStateException("Email already in use");
        }
        // 2. Encrypt the user's password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        //3. Save the new user to the database
        return userRepository.save(user);
    }
    // Now Create PasswordEncoder using Spring Security

    public LoginResponse login(LoginRequest request) {
        // 1. The authenticate method now returns an Authentication object on success
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // 2. The principal of this Authentication object IS the UserDetails object we need.
        //    Spring Security has already fetched it for us.
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. Now, we can generate the token with the correct type
        var jwtToken = jwtService.generateToken(userDetails);

        // 4. Return the token in the response
        return new LoginResponse(jwtToken);
    }
}

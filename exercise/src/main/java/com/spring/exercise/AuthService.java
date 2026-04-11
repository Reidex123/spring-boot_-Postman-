package com.spring.exercise;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository ur, PasswordEncoder pe, JwtService js, AuthenticationManager am) {
        this.userRepository = ur;
        this.passwordEncoder = pe;
        this.jwtService = js;
        this.authManager = am;
    }

    public void register(RegisterRequest request) throws EmailAlreadyExistsException {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.email()));
        user.setRole(Role.STUDENT);

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        // Spring Security authenticates (throws if wrong credentials)

        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = userRepository.findByEmail(request.email()).orElseThrow();

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
    
}

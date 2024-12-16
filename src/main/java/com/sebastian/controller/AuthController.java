package com.sebastian.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sebastian.config.JwtProvider;
import com.sebastian.model.Cart;
import com.sebastian.model.USER_ROLE;
import com.sebastian.model.User;
import com.sebastian.repository.CartRepository;
import com.sebastian.repository.UserRepository;
import com.sebastian.request.LoginRequest;
import com.sebastian.response.AuthResponse;
import com.sebastian.service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CartRepository cartRepository;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
    
        // Sprawdzenie, czy e-mail już istnieje
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
            throw new Exception("email is already taken");
        }
    
        // Tworzenie użytkownika
        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
    
        User savedUser = userRepository.save(createdUser);
    
        // Tworzenie koszyka
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);
    
        // Logowanie użytkownika
        Authentication authentication = authenticate(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    
        // Generowanie tokena JWT
        String jwt = jwtProvider.generateToken(authentication);
    
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());
    
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
    

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req) {
    
        String email = req.getEmail();
        String password = req.getPassword();
    
        // Uwierzytelnianie użytkownika
        Authentication authentication = authenticate(email, password);
    
        // Debugowanie: wyświetlenie uwierzytelnionego użytkownika
        System.out.println("Authenticated user email: " + authentication.getName());
        System.out.println("Authenticated authorities: " + authentication.getAuthorities());
    
        // Pobranie roli
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
    
        // Generowanie tokena JWT
        String jwt = jwtProvider.generateToken(authentication);
    
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success");
        authResponse.setRole(USER_ROLE.valueOf(role));
    
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    

    private Authentication authenticate(String username, String password) {
        // Pobranie UserDetails na podstawie e-maila
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username...");
        }
        
        // Sprawdzenie poprawności hasła
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password...");
        }
        
        // Zwrócenie poprawnego Authentication z e-mailem jako Principal
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    
}

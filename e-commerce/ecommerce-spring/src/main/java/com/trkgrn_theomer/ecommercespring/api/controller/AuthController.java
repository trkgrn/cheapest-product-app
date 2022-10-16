package com.trkgrn_theomer.ecommercespring.api.controller;


import com.trkgrn_theomer.ecommercespring.api.exception.SQLExc;
import com.trkgrn_theomer.ecommercespring.api.model.concretes.Token;
import com.trkgrn_theomer.ecommercespring.api.model.concretes.User;
import com.trkgrn_theomer.ecommercespring.api.service.TokenService;
import com.trkgrn_theomer.ecommercespring.api.service.UserService;
import com.trkgrn_theomer.ecommercespring.api.service.userdetail.CustomUserDetails;
import com.trkgrn_theomer.ecommercespring.api.service.userdetail.UserDetailService;
import com.trkgrn_theomer.ecommercespring.config.jwt.model.AuthRequest;
import com.trkgrn_theomer.ecommercespring.config.jwt.model.Response;
import com.trkgrn_theomer.ecommercespring.config.jwt.service.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final UserDetailService userDetailsService;

    private final UserService userService;

    private final TokenService tokenService;

    @Value("${jwt.login.expire.hours}")
    Long expireHours;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserDetailService userDetailsService, UserService userService, TokenService tokenService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorret username or password", ex);
        }
        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails,expireHours);

        // rediste 10 dk kaydet
            tokenService.save(new Token(authRequest.getUsername(),jwt),expireHours);
            

        return new ResponseEntity<Response>(new Response(jwt, userDetails.getRole(), userDetails.getId()), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        User addedUser = null;
        try{
            addedUser = this.userService.add(user);
        }
        catch (DataIntegrityViolationException ex){
            throw new SQLExc("Sistemde bu bilgilere ait kayıt bulunmaktadır. Lütfen bilgilerinizi kontrol edin.");
        }

          return new ResponseEntity<>(addedUser,HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserRole(@PathVariable String username){
        return ResponseEntity.ok(this.userService.findByUserName(username).getRole());
    }
}

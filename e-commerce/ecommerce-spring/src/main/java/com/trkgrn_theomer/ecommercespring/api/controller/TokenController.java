package com.trkgrn_theomer.ecommercespring.api.controller;

import com.trkgrn_theomer.ecommercespring.api.exception.ExpiredJwtExc;
import com.trkgrn_theomer.ecommercespring.api.exception.NullPointerExc;
import com.trkgrn_theomer.ecommercespring.api.model.concretes.Token;
import com.trkgrn_theomer.ecommercespring.api.model.concretes.User;
import com.trkgrn_theomer.ecommercespring.api.model.dtos.UserDto;
import com.trkgrn_theomer.ecommercespring.api.service.TokenService;
import com.trkgrn_theomer.ecommercespring.api.service.userdetail.CustomUserDetails;
import com.trkgrn_theomer.ecommercespring.api.service.userdetail.UserDetailService;
import com.trkgrn_theomer.ecommercespring.config.jwt.service.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/token")
public class TokenController {

    private final TokenService tokenService;


    private final ModelMapper modelMapper;

    private final JwtUtil jwtUtil;

    private final UserDetailService userDetailsService;

    public TokenController(TokenService tokenService,  ModelMapper modelMapper, JwtUtil jwtUtil, UserDetailService userDetailsService) {
        this.tokenService = tokenService;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }




    @PostMapping
    public ResponseEntity<?> save(@RequestBody Token token) {
        return new ResponseEntity<>(tokenService.save(token,60L), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findToken(@PathVariable String username) {
        Token token = null;
        try {
            token = tokenService.findTokenByUsername(username);
            //  CustomUserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token.getJwt()));
            //  System.out.println("user: "+userDetails.getUser());
        } catch (NullPointerException e) {
            throw new NullPointerExc("Oturum süresi sona erdi.");
        }
        return ResponseEntity.ok(token);
    }

    @GetMapping("/values/{token}")
    public ResponseEntity<?> findUserByToken(@PathVariable String token) {
        User user = null;
        try {
            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
            user = userDetails.getUser();

        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtExc("Oturum süresi sona erdi.");
        }
        return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> remove(@PathVariable String username) {
        return ResponseEntity.ok(tokenService.delete(username));
    }

    @GetMapping("/isTokenExpired/{token}")
    public ResponseEntity<?> isTokenExpired(@PathVariable String token) {
        String username;
        Token tokenObj;
        try {
            username = this.jwtUtil.extractUsername(token);
            tokenObj = this.tokenService.findTokenByUsername(username);
        } catch (NullPointerException e) {
            throw new NullPointerExc("Oturum süresi sona erdi.");
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtExc("Oturun süresi sona erdi.");
        }
        if(token.equals(tokenObj.getJwt())){
            return ResponseEntity.ok("Oturum devam ediyor.");
        }
        return ResponseEntity.ok("Oturum sona erdi");
    }
}

package com.trkgrn_theomer.cheapspring.api.service;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Token;
import com.trkgrn_theomer.cheapspring.api.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token save(Token token, Long expiredTime) {
        return this.tokenRepository.save(token,expiredTime);
    }

    public Token findTokenByUsername(String username) {
        return this.tokenRepository.findTokenByUsername(username);
    }

    public String delete(String username) {
        return this.tokenRepository.delete(username);
    }
}

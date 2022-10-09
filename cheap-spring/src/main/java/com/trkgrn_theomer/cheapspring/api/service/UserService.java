package com.trkgrn_theomer.cheapspring.api.service;

import com.trkgrn_theomer.cheapspring.api.model.concretes.User;
import com.trkgrn_theomer.cheapspring.api.repository.UserRepository;
import com.trkgrn_theomer.cheapspring.config.jwt.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User findByUserName(String userName) {
        return this.userRepository.findByUsername(userName);
    }

    public User add(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User addedUser =  this.userRepository.save(user);
        return addedUser;
    }

    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    public User findUserByToken(String token){
        return this.findByUserName(jwtUtil.extractUsername(token));
    }

    public List<User> searchCandidateFriendByUserId(Long userId){
        return this.userRepository.searchCandidateFriendByUserId(userId);
    }

    public List<User> searchCandidateFriendByUserIdAndUsername(Long userId,String username,int pageNo,int pageSize){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        return this.userRepository.searchCandidateFriendByUserIdAndUsername(userId,username,pageable);
    }

    public User updateUser( User user){
        User existingUser = this.findByUserName(user.getUsername());
        existingUser.setImage(user.getImage());
        existingUser.setMail(user.getMail());
        existingUser.setTelNumber(user.getTelNumber());
        existingUser.setName(user.getName());
        return userRepository.save(existingUser);
    }

}

package com.trkgrn_theomer.ecommercespring.api.controller;


import com.trkgrn_theomer.ecommercespring.api.model.concretes.User;
import com.trkgrn_theomer.ecommercespring.api.model.dtos.UserDto;
import com.trkgrn_theomer.ecommercespring.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;


    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        System.out.println("içeride");

        return new ResponseEntity<>(new User(), HttpStatus.OK);
    }

    @GetMapping("getUserByUsername")
    public ResponseEntity<UserDto> getByUsername(@RequestParam String username){
        User user = this.userService.findByUserName(username);
        return ResponseEntity.ok(modelMapper.map(user,UserDto.class));
    }

    @GetMapping("/searchCandidateFriends")
    public ResponseEntity<List<UserDto>>  searchCandidateFriendByUserId(@RequestParam Long userId){
        List<User> users = this.userService.searchCandidateFriendByUserId(userId);
        return ResponseEntity.ok(users
                .stream()
                .map(user -> {
                  return modelMapper.map(user, UserDto.class);
                }).collect(Collectors.toList()));
    }

    @GetMapping("/searchCandidateFriendsByUsername")
    public ResponseEntity<List<UserDto>> searchCandidateFriendByUsername(@RequestParam String token,
                                                                         @RequestParam String username,
                                                                         @RequestParam int pageNo,
                                                                         @RequestParam int pageSize){
        User thisUser = this.userService.findUserByToken(token);
        List<User> users = this.userService.searchCandidateFriendByUserIdAndUsername(thisUser.getUserId(),username,pageNo,pageSize);
        return ResponseEntity.ok(users
                .stream()
                .map(user -> {
                   return modelMapper.map(user, UserDto.class);
                })
                .collect(Collectors.toList()));
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody User user){
        User updatedUser = this.userService.updateUser(user);
        return new ResponseEntity<UserDto>
                (modelMapper.map(updatedUser,UserDto.class)
                        , HttpStatus.OK);
    }



}

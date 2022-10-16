package com.trkgrn_theomer.ecommercespring.api.service.userdetail;

import com.trkgrn_theomer.ecommercespring.api.model.concretes.User;
import com.trkgrn_theomer.ecommercespring.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if (user==null)
            throw new UsernameNotFoundException("Kullanıcı bulunamadı!");

        return new CustomUserDetails(user);
    }
}

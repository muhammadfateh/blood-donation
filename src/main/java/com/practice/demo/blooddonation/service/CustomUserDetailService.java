package com.practice.demo.blooddonation.service;

import com.practice.demo.blooddonation.model.User;
import com.practice.demo.blooddonation.oauth.CustomUserDetails;
import com.practice.demo.blooddonation.repo.AuthGroupRepo;
import com.practice.demo.blooddonation.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepo userRepo;
    private final AuthGroupRepo authGroupRepo;

    public CustomUserDetailService(UserRepo userRepo, AuthGroupRepo authGroupRepo) {
        this.userRepo = userRepo;
        this.authGroupRepo = authGroupRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(s);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("cannot find username");
        }

        return new CustomUserDetails(user.get(), authGroupRepo.findByUsername(user.get().getUsername()));
    }
}

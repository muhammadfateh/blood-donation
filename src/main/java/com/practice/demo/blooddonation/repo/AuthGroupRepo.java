package com.practice.demo.blooddonation.repo;

import com.practice.demo.blooddonation.oauth.UserAuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthGroupRepo extends JpaRepository<UserAuthGroup, Long> {
    List<UserAuthGroup> findByUsername(String username);
}

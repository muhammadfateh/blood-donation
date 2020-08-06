package com.practice.demo.blooddonation.controller;

import com.practice.demo.blooddonation.model.User;
import com.practice.demo.blooddonation.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/{username}")
    public User getById(@PathVariable String username) {
        Optional<User> user = userRepo.findByUsername(username);
        return user.get();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<User> user = userRepo.findById(id);
        user.ifPresent(userRepo::delete);
    }
    @PostMapping("/insert")
    public User insertUser(@RequestBody @Validated User user) {
        return userRepo.saveAndFlush(user);
    }

    @GetMapping("login/{username}/{password}")
    public ResponseEntity<?> login(@PathVariable String username, @PathVariable String password){
        Optional<User> user= userRepo.findByUsername(username);
        if(user.isPresent()){
            if( user.get().getPassword().equals(password))
                    return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/principal")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Principal getPrincipal(Principal principal){
        return principal;
    }
}
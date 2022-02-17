package com.diego.criptopassword.controller;

import com.diego.criptopassword.model.User;
import com.diego.criptopassword.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping("list")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("save")
    public ResponseEntity<User> save(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }
}

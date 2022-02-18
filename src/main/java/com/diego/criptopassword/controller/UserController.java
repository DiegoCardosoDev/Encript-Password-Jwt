package com.diego.criptopassword.controller;

import com.diego.criptopassword.model.UserModel;
import com.diego.criptopassword.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<UserModel>> findAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("save")
    public ResponseEntity<UserModel> save(@RequestBody UserModel user){
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/validpassword")
    public ResponseEntity<Boolean> validPassword(@RequestParam String login, @RequestParam String password){

        Optional<UserModel> optionalUser = userRepository.findByLogin(login);

        if (optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        UserModel user = optionalUser.get();
        boolean valid = encoder.matches(password, user.getPassword());

        HttpStatus status = (valid) ?  HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return  ResponseEntity.status(status).body(valid);
    }
}

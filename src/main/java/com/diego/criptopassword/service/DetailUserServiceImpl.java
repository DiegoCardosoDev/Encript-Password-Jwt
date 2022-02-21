package com.diego.criptopassword.service;


import com.diego.criptopassword.data.DetaiilsUserData;
import com.diego.criptopassword.model.UserModel;
import com.diego.criptopassword.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;



@Component
public class DetailUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public DetailUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> userModel = userRepository.findByLogin(username);

        if (userModel.isEmpty()) {
            throw new UsernameNotFoundException("usuario" + "[" + userModel + "]" + " não encontrado");
        }
        return new DetaiilsUserData(userModel);
    }
}

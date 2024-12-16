package com.sebastian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sebastian.config.JwtProvider;
import com.sebastian.model.User;
import com.sebastian.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    public User findUserByEmail(String email) throws Exception {
        
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User not found");
        }

        return user;
    }

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
      String email = jwtProvider.getEmailFromJwtToken(jwt);

      User user = findUserByEmail(email);

      if(user == null){
        throw new Exception("User not found");
    }
        return user;
    }

}

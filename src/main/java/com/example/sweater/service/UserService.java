package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    public boolean activateUser(String code) {
       User user= userRepo.findByActivationCode(code);
       if(user==null){
           return false;
       }
       user.setRoles(null);
       userRepo.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user){
        User userFromDB= userRepo.findByUsername(user.getUsername());
        //если пользователь сущетвует то не добавляем его
        if (userFromDB!=null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActiveCode(UUID.randomUUID().toString());
        userRepo.save(user);

        if(!StringUtils.isEmpty(user.getEmail())){
            String message=String.format("Hello, %s\n" +
                    "Welcome to Sweater! Please visit next link: localhost:8080/activate/%s",
                    user.getUsername(), user.getActiveCode());
                mailSender.send(user.getEmail(), "Activation code", message);
        }
        return true;
    }
}

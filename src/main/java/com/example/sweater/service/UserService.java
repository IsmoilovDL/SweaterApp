package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean activateUser(String code) {
       User user= userRepo.findByActiveCode(code);
       if(user==null){
           return false;
       }
       user.setRoles(null);
       userRepo.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepo.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        if(!StringUtils.isEmpty(user.getEmail())){
            String message=String.format("Hello, %s\n" +
                    "Welcome to Sweater! Please visit next link: localhost:8080/activate/%s",
                    user.getUsername(), user.getActiveCode());
            System.out.println(message);
//                mailSender.send(user.getEmail(), "Activation code", message);
        }
        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void userSave(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles=  Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key:form.keySet()){
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }

    public void updataProfile(User user, String password, String email) {
        String userEmail=user.getEmail();
        boolean emailChange=
        (email!=null && !email.equals(userEmail)) || (userEmail!=null && !userEmail.equals(email));

        if(emailChange){
            user.setEmail(email);
            if(!StringUtils.isEmpty(email)){
                user.setActiveCode(UUID.randomUUID().toString());
            }
        }

        if(!StringUtils.isEmpty(password)){
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepo.save(user);
    }
}

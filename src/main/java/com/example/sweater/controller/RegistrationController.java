package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.CaptchaResponse;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";


    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfim,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model){

        String url=String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponse responseDto=
        restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);

        if(!responseDto.isSuccess()){
            model.addAttribute("captchaError", "Fill captcha");
        }
        boolean isConfirm=StringUtils.isEmpty(passwordConfim);
        if(isConfirm){
            model.addAttribute("password2Error", "Password confirmation empty");
        }
        if(user.getPassword()!=null && !user.getPassword().equals(passwordConfim)){
            model.addAttribute("passwordError", "Passwords are different");
        }

        if(isConfirm || bindingResult.hasErrors() || !responseDto.isSuccess()){
            Map<String, String> errors = ControllerUtil.getErros(bindingResult);
            model.addAttribute(errors);
            return "registration";
        }

        if(!userService.addUser(user)){

            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model){
        boolean isActivate=userService.activateUser(code);
        if(isActivate){
            model.addAttribute("message","User successfully activated");
        }else {
            model.addAttribute("message", "Activation code is not find!");
        }
        return "login";
    }
}




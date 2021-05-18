package com.farzan.chat.controller;


import com.farzan.chat.model.User;
import com.farzan.chat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView() {
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = null;

        if (signupError == null){
            if(!this.userService.isUsernameAvailable(user.getUsername())){
                signupError = "Username is already taken, please try a new one!";
            }
        }

        if (signupError == null){
            int newUserAdded = this.userService.createUser(user);
            if (newUserAdded < 0){
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null){
            model.addAttribute("signupSuccess", true);
        }else{
            model.addAttribute("signupError", signupError);
        }
        return "signup";
    }

}

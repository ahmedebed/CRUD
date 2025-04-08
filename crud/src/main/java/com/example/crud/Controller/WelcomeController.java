package com.example.crud.Controller;


import com.example.crud.Service.WelcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WelcomeController {
    private final WelcomeService welcomeService;
    @GetMapping("/home")
    public String WelcomeMassage(){
        return welcomeService.getWelcomeMessageForUser();
    }
}
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorPage {
    
    @RequestMapping("/acesso_negado")
    public String accessDenied(){
        return "acesso_negado";
    }
}

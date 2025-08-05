package com.example.mytestproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //컨트롤러 선언
public class MyController {

    //메서드 선언
    @GetMapping("/hello")
    public String hellMethod(Model model){
        model.addAttribute("username","조석현"); // 키(String)와 값(object)으로 이루어짐
        return "greeting"; //뷰에게 전달되는 효과
    }
    @GetMapping("/bye")
    public String byeMethod(Model model){
        model.addAttribute("username","조석현"); // 키(String)와 값(object)으로 이루어짐
        return "bye"; //뷰에게 전달되는 효과
    }
}

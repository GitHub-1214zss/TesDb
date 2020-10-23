package com.example.demo.controll;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Test")
public class TestControll {
    @RequestMapping("/Test")
    public String Test(Model model){
        model.addAttribute("mag","123");
        return "Test";

    }
}

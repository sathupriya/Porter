package com.example.Porter.OAuth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oauth")
public class CustomerOAuthController {

	 @GetMapping("/oauth-success")
	    public String showOAuthSuccessPage(@RequestParam String token, Model model) {
	        model.addAttribute("token", token);
	        return "oauth-success"; 
	    }
}

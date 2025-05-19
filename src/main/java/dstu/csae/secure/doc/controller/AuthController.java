package dstu.csae.secure.doc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/signin")
    public String loginPage() {
        return "signin";
    }

    @GetMapping("/signup")
    public String registerPage() {
        return "signup";
    }
}

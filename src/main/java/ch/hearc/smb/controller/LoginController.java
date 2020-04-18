package ch.hearc.smb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model, String error, String logout, String invalidtoken, String expired) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        if (invalidtoken != null)
            model.addAttribute("error", "Invalid link");

        if (expired != null)
            model.addAttribute("error", "Your link has expired");

        return "login";
    }
}

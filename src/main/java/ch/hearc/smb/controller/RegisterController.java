package ch.hearc.smb.controller;

import ch.hearc.smb.dto.PasswordDto;
import ch.hearc.smb.model.CustomUser;
import ch.hearc.smb.service.CustomUserService;
import ch.hearc.smb.service.SecurityService;
import ch.hearc.smb.validator.CustomUserValidator;
import ch.hearc.smb.validator.ResetPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.UUID;


@Controller
public class RegisterController {

    @Autowired
    CustomUserService customUserService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CustomUserValidator customUserValidator;

    @Autowired
    private ResetPasswordValidator resetPasswordValidator;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment env;

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new CustomUser());

        return "register";
    }

    @GetMapping("/user/updatePassword")
    public String updatePassword(Model model, @RequestParam("id") long id, @RequestParam("token") String token) {
        model.addAttribute("pwdForm", new PasswordDto(id,token));

        return "updatePassword";
    }


    @GetMapping("/forgotpassword")
    public String forgotPassword(Model model, String error, String ok) {
        if (error != null)
            model.addAttribute("error", "Your email is invalid.");
        if (ok != null)
            model.addAttribute("message", "email sent");

        return "forgotPassword";
    }

    @PostMapping("/forgotpassword")
    public String forgotPassword(HttpServletRequest request) {
        String email = request.getParameter("email");

        CustomUser user = customUserService.findByCustomemail(email);

        if (user == null) {
            return "redirect:/forgotpassword?error";
        }

        new Thread( () -> sendMail(user,request.getLocale())).start();


        return "redirect:/forgotpassword?ok";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("userForm") CustomUser userForm, BindingResult bindingResult) {
        customUserValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        customUserService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/";
    }


    @GetMapping("/user/changePassword")
    public String showChangePasswordPage(@RequestParam("id") long id, @RequestParam("token") String token) {
        String result = securityService.validatePasswordResetToken(id, token);
        if (result != null) {
            return "redirect:/login?" + result;
        }
        return "redirect:/user/updatePassword?id=" + id + "&token=" + token;
    }


    @PostMapping("/user/updatePassword")
    public String registration(@ModelAttribute("pwdForm") PasswordDto passwordDto, BindingResult bindingResult) {
        resetPasswordValidator.validate(passwordDto, bindingResult);
        String result = securityService.validatePasswordResetToken(passwordDto.getId(), passwordDto.getToken());

        if (result != null) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {

            return "updatePassword";
        }

        CustomUser user = customUserService.findByCustomId(passwordDto.getId());
        customUserService.changeUserPassword(user, passwordDto.getNewPassword());

        return "redirect:/login";

    }

    private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, CustomUser user) {
        String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
        String message = messageSource.getMessage("message.resetPassword",null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, CustomUser user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom("SMB");
        return email;
    }

    private void sendMail(CustomUser user, Locale locale){
        String token = UUID.randomUUID().toString();
        customUserService.createPasswordResetTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail(env.getProperty("spring.application.url"), locale, token, user));
    }

}

package ch.hearc.smb.controller;


import ch.hearc.smb.model.CustomUser;
import ch.hearc.smb.model.Role;
import ch.hearc.smb.repository.RoleRepository;
import ch.hearc.smb.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class AdminController {

    @Autowired
    CustomUserService customUserService;

    @Autowired
    RoleRepository roleRepository;

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String admin(Model model, HttpServletRequest request) {

        String user = request.getParameter("user");
        if (user != null) {
            model.addAttribute("roleUpdate", "User " + user + " has been updated");
        }

        String search = request.getParameter("search");
        if (search != null) {
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
            Role roleModo = roleRepository.findByName("ROLE_MODO");
            List<CustomUser> users = null;

            users = customUserService.findByUsernameContaining(search);

            model.addAttribute("usernames", users);
            model.addAttribute("roleAdmin", roleAdmin);
            model.addAttribute("roleModo", roleModo);
            model.addAttribute("search",search);
        }
        return "admin";
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("admin/changerole/{id}/{search}")
    public String changeRole(HttpServletRequest request, @PathVariable Long id, @PathVariable String search) {
        String admin = request.getParameter("admin");
        String modo = request.getParameter("modo");

        Set<Role> roles = new HashSet<>();

        roles.add(roleRepository.findByName("ROLE_USER"));

        if (admin != null) {
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
        }

        if (modo != null) {
            roles.add(roleRepository.findByName("ROLE_MODO"));
        }

        CustomUser user = customUserService.findByCustomId(id);
        user.setRole(roles);
        customUserService.save(user);
        return "redirect:/admin?user=" + user.getUsername() + "&search=" + search;
    }
}


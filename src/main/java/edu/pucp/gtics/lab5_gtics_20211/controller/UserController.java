package edu.pucp.gtics.lab5_gtics_20211.controller;

import edu.pucp.gtics.lab5_gtics_20211.entity.User;
import edu.pucp.gtics.lab5_gtics_20211.repository.JuegosRepository;
import edu.pucp.gtics.lab5_gtics_20211.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller

public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JuegosRepository juegosRepository;


    @GetMapping(value = {"", "/"})
    public String mainPage(Model model){
        System.out.println("ININCio");
        model.addAttribute("listaJuegosDesc", juegosRepository.findAllByOrderByNombreDesc());
        return "juegos/vista";
    }

    @GetMapping("/user/signIn")
    public String signIn(){
      return "user/signIn";
    }

    @GetMapping("/user/signInRedirect")
    public String signInRedirect(Authentication auth, HttpSession session){

        System.out.println("ENTRO");
        String rol = "";
        for (GrantedAuthority role : auth.getAuthorities()) {
            rol = role.getAuthority();
            break;
        }
        System.out.println(rol);


        String correo = auth.getName();
        User usuario = userRepository.findUserByCorreo(correo);
        session.setAttribute("usuario", usuario);



        if (rol.equals("USER")) {
            return "redirect:/juegos/lista";
        } else {
            if (rol.equals("ADMIN") ) {
                System.out.println("enviando a controller admin");
                return "redirect:/juegos/lista";
            }

        }
        return "redirect:/";

    }

}

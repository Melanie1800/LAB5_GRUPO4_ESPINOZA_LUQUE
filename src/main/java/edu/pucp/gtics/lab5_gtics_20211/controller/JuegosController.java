package edu.pucp.gtics.lab5_gtics_20211.controller;

import edu.pucp.gtics.lab5_gtics_20211.entity.Juegos;
import edu.pucp.gtics.lab5_gtics_20211.entity.Plataformas;
import edu.pucp.gtics.lab5_gtics_20211.entity.User;
import edu.pucp.gtics.lab5_gtics_20211.repository.JuegosRepository;
import edu.pucp.gtics.lab5_gtics_20211.repository.PlataformasRepository;
import edu.pucp.gtics.lab5_gtics_20211.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("juegos")
public class JuegosController {

    @Autowired
    JuegosRepository juegosRepository;

    @Autowired
    PlataformasRepository plataformasRepository;

    @GetMapping("/lista")
    public String listaJuegos(Model model) {
        /** Completar */
        String rol = "admin";
        if (rol.equalsIgnoreCase("user")) {
            model.addAttribute("listaJuegosPorUser", juegosRepository.obtenerJuegosPorUser(1));
            return "juegos/comprado";
        }
        model.addAttribute("listaJuegosAsc", juegosRepository.findAllByOrdOrderByPrecioAsc());
        return "juegos/lista";
    }

    @GetMapping(value = {"", "/", "/vista"})
    public String vistaJuegos(Model model) {

        /** Completar */
        model.addAttribute("listaJuegosDesc", juegosRepository.findAllByOrderByNombreDesc());
        return "juegos/vista";

    }

    @GetMapping("/nuevo")
    public String nuevoJuegos(Model model, @ModelAttribute("juego") Juegos juego) {
        /** Completar */
        model.addAttribute("listaPlataformas",plataformasRepository.findAll());
        return "juegos/editarFrm";
    }

    @GetMapping("/editar")
    public String editarJuegos(@ModelAttribute("juego") Juegos juegos,@RequestParam("id") int id, Model model) {
        /** Completar */
        Optional<Juegos> optionalJuegos = juegosRepository.findById(id);
        if (optionalJuegos.isPresent()) {
            model.addAttribute("listaPlataformas",plataformasRepository.findAll());
            juegos = optionalJuegos.get();
            model.addAttribute("juego", juegos);
            return "juegos/editarFrm";
        } else {
            return "redirect:/juegos/lista";
        }
    }

    @PostMapping("/guardar")
    public String guardarJuegos(Model model, RedirectAttributes attr, @ModelAttribute("juego") @Valid Juegos juego, BindingResult bindingResult) {
        /**
         * Completar
         */
        Integer idJuego = juego.getIdjuego();
        if (idJuego==null) {
            attr.addFlashAttribute("msg", "Juego creado exitosamente");
            juegosRepository.save(juego);
            return "redirect:/juegos/lista";
        } else {
            juegosRepository.save(juego);
            attr.addFlashAttribute("msg", "Juego actualizado exitosamente");
            return "redirect:/juegos/lista";
        }
    }

    @GetMapping("/borrar")
    public String borrarDistribuidora(@RequestParam("id") int id) {
        Optional<Juegos> opt = juegosRepository.findById(id);
        if (opt.isPresent()) {
            juegosRepository.deleteById(id);
        }
        return "redirect:/juegos/lista";
    }

}

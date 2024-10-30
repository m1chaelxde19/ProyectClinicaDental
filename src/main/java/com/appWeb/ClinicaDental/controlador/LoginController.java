package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.Sesion;
import com.appWeb.ClinicaDental.entidad.Usuario;
import com.appWeb.ClinicaDental.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Controller
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private Sesion sesion;
    @GetMapping
    public String login() {
        return "index";
    }

    @PostMapping("/loginValidation")
    public String validarLogin(@RequestParam("correo") String correo, @RequestParam("clave") String clave, Model model) {
        try {
            Usuario usuario= usuarioService.validarLogin(correo, clave);
            if (usuario == null) {
                model.addAttribute("error","Credenciales incorrecta");
                return "index";
            }
            sesion.setId_usuario(usuario.getId_usuario());
            sesion.setNombre(usuario.getNombre());
            model.addAttribute("nombre", usuario.getNombre());
            return "redirect:/Dashboard";
        }catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("error",e.getMessage());
            return "index";
        }

    }

    @GetMapping("/logout")
    public String logout() {
        sesion.clear();
        return "redirect:/api";
    }
}

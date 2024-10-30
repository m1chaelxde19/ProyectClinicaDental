package com.appWeb.ClinicaDental.controlador;
import com.appWeb.ClinicaDental.servicio.EnviarEmail;
import com.appWeb.ClinicaDental.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/restablecerContra")
public class ClaveController {
    @Autowired
    private EnviarEmail enviarEmail;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String clave(Model model) {
        return "restaContra";
    }

    @PostMapping
    public String actualizarClave(@RequestParam("correo") String correo, @RequestParam("codigoVerificacion") String codigo,@RequestParam("clave") String clave) {
        if (enviarEmail.verificarCodigo(correo, codigo)){
            usuarioService.actualizarClave(correo, clave);
        }
        return "index";
    }
}

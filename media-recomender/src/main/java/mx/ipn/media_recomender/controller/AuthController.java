package mx.ipn.media_recomender.controller;

import mx.ipn.media_recomender.dto.LoginDTO;
import mx.ipn.media_recomender.dto.RegisterDTO;
import mx.ipn.media_recomender.model.Usuario;
import mx.ipn.media_recomender.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UsuarioService usuarioService;
    
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }
    
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "registro";
    }
    
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("registerDTO") RegisterDTO registerDTO, 
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registro";
        }
        
        Usuario usuario = new Usuario();
        usuario.setUsername(registerDTO.getUsername());
        usuario.setEmail(registerDTO.getEmail());
        usuario.setPassword(registerDTO.getPassword());
        
        usuarioService.registrarUsuario(usuario);
        
        return "redirect:/login?registroExitoso";
    }
}
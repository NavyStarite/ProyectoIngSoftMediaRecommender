package mx.ipn.media_recomender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        String message = request.getParameter("message");
        model.addAttribute("error", message != null ? message : "Ocurrió un error inesperado");
        return "error"; // Crea una plantilla error.html
    }
}
package com.digis01.IHernandezProgramacionNCapas.Controller;

import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("login")
public class LogInController 
{
    @GetMapping
    public String Login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Model model)
    {
        if("bad_credentials".equals(error))
        {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
        }
        else if (error != null) 
        {
            model.addAttribute("error", "Usuario deshabilitado, contacte al administrador.");
        }
        if(logout != null)
        {
            model.addAttribute("logout", "Sesión cerrada correctamente");
        }
        return "LogIn";
    }
}

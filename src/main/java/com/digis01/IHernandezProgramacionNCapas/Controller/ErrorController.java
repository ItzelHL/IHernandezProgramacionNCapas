package com.digis01.IHernandezProgramacionNCapas.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.naming.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("error")
@Component
public class ErrorController implements AuthenticationFailureHandler
{
    @GetMapping("403")
    public String error403()
    {
        return "AccessDenied";
    }
    
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException 
    {
        if (exception instanceof DisabledException) 
        {
            response.sendRedirect("/login?error=disabled");
        } 
        else if (exception instanceof BadCredentialsException) 
        {
            response.sendRedirect("/login?error=bad_credentials");
        }
        else 
        {
            response.sendRedirect("/login?error=disabled");
        }
    }
}

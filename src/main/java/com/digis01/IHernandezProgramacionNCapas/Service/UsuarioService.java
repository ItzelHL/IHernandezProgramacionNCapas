package com.digis01.IHernandezProgramacionNCapas.Service;

import com.digis01.IHernandezProgramacionNCapas.DAO.IDireccionRepository;
import com.digis01.IHernandezProgramacionNCapas.DAO.IUsuarioRepository;
import com.digis01.IHernandezProgramacionNCapas.JPA.Direccion;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService 
{
    private final IDireccionRepository iDireccionRepository;
    private final IUsuarioRepository iUsuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(IUsuarioRepository iUsuarioRepository, PasswordEncoder passwordEncoder, IDireccionRepository iDireccionRepository) 
    {
        this.iUsuarioRepository = iUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.iDireccionRepository = iDireccionRepository;
    }
    
    public Result GetAll()
    {
        Result result = new Result();
        
        try 
        {
            result.object = iUsuarioRepository.findAll();
            result.correct = true;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    public Result Add(Usuario usuario)
    {
        Result result = new Result();
        
        try 
        {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            iUsuarioRepository.save(usuario);
            result.correct = true;
        } 
        catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex =ex;
        }  
        return result;
    }
    
    public Result UpdatePassword()
    {
        Result result = new Result();
        
        try 
        {
            // Traemos todos los usuarios de la BD
            List<Usuario> usuarios = iUsuarioRepository.findAll();
            
            // Configuramos un BCrypt con fuerza 12
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            
            for (Usuario usuario : usuarios) 
            {
                String password = usuario.getPassword();

                // Si la contraseña no empieza con $2a$/$2b$/$2y$, asumimos que no está hasheada
                if (password != null && !(password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$"))) 
                {
                    usuario.setPassword(encoder.encode(password));
                }
            }

            // Guardamos todos los cambios
            iUsuarioRepository.saveAll(usuarios);

            result.correct = true;
            result.object = usuarios;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
}

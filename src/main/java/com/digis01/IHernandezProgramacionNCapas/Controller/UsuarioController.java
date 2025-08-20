package com.digis01.IHernandezProgramacionNCapas.Controller;

import com.digis01.IHernandezProgramacionNCapas.DAO.ColoniaDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.EstadoDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.MunicipioDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.PaisDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.RolDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.ML.Direccion;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import com.digis01.IHernandezProgramacionNCapas.ML.Usuario;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("usuario")
public class UsuarioController 
{
    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    @Autowired
    private RolDAOImplementation rolDAOImplementation;
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;
    
    @GetMapping // localhost:8080/usuario
    public String Index(Model model)
    {
        Result result = usuarioDAOImplementation.GetAll();
        
        if (result.correct) {
            model.addAttribute("usuarios", result.objects);
        } else  {
            model.addAttribute("usuarios", null);
        }
        
        return "UsuarioIndex";
    }
    
    @GetMapping("/action/{IdUsuario}") // localhost:8080/usuario/usuarioDetail/{idUsuario}
    public String Add( Model model, @PathVariable ("IdUsuario") int IdUsuario)
    {
        if(IdUsuario == 0) //usuario no existe - usuarioDireccionAdd
        {
            //        Result result = rolDAOImplementation.GetAll();
            model.addAttribute("paises", paisDAOImplementation.GetAllPais().objects);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            Usuario usuario = new Usuario();
            model.addAttribute("usuario", usuario);

            return "UsuarioForm";
        } else //usuario existe - usuarioDetail 
        {
            Result result = usuarioDAOImplementation.DireccionesByIdUsuario(IdUsuario);

            if (result.correct) 
            {
                model.addAttribute("usuario", result.object);
            } else 
            {
                return "Error";
            }
            return "UsuarioDetail";
        }
    }
    
    @PostMapping("add")
    public String Add(@Valid @ModelAttribute("usuario") Usuario usuario, 
                                BindingResult bindingResult, 
                                Model model, 
                                @RequestParam("imagenFile") MultipartFile imagen)
    {
        if (bindingResult.hasErrors())
        {
            model.addAttribute("paises", paisDAOImplementation.GetAllPais().objects);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            model.addAttribute("usuario", usuarioDAOImplementation.Add(usuario));
            return "UsuarioForm";
        }
        else{
            if(imagen != null)
            {
                String nombre = imagen.getOriginalFilename();
                String extension = nombre.split("\\.")[1];
                if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) 
                {
                    try 
                    {
                        byte[] bytes = imagen.getBytes();
                        String base64Image = Base64.getEncoder().encodeToString(bytes);
                        usuario.setImagen(base64Image);
                    } catch (Exception ex) 
                    {
                        System.out.println("Solo se permiten archivos .jpg, .jpeg, .png");
                    }
                }
            }
            Result result = usuarioDAOImplementation.Add(usuario);
            return "redirect:/usuario";
        }
    }
    
    @GetMapping("formEditable")
    public String FormEditable(@RequestParam int IdUsuario, 
                                                @RequestParam(required = false) Integer IdDireccion, 
                                                Model model) 
    {
        if (IdDireccion == null)  //Editar usuario - return UsuarioForm - usuarioGetById
        {
            Result result = usuarioDAOImplementation.GetById(IdUsuario);
            if(result.correct && result.object != null)
            {
                Usuario usuario = (Usuario) result.object;
                if (usuario.getDireccion() == null) 
                {
                    usuario.setDireccion(new ArrayList<>());
                    usuario.Direccion.add(new Direccion(-1));
                }
                
                model.addAttribute("paises", paisDAOImplementation.GetAllPais().objects);
                model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
                model.addAttribute("usuario", usuario);
            }
            else
            {
                model.addAttribute("error", result.errorMessage);
            }
            return "UsuarioForm";
        } else if (IdDireccion == 0) //Agregar dirección
        {
            Result result;
        } else 
        {
        }
        return "";
    }
    
    @PostMapping("Update")
    public String Update(@Valid Usuario usuario,
                                        BindingResult bindingResult,
                                        Model model)
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("paises", paisDAOImplementation.GetAllPais().objects);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            model.addAttribute("usuario", usuario);
            
            return "UsuarioDetail";
        }
        usuarioDAOImplementation.UpdateUsuario(usuario);
        return "redirect:/action/{IdUsuario}";
    }
    
//    @GetMapping("getPaises")
//    @ResponseBody
//    public Result GetAllPais()
//    {
//        return paisDAOImplementation.GetAllPais();
//    }
    
    @GetMapping("getEstadosByIdPais/{idPais}")
    @ResponseBody
    public Result EstadosByPais(@PathVariable int idPais)
    {
        return estadoDAOImplementation.EstadoByPaisGetAll(idPais);
    }
    
    @GetMapping("getMunicipiosByIdEstado/{idEstado}")
    @ResponseBody
    public Result MunicipiosByIdEstado(@PathVariable int idEstado)
    {
        return municipioDAOImplementation.MunicipioByEstadoGetAll(idEstado);
    }
    //comentario prueba
    
    @GetMapping("getColoniasByIdMunicipio/{idMunicipio}")
    @ResponseBody
    public Result ColoniasByIdMunicipio(@PathVariable int idMunicipio)
    {
        return coloniaDAOImplementation.ColoniaByMunicipioGetAll(idMunicipio);
    }
}

package com.digis01.IHernandezProgramacionNCapas.Controller;

import com.digis01.IHernandezProgramacionNCapas.DAO.ColoniaDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.EstadoDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.MunicipioDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.PaisDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.RolDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import com.digis01.IHernandezProgramacionNCapas.ML.Usuario;
import jakarta.validation.Valid;
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
            model.addAttribute("Usuario", new Usuario());

            return "UsuarioForm";
        } else //usuario existe - usuarioDetail 
        {
            Result result = usuarioDAOImplementation.DireccionesByIdUsuario(IdUsuario);

            if (result.correct) 
            {
                model.addAttribute("Usuario", result.object);
            } else 
            {
                return "Error";
            }
            return "UsuarioDetail";
        }
    }
    
    @GetMapping("formEditable")
    public String FormEditable(@RequestParam int IdUsuario, 
                                                @RequestParam(required = false) Integer IdDireccion, 
                                                Model model) 
    {
        if (IdDireccion == null)  //Editar usuario - return UsuarioForm - usuarioGetById
        {
            Result result = usuarioDAOImplementation.DireccionesByIdUsuario(IdUsuario);
            if(result.correct)
            {
                model.addAttribute("Usuario", result.object);
            }
            else
            {
                model.addAttribute("Usuario", null);
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
    
    @PostMapping("action")
    public String Add(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, Model model)
    {
        
        if (bindingResult.hasErrors())
        {
            model.addAttribute("usuario", usuario);
            return "UsuarioForm";
        }
        else{
            Result result = usuarioDAOImplementation.Add(usuario);
            return "redirect:/usuario";
        }
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

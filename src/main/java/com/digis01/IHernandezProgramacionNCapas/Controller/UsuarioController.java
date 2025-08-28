package com.digis01.IHernandezProgramacionNCapas.Controller;

import com.digis01.IHernandezProgramacionNCapas.DAO.ColoniaDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.ColoniaJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.EstadoDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.EstadoJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.MunicipioDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.MunicipioJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.PaisDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.PaisJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.RolDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.RolJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.UsuarioJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.ML.Colonia;
import com.digis01.IHernandezProgramacionNCapas.ML.Direccion;
import com.digis01.IHernandezProgramacionNCapas.ML.ErrorCM;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import com.digis01.IHernandezProgramacionNCapas.ML.Rol;
import com.digis01.IHernandezProgramacionNCapas.ML.Usuario;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
public class UsuarioController {

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
    
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    @Autowired
    private RolJPADAOImplementation rolJPADAOImplementation;
    @Autowired
    private PaisJPADAOImplementation paisJPADAOImplementation;
    @Autowired
    private EstadoJPADAOImplementation estadoJPADAOImplementation;
    @Autowired
    private MunicipioJPADAOImplementation municipioJPADAOImplementation;
    @Autowired
    private ColoniaJPADAOImplementation coloniaJPADAOImplementation;

    @GetMapping // localhost:8080/usuario
    public String Index(Model model) 
    {
//        Result result = usuarioDAOImplementation.GetAll(new Usuario(" ", " ", " ", new Rol()));
        
        Result result = usuarioJPADAOImplementation.GetAll();
        
        model.addAttribute("usuarioBusqueda", new Usuario());
        model.addAttribute("roles", rolJPADAOImplementation.GetAll().objects);
        
        if (result.correct) {
            model.addAttribute("usuarios", result.objects);
        } else {
            model.addAttribute("usuarios", null);
        }

        return "UsuarioIndex";
    }
    
    @PostMapping() // localhost:8080/usuario/add
    public String Index(Model model, @ModelAttribute("usuarioBusqueda") Usuario usuarioBusqueda) 
    {
//        Result result = usuarioDAOImplementation.GetAll(usuarioBusqueda);
        Result result = usuarioJPADAOImplementation.GetAll();
        model.addAttribute("roles", rolJPADAOImplementation.GetAll().objects);
        model.addAttribute("usuarios", result.objects);
        return "UsuarioIndex";
    }

    @GetMapping("/action/{IdUsuario}") // localhost:8080/usuario/action/{idUsuario}
    public String Add(Model model, @PathVariable("IdUsuario") int IdUsuario) 
    {
        if (IdUsuario == 0) //usuario no existe - usuarioDireccionAdd
        {
            //        Result result = rolDAOImplementation.GetAll();
            model.addAttribute("paises", paisJPADAOImplementation.GetAllPais().objects);
            model.addAttribute("roles", rolJPADAOImplementation.GetAll().objects);
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

    @PostMapping("add") // localhost:8080/usuario/add
    public String Add(@Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult,
            Model model,
            @RequestParam("imagenFile") MultipartFile imagen) 
    {
        if (bindingResult.hasErrors()) 
        {
            model.addAttribute("usuario", usuario);
            return "UsuarioForm";
        } else {
            if (imagen != null) 
            {
                String nombre = imagen.getOriginalFilename();
                String extension = nombre.split("\\.")[1];
                if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
                    try {
                        byte[] bytes = imagen.getBytes();
                        String base64Image = Base64.getEncoder().encodeToString(bytes);
                        usuario.setImagen(base64Image);
                    } catch (Exception ex) {
                        System.out.println("Solo se permiten archivos .jpg, .jpeg, .png");
                    }
                }
            }
            Result result = usuarioJPADAOImplementation.Add(usuario);
            model.addAttribute("paises", paisJPADAOImplementation.GetAllPais().objects);
            model.addAttribute("roles", rolJPADAOImplementation.GetAll().objects);
            return "redirect:/usuario";
        }
    }
    
    

    @GetMapping("formEditable") // localhost:8080/usuario/formEditable
    public String FormEditable(@RequestParam int IdUsuario,
            @RequestParam(required = false) Integer IdDireccion,
            Model model) 
    {
        if (IdDireccion == null) //Editar usuario - return UsuarioForm - usuarioGetById // 
        {
            Result result = usuarioDAOImplementation.GetById(IdUsuario);
            if (result.correct && result.object != null) {
                Usuario usuario = (Usuario) result.object;
                if (usuario.getDireccion() == null) {
                    usuario.setDireccion(new ArrayList<>());
                    usuario.Direccion.add(new Direccion(-1));
                }

                model.addAttribute("paises", paisJPADAOImplementation.GetAllPais().objects);
                model.addAttribute("roles", rolJPADAOImplementation.GetAll().objects);
                model.addAttribute("usuario", usuario);
            } else {
                model.addAttribute("error", result.errorMessage);
            }
            return "UsuarioForm";
        } else if (IdDireccion == 0) //Agregar dirección
        {
            Result result = usuarioDAOImplementation.AddDireccion(IdUsuario);
            if (result.correct && result.object != null) {
                Usuario usuario = (Usuario) result.object;
                if (usuario.getDireccion() == null) {
                    usuario.setDireccion(new ArrayList<>());
                    usuario.Direccion.add(new Direccion());
                }
                model.addAttribute("paises", paisJPADAOImplementation.GetAllPais().objects);
                model.addAttribute("roles", rolJPADAOImplementation.GetAll().objects);
                model.addAttribute("usuario", usuarioDAOImplementation.AddDireccion(IdUsuario));

                return "UsuarioForm";
            } else {

            }
            return "UsuarioForm";
        }
        return "redirect:/usuario";
    }

    @PostMapping("update") // localhost:8080/usuario/update
    public String Update(@Valid @ModelAttribute("Usuario") Usuario usuario,
                                        BindingResult bindingResult,
                                        Model model) 
    {
        if (bindingResult.hasErrors()) 
        {
            
            model.addAttribute("paises", paisJPADAOImplementation.GetAllPais().objects);
            model.addAttribute("roles", rolJPADAOImplementation.GetAll().objects);
            model.addAttribute("usuario", usuario);

            return "UsuarioDetail";
        }
        usuarioDAOImplementation.UpdateUsuario(usuario);
        return "redirect:/action/{IdUsuario}";
    }

    @GetMapping("cargaMasiva") // localhost:8080/usuario/cargaMasiva
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("cargaMasiva") // localhost:8080/usuario/cargaMasiva
    public String CargaMasiva(@RequestParam("archivo") MultipartFile file, Model model, HttpSession session) 
    {
        String root = System.getProperty("user.dir");
        String rutaArchivo = "/src/main/resources/archivos/";
        String fechaSubida = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
        String rutaFinal = root + rutaArchivo + fechaSubida + file.getOriginalFilename();
        
        try 
        {
            file.transferTo(new File(rutaFinal));
        } catch (Exception ex) 
        {
            System.out.println(ex.getLocalizedMessage());
        }
        
        if (file.getOriginalFilename().split("\\.")[1].equals("txt")) //txt
        {
            List<Usuario> usuarios = ProcesarTXT(new File(rutaFinal));
            List<ErrorCM> errores = ValidarDatos(usuarios);

            if (errores.isEmpty()) 
            {
                model.addAttribute("listaErrores", errores);
                model.addAttribute("archivoCorrecto", true);
                session.setAttribute("path", rutaFinal);
            } else 
            {
                model.addAttribute("listaErrores", errores);
                model.addAttribute("archivoCorrecto", false);
            }
        } else // excel
        {
            List<Usuario> usuarios = ProcesarExcel(new File(rutaFinal));
            List<ErrorCM> errores = ValidarDatos(usuarios);

            if (errores.isEmpty()) 
            {
                model.addAttribute("listaErrores", errores);
                model.addAttribute("archivoCorrecto", true);
                session.setAttribute("path", rutaFinal);
            } else 
            {
                model.addAttribute("listaErrores", errores);
                model.addAttribute("archivoCorrecto", false);
            }
        }
        return "CargaMasiva";
    }
    
    @GetMapping("cargaMasiva/procesar") // localhost:8080/usuario/cargaMasiva/procesar
    public String CargaMasiva(HttpSession session)
    {
        try 
        {
            String ruta = session.getAttribute("path").toString();
            List<Usuario> usuarios;
            
            if(ruta.split("\\.")[1].equals("txt"))
            {
                usuarios = ProcesarTXT(new File(ruta));
            }
            else
            {
                usuarios = ProcesarExcel(new File(ruta));
            }
            
            for(Usuario usuario : usuarios)
            {
                usuarioDAOImplementation.Add(usuario);
            }
            
            session.removeAttribute("path");
        } catch (Exception ex) 
        {
            System.out.println(ex.getLocalizedMessage());
        }
        return "redirect:/usuario";
    }

    // MÉTODOS CARGA MASIVA
    private List<Usuario> ProcesarTXT(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String linea = "";
            List<Usuario> usuarios = new ArrayList<>();
            while ((linea = bufferedReader.readLine()) != null) {
                String[] campo = linea.split("\\|");
                Usuario usuario = new Usuario();
                usuario.setUsername(campo[0]);
                usuario.setNombre(campo[1]);
                usuario.setApellidoPaterno(campo[2]);
                usuario.setApellidoMaterno(campo[3]);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha =  campo[4] == "" ? null : format.parse(campo[4]);
                usuario.setFechaNacimiento(fecha);
                usuario.setSexo(campo[5]);
                usuario.setCurp(campo[6]);
                usuario.setEmail(campo[7]);
                usuario.setPassword(campo[8]);
                usuario.setTelefono(campo[9]);
                usuario.setCelular(campo[10]);

                usuario.Rol = new Rol();
                Integer idRol = campo[11] == "" ? null : Integer.parseInt(campo[11]);
                usuario.Rol.setIdRol(idRol);
                
                usuario.Direccion = new ArrayList<>();
                Direccion direccion = new Direccion();
                direccion.setCalle(campo[12]);
                direccion.setNumeroExterior(campo[13]);
                direccion.setNumeroInterior(campo[14]);
                
                direccion.Colonia = new Colonia();
                Integer idColonia = campo[15] == "" ? null : Integer.parseInt(campo[15]);
                direccion.Colonia.setIdColonia(idColonia);
                usuario.Direccion.add(direccion);
                
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (Exception ex) 
        {
            System.out.println("Error");
            return null;
        }
    }
    
    private List<Usuario> ProcesarExcel(File file)
    {
        List<Usuario> usuarios = new ArrayList<>();
        try
        {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row : sheet)
            {
                Usuario usuario = new Usuario();
                usuario.setUsername(row.getCell(0) != null ? row.getCell(0).toString() : "");
                usuario.setNombre(row.getCell(1) != null ? row.getCell(1).toString() : "");
                usuario.setApellidoPaterno(row.getCell(2) != null ? row.getCell(2).toString() : "");
                usuario.setApellidoMaterno(row.getCell(3) != null ? row.getCell(3).toString() : "");
                SimpleDateFormat format = new SimpleDateFormat();
                if(row.getCell(4) != null)
                {
                    if(row.getCell(4).getCellType() == CellType.NUMERIC || DateUtil.isCellDateFormatted(row.getCell(4)))
                    {
                        usuario.setFechaNacimiento(row.getCell(4).getDateCellValue());
                    }
                    else
                    {
                        usuario.setFechaNacimiento(format.parse(row.getCell(4).toString()));
                    }
                }
                usuario.setSexo(row.getCell(5) != null ? row.getCell(5).toString() : "");
                usuario.setCurp(row.getCell(6) != null ? row.getCell(6).toString() : "");
                usuario.setEmail(row.getCell(7) != null ? row.getCell(7).toString() : "");
                usuario.setPassword(row.getCell(8) != null ? row.getCell(8).toString() : "");
                DataFormatter dataFormatter = new DataFormatter();
                usuario.setTelefono(row.getCell(9) != null ? dataFormatter.formatCellValue(row.getCell(9)) : "");
                usuario.setCelular(row.getCell(10) != null ? dataFormatter.formatCellValue(row.getCell(10)) : "");
                
                usuario.Rol = new Rol();
                usuario.Rol.setIdRol(row.getCell(11) != null  ? (int) row.getCell(11).getNumericCellValue() : 0);
                
                usuario.Direccion = new ArrayList<>();
                Direccion direccion = new Direccion();
                direccion.setCalle(row.getCell(12) != null ? row.getCell(12).toString() : "");
                direccion.setNumeroExterior(row.getCell(13) != null ? dataFormatter.formatCellValue(row.getCell(13)) : "");
                direccion.setNumeroInterior(row.getCell(14) != null ? dataFormatter.formatCellValue(row.getCell(14)) : "");
                
                direccion.Colonia = new Colonia();
                direccion.Colonia.setIdColonia(row.getCell(15) != null ? (int) row.getCell(15).getNumericCellValue() : 0);
                usuario.Direccion.add(direccion);
                
                usuarios.add(usuario);
            }
            return usuarios;
        } 
        catch (Exception ex) 
        {
            return null;
        }
    }

    private List<ErrorCM> ValidarDatos(List<Usuario> usuarios) {
        List<ErrorCM> errores = new ArrayList<>();
        int linea = 1;
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername() == null || usuario.getUsername() == "") {
                errores.add(new ErrorCM(linea, usuario.getUsername(), "El campo USERNAME es obligatorio"));
            }
            if (usuario.getNombre() == null || usuario.getNombre() == "") {
                errores.add(new ErrorCM(linea, usuario.getNombre(), "El campo NOMBRE es obligatorio"));
            }
            if (usuario.getApellidoPaterno() == null || usuario.getApellidoPaterno() == "") {
                errores.add(new ErrorCM(linea, usuario.getApellidoPaterno(), "El campo APELLIDO PATERNO es obligatorio"));
            }
            if (usuario.getApellidoMaterno() == null || usuario.getApellidoMaterno() == "") {
                errores.add(new ErrorCM(linea, usuario.getApellidoMaterno(), "El campo APELLIDO MATERNO es obligatorio"));
            }
            if (usuario.getFechaNacimiento() == null || usuario.getFechaNacimiento().equals("")) {
                errores.add(new ErrorCM(linea,"fecha vacia", "El campo FECHA DE NACIMIENTO es obligatorio"));
            }
            if (usuario.getSexo() == null || usuario.getSexo() == "") {
                errores.add(new ErrorCM(linea, usuario.getSexo(), "El campo SEXO es obligatorio"));
            }
            if (usuario.getCurp() == null || usuario.getCurp() == "") {
                errores.add(new ErrorCM(linea, usuario.getCurp(), "El campo CURP es obligatorio"));
            }
            if (usuario.getEmail() == null || usuario.getEmail() == "") {
                errores.add(new ErrorCM(linea, usuario.getEmail(), "El campo EMAIL es obligatorio"));
            }
            if (usuario.getPassword() == null || usuario.getPassword() == "") {
                errores.add(new ErrorCM(linea, usuario.getPassword(), "El campo PASSWORD es obligatorio"));
            }
            if (usuario.getTelefono() == null || usuario.getTelefono() == "") {
                errores.add(new ErrorCM(linea, usuario.getTelefono(), "El campo TELEFONO es obligatorio"));
            }
            if (usuario.getCelular() == null || usuario.getCelular() == "") {
                errores.add(new ErrorCM(linea, usuario.getCelular(), "El campo CELULAR es obligatorio"));
            }
            if (usuario.Rol.getIdRol() <= 0) {
                errores.add(new ErrorCM(linea, String.valueOf(usuario.Rol.getIdRol()) , "El campo ID ROL debe ser mayor a cero"));
            }
            if (usuario.Direccion.get(0).getCalle() == null || usuario.Direccion.get(0).getCalle() == "") {
                errores.add(new ErrorCM(linea, usuario.getNombre(), "El campo CALLE es obligatorio"));
            }
            if (usuario.Direccion.get(0).getNumeroExterior() == null || usuario.Direccion.get(0).getNumeroExterior() == "") {
                errores.add(new ErrorCM(linea, usuario.Direccion.get(0).getNumeroExterior(), "El campo NÚMERO EXTERIOR es obligatorio"));
            }
            if (usuario.Direccion.get(0).getNumeroInterior() == null || usuario.Direccion.get(0).getNumeroInterior() == "") {
                errores.add(new ErrorCM(linea, usuario.Direccion.get(0).getNumeroInterior(), "El campo NÚMERO INTERIOR es obligatorio"));
            }
            if (usuario.Direccion.get(0).Colonia.getIdColonia()<= 0) {
                errores.add(new ErrorCM(linea, String.valueOf(usuario.Direccion.get(0).Colonia.getIdColonia()), "El campo ID COLONIA debe ser mayor a cero"));
            }
            linea++;
        }
        return errores;
    }

//    TRAER LA INFO DE LOS DDL DE ESTADO, MUNICIPIO, COLONIA
    @GetMapping("getEstadosByIdPais/{idPais}")
    @ResponseBody
    public Result EstadosByPais(@PathVariable int idPais) 
    {
//        return estadoDAOImplementation.EstadoByPaisGetAll(idPais);
        return estadoJPADAOImplementation.EstadoByPaisGetAll(idPais);
    }

    @GetMapping("getMunicipiosByIdEstado/{idEstado}")
    @ResponseBody
    public Result MunicipiosByIdEstado(@PathVariable int idEstado) 
    {
//        return municipioDAOImplementation.MunicipioByEstadoGetAll(idEstado);
        return municipioJPADAOImplementation.MunicipioByEstadoGetAll(idEstado);
    }

    @GetMapping("getColoniasByIdMunicipio/{idMunicipio}")
    @ResponseBody
    public Result ColoniasByIdMunicipio(@PathVariable int idMunicipio) 
    {
//        return coloniaDAOImplementation.ColoniaByMunicipioGetAll(idMunicipio);
        return coloniaJPADAOImplementation.ColoniaByMunicipioGetAll(idMunicipio);
    }
}

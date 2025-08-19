package com.digis01.IHernandezProgramacionNCapas.ML;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class Usuario 
{
    private int IdUsuario;
//    @NotEmpty(message = "El username debe iniciar con 2 mayúsculas")
//    @Pattern(regexp = "[A-ZñÑ]{2}[a-z?0-9_ñÑ]+", message = "Formato de Username incorrecto")
//    @Size(min = 2, max = 36, message = "Username entre 2 y 36 carácteres, incluyendo letras y números, sin espacios ni acentos")
    private String Username;
    
//    @Pattern(regexp = "[A-Za-z áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ]+", message = "Este campo no puede contener números ni carácteres especiales como {}, [], @, #, $, %, ^, &, *, (, ), _, -, +, =, |, \\, :, ;, \", ', <, >, ?, /, etc")
//    @Size(min = 2, max = 50, message = "Texto de entre 2 y 50 letras, incluyendo espacios")
//    @NotEmpty(message = "Información necesaria")
    private String Nombre;
    
//    @Pattern(regexp = "[A-Za-z áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ]+", message = "Este campo no puede contener números ni carácteres especiales como {}, [], @, #, $, %, ^, &, *, (, ), _, -, +, =, |, \\, :, ;, \", ', <, >, ?, /, etc")
//    @NotEmpty(message = "Información necesaria")
    private String ApellidoPaterno;
    
//    @Pattern(regexp = "[A-Za-z áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ]+", message = "Este campo no puede contener números ni carácteres especiales como {}, [], @, #, $, %, ^, &, *, (, ), _, -, +, =, |, \\, :, ;, \", ', <, >, ?, /, etc")
//    @NotEmpty(message = "Información necesaria")
    private String ApellidoMaterno;
    
//    @Pattern(regexp = "^[^\\s@!\"#$%&/()=?¡,{}[\\]¨*´+^`;:]+@[^\\s@]+\\.[^\\s@\\d]+$", message = "Este no es el formato para un email.")
//    @NotEmpty(message = "Información necesaria")
    private String Email;
    
//    @Pattern(regexp = "^(?=.*[A-Z].*[<>,.-;:_{}[\\]^`´\\+¨\\*~\\|!\"#\\$%&\\/\\(\\)=\\?¡'¿])(?!.*(?:012|123|234|345|456|567|678|789|890))[A-z\\d\\W]{8,}$")
//    @Size(min = 8, message = "Mínimo 8 carácteres, incluyendo al menos 1 mayúscula, 1 carácter especial y números no ")
//    @NotEmpty(message = "Información necesaria")
    private String Password;
    
//    @Past
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FechaNacimiento;
    
//    @NotEmpty(message = "Información necesaria")
    private String Sexo;
    
//    @NotEmpty(message = "Información necesaria")
    private String Telefono;
    
//    @NotEmpty(message = "Información necesaria")
    private String Celular;
    
//    @NotEmpty(message = "Información necesaria")
    private String Curp;
    
//    @NotEmpty(message = "Información necesaria")
    public Rol Rol;
    
//    @NotEmpty(message = "Información necesaria")
    public List<Direccion> Direccion;
    
    private String Imagen;
    
    public Usuario(){}
    
    public Usuario(int idUsuario, String username, String nombre, String apellidoPaterno, String apellidoMaterno, String email, 
            String password, Date fechaNacimiento, String sexo, String telefono, String celular, String curp)
    {
        this.IdUsuario = idUsuario;
        this.Username = username;
        this.Nombre = nombre;
        this.ApellidoPaterno = apellidoPaterno;
        this.ApellidoMaterno = apellidoMaterno;
        this.Email = email;
        this.Password = password;
        this.FechaNacimiento = fechaNacimiento;
        this.Sexo = sexo;
        this.Telefono = telefono;
        this.Celular = celular;
        this.Curp = curp;
    }
    
    public void setIdUsuario(int idUsuario) 
    {
        this.IdUsuario = idUsuario;
    }
    public int getIdUsuario() 
    {
        return IdUsuario;
    }
    
    public void setUsername(String username)
    {
        this.Username = username;
    }
    public String getUsername()
    {
        return Username;
    }
    
    public void setNombre(String nombre)
    {
        this.Nombre = nombre;
    }
    public String getNombre()
    {
        return Nombre;
    }
    
    public void setApellidoPaterno(String apellidoPaterno)
    {
        this.ApellidoPaterno = apellidoPaterno;
    }
    public String getApellidoPaterno()
    {
        return ApellidoPaterno;
    }
    
    public void setApellidoMaterno(String apellidoMaterno)
    {
        this.ApellidoMaterno = apellidoMaterno;
    }
    public String getApellidoMaterno()
    {
        return ApellidoMaterno;
    }
    
    public void setEmail(String email)
    {
        this.Email = email;
    }
    public String getEmail()
    {
        return Email;
    }
    
    public void setPassword(String password)
    {
        this.Password = password;
    }
    public String getPassword()
    {
        return Password;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento)
    {
        this.FechaNacimiento = fechaNacimiento;
    }
    public Date getFechaNacimiento()
    {
        return FechaNacimiento;
    }
    
    public void setSexo(String sexo)
    {
        this.Sexo = sexo;
    }
    public String getSexo()
    {
        return Sexo;
    }
    
    public void setTelefono(String telefono)
    {
        this.Telefono = telefono;
    }
    public String getTelefono()
    {
        return Telefono;
    }
    
    public void setCelular(String celular)
    {
        this.Celular = celular;
    }
    public String getCelular()
    {
        return Celular;
    }
    
    public void setCurp(String curp)
    {
        this.Curp = curp;
    }
    public String getCurp()
    {
        return Curp;
    }

    public void setRol(Rol rol) 
    {
        this.Rol = rol;
    }
    public Rol getRol() 
    {
        return Rol;
    }
   
    public void setDireccion(List<Direccion> direccion)
    {
        this.Direccion = direccion;
    }
    public List<Direccion> getDireccion() 
    {
        return Direccion;
    }

    public void setImagen(String imagen) 
    {
        this.Imagen = imagen;
    }
    public String getImagen() 
    {
        return Imagen;
    }

    
    
    
    
}

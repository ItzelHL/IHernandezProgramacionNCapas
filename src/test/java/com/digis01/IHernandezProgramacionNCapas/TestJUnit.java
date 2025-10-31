package com.digis01.IHernandezProgramacionNCapas;

import com.digis01.IHernandezProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.ML.Colonia;
import com.digis01.IHernandezProgramacionNCapas.ML.Direccion;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import com.digis01.IHernandezProgramacionNCapas.ML.Rol;
import com.digis01.IHernandezProgramacionNCapas.ML.Usuario;
import java.util.ArrayList;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestJUnit 
{
    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    
    @Test
    public void GetAllUsers()
    {
        Usuario usuario = new Usuario();
        usuario.Rol = new Rol();
//        usuario.setNombre("Alberto");
//        usuario.setApellidoPaterno("Carmona");
        usuario.setApellidoMaterno("López");
        Result result = usuarioDAOImplementation.GetAll(usuario);
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertNotNull(result.objects);
        Assertions.assertInstanceOf(Result.class, result);
    }
    
    @Test
    public void GetUserById()
    {
        Usuario usuario = new Usuario();
        usuario.Rol = new Rol();
        
        Result result = usuarioDAOImplementation.GetById(6);
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertNotNull(result.object);
        Assertions.assertEquals(result, result);
    }
    
    @Test
    public void DireccionesByIdUsuario()
    {
        Usuario usuario = new Usuario();
        usuario.Rol = new Rol();
        
        Result result = usuarioDAOImplementation.DireccionesByIdUsuario(8);
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertNotNull(result.object);
        
    }
    
    @Test
    public void Add()
    {
        Usuario usuario = new Usuario();
        usuario.Rol = new Rol();
        
        usuario.setUsername("PLop89");
        usuario.setNombre("Patricia");
        usuario.setApellidoPaterno("López");
        usuario.setApellidoMaterno("Romero");
        usuario.setFechaNacimiento(new Date(1994, 9, 9));
        usuario.setSexo("F");
        usuario.setCurp("LORP890909MDFRRN05");
        usuario.setEmail("patylop94@example.com");
        usuario.setPassword("PLop89*@");
        usuario.setTelefono("5556678899");
        usuario.setCelular("5523345567");
        usuario.Rol.setIdRol(3);
        usuario.Direccion = new ArrayList<>();
        Direccion direccion = new Direccion();
        direccion.setCalle("Calle San Martín");
        direccion.setNumeroExterior("No. 99");
        direccion.setNumeroInterior("7B");
        direccion.Colonia = new Colonia();
        direccion.Colonia.setIdColonia(14);
        usuario.Direccion.add(direccion);
        
        Result result = usuarioDAOImplementation.Add(usuario);
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertInstanceOf(Result.class, result);
    }
    
    @Test
    public void AddDireccion()
    {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(6);
        
        usuario.Direccion = new ArrayList<>();
        Direccion direccion = new Direccion();
        direccion.setCalle("Río Tíber");
        direccion.setNumeroExterior("75");
        direccion.setNumeroInterior("2");
        direccion.Colonia = new Colonia();
        direccion.Colonia.setIdColonia(6);
        usuario.Direccion.add(direccion);
        
        Result result = usuarioDAOImplementation.AddDireccion(usuario);
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertInstanceOf(Result.class, result);
    }
    
    @Test
    public void Update()
    {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(204);
        usuario.setUsername("SGom90");
        usuario.setNombre("Sofía");
        usuario.setApellidoPaterno("Hernández");
        usuario.setApellidoMaterno("Luna");
        usuario.setEmail("sofiaherlun@example.com");
        usuario.setPassword("SGom90*@");
        usuario.setFechaNacimiento(new Date(2001, 12, 16));
        usuario.setSexo("F");
        usuario.setTelefono("5589786548");
        usuario.setCelular("5698781234");
        usuario.setCurp("HELS011612MDFRNPA3");
        usuario.Rol = new Rol();
        usuario.Rol.setIdRol(3);
        
        Result result = usuarioDAOImplementation.UpdateUsuario(usuario);
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertInstanceOf(Result.class, result);
    }
}

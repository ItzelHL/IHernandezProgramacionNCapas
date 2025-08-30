package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Colonia;
import com.digis01.IHernandezProgramacionNCapas.JPA.Direccion;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPADAO 
{
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() 
    {
        Result result = new Result();

        try 
        {
            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario ORDER BY IdUsuario", Usuario.class);
            List<Usuario> usuarios = queryUsuario.getResultList();
            
            result.objects = new ArrayList<>();
            
            for (Usuario usuario : usuarios) 
            {
                result.objects.add(new com.digis01.IHernandezProgramacionNCapas.ML.Usuario(usuario));
            }
            
            result.correct = true;
        } 
        catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Override
    public Result GetById(int idUsuario)
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuarioJPA = entityManager.find(Usuario.class, idUsuario);
            com.digis01.IHernandezProgramacionNCapas.ML.Usuario usuarioML = new com.digis01.IHernandezProgramacionNCapas.ML.Usuario(usuarioJPA);
            result.object = usuarioML;
            
            result.correct = true;
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Override
    @Transactional
    public Result Add(com.digis01.IHernandezProgramacionNCapas.ML.Usuario usuarioML) 
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuarioJPA = new Usuario(usuarioML);
            entityManager.persist(usuarioJPA);
            
            result.correct = true;
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Transactional
    @Override
    public Result Delete(int idUsuario) 
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuarioJPA = entityManager.find(Usuario.class, idUsuario);
            entityManager.remove(usuarioJPA);
            result.correct = true;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Transactional
    @Override
    public Result Update(com.digis01.IHernandezProgramacionNCapas.ML.Usuario usuarioML)
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuarioBD = entityManager.find(Usuario.class, usuarioML.getIdUsuario());
            Usuario usuarioJPA = new Usuario(usuarioML);
            usuarioJPA.Direcciones = usuarioBD.Direcciones;
            entityManager.merge(usuarioJPA);
            
            result.correct = true;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }       
        return result;
    }
}

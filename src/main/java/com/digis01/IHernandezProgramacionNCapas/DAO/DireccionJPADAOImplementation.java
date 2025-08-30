package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Direccion;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPADAO
{
    @Autowired
        private EntityManager entityManager;
    
    @Override
    public Result AddDireccion(int IdUsuario)
    {
        Result result = new Result();
        
        try 
        {
            com.digis01.IHernandezProgramacionNCapas.ML.Direccion direccionML = new com.digis01.IHernandezProgramacionNCapas.ML.Direccion();
//            direccionML = new ArrayList<>();
            
            com.digis01.IHernandezProgramacionNCapas.ML.Usuario usuarioML = new com.digis01.IHernandezProgramacionNCapas.ML.Usuario();
            Usuario usuarioJPA = new Usuario(usuarioML);
            
            List<Direccion> direccionJPA = usuarioJPA.Direcciones;
//            usuarioJPA.Direcciones = new Direccion(direccionML);
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    @Transactional
    @Override
    public Result Delete(int IdDireccion) 
    {
        Result result = new Result();
        
        try 
        {
            Direccion direccionJPA = entityManager.find(Direccion.class, IdDireccion);
            entityManager.remove(direccionJPA);
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

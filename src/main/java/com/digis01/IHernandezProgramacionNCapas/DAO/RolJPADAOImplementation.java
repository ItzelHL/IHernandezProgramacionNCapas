package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Rol;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolJPADAOImplementation implements IRolJPADAO
{
    @Autowired
    EntityManager entityManager;

    @Override
    public Result GetAll()
    {
        Result result = new Result();
        
        try 
        {
            TypedQuery<Rol> queryRol = entityManager.createQuery("FROM Rol ORDER BY IdRol", Rol.class);
            List<Rol> roles = queryRol.getResultList();
            
            result.objects = new ArrayList<>();
            
            for (Rol rol : roles) 
            {
                result.objects.add(new com.digis01.IHernandezProgramacionNCapas.ML.Rol(rol));
            }
            
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

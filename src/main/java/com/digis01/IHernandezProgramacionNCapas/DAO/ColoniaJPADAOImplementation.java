package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Colonia;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaJPADAOImplementation implements IColoniaJPADAO
{
    @Autowired
    EntityManager entityManager;
    
    @Override
    public Result ColoniaByMunicipioGetAll(int idMunicipio)
    {
        Result result = new Result();
        
        try 
        {
            TypedQuery<Colonia> queryColonia = entityManager.createQuery("FROM Colonia ORDER BY IdColonia", Colonia.class);
            List<Colonia> colonias = queryColonia.getResultList();
            
            result.objects = new ArrayList<>();
            
            for (Colonia colonia : colonias) 
            {
                result.objects.add(new com.digis01.IHernandezProgramacionNCapas.ML.Colonia(colonia));
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

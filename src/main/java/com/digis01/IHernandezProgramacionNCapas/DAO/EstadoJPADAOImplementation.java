package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Estado;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoJPADAOImplementation implements IEstadoJPADAO
{
    @Autowired
    EntityManager entityManager;

    @Override
    public Result EstadoByPaisGetAll(int idPais)
    {
        Result result = new Result();
        
        try 
        {
            TypedQuery<Estado> queryEstado = entityManager.createQuery("FROM Estado ORDER BY IdEstado", Estado.class);
            List<Estado> estados = queryEstado.getResultList();
            
            result.objects = new ArrayList<>();
            
            for (Estado estado : estados) 
            {
                result.objects.add(new com.digis01.IHernandezProgramacionNCapas.ML.Estado(estado));
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

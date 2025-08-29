package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Municipio;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioJPADAOImplementation implements IMunicipioJPADAO
{
    @Autowired
    EntityManager entityManager;

    @Override
    public Result MunicipioByEstadoGetAll(int IdEstado)
    {
        Result result = new Result();
        
        try 
        {
            TypedQuery<Municipio> queryMuncipio = entityManager.createQuery("FROM Municipio WHERE Estado.IdEstado = :IdEstado ORDER BY IdMunicipio", Municipio.class);
            queryMuncipio.setParameter("IdEstado", IdEstado);
            List<Municipio> municipios = queryMuncipio.getResultList();
            
            result.objects = new ArrayList<>();
            
            for (Municipio municipio : municipios) 
            {
                result.objects.add(new com.digis01.IHernandezProgramacionNCapas.ML.Municipio(municipio));
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

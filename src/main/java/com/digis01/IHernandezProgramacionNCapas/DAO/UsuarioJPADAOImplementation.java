package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import com.digis01.IHernandezProgramacionNCapas.ML.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuarios = queryUsuario.getResultList();
        } 
        catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}

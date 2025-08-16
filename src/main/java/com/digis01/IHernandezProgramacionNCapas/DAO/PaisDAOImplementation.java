package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.ML.Pais;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaisDAOImplementation implements IPaisDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAllPais() 
    {
        Result result = new Result();
        
        try {
            jdbcTemplate.execute("CALL PaisGetAll(?)", (CallableStatementCallback<Boolean>) callableStatement -> 
            {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                result.objects = new ArrayList<>();
                
                while (resultSet.next()) 
                {
                    Pais pais = new Pais();
                    
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombre(resultSet.getString("Nombre"));
                    
                    result.objects.add(pais);
                }
                result.correct = true;
                return true;
            });
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}

package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.ML.Colonia;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements IColoniaDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result ColoniaByMunicipioGetAll(int idMunicipio)
    {
        Result result = new Result();
        
        try 
        {
            jdbcTemplate.execute("CALL ColoniaByMunicipioGetAll(?, ?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, idMunicipio);
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                result.objects = new ArrayList<>();
                
                while (resultSet.next())
                {
                    Colonia colonia = new Colonia();
                    
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombre(resultSet.getString("Nombre"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    
                    result.objects.add(colonia);
                }
                result.correct = true;
                return true;
            });
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
}

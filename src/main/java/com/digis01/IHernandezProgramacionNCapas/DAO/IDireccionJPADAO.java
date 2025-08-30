package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.ML.Result;

public interface IDireccionJPADAO 
{
    public Result AddDireccion(int IdUsuario);
    
    public Result Delete(int IdUsuario);
}

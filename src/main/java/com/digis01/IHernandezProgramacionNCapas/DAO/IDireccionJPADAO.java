package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.ML.Result;

public interface IDireccionJPADAO 
{
    public Result AddDireccion(int IdDireccion);
    
    public Result UpdateDireccion(com.digis01.IHernandezProgramacionNCapas.ML.Usuario usuarioML);
    
    public Result Delete(int IdDireccion);
}

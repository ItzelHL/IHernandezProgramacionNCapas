package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;

public interface IUsuarioJPADAO 
{
    Result GetAll();
    
    Result GetById(int idUsuario);
    
    Result Add(com.digis01.IHernandezProgramacionNCapas.ML.Usuario usuarioML);
    
    Result Delete(int idUsuario);
    
    Result Update(com.digis01.IHernandezProgramacionNCapas.ML.Usuario usuarioML);
    
    Result UpdateStatus(Usuario usuario);
}

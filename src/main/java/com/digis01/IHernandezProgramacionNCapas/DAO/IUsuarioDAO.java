package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import com.digis01.IHernandezProgramacionNCapas.ML.Usuario;

public interface IUsuarioDAO 
{
    Result GetAll();
    
    Result GetById(int idUsuario);
    
    Result DireccionesByIdUsuario(int idUsuario);
    
    Result Add(Usuario usuario);
    
    Result UpdateUsuario(Usuario usuario);
}

package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;

public interface IUsuarioJPADAO 
{
    Result GetAll();
    
    Result Add(Usuario usuario);
}

package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.ML.Result;

public interface IMunicipioDAO 
{
    Result MunicipioByEstadoGetAll(int idEstado);
}

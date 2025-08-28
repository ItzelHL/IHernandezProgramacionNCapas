package com.digis01.IHernandezProgramacionNCapas.ML;

public class Pais {
    private int IdPais;
    private String Nombre;
    
    public Pais(){}

    public Pais(int idPais, String nombre) 
    {
        this.IdPais = idPais;
        this.Nombre = nombre;
    }
    
    public Pais(com.digis01.IHernandezProgramacionNCapas.JPA.Pais paisJPA)
    {
        this.IdPais = paisJPA.getIdPais();
        this.Nombre = paisJPA.getNombre();
    }

    public void setIdPais(int idPais) 
    {
        this.IdPais = idPais;
    }
    public int getIdPais() {
        return IdPais;
    }
    
     public void setNombre(String nombre) 
     {
        this.Nombre = nombre;
     }
    public String getNombre() 
    {
        return Nombre;
    }
}

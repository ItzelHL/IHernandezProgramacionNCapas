package com.digis01.IHernandezProgramacionNCapas.ML;

public class Estado {
    private int IdEstado;
    private String Nombre;
    public Pais Pais;
    
    public Estado(){}

    public Estado(int idEstado, String nombre) 
    {
        this.IdEstado = idEstado;
        this.Nombre = nombre;
    }
    
    public Estado(com.digis01.IHernandezProgramacionNCapas.JPA.Estado estadoJPA)
    {
        this.IdEstado = estadoJPA.getIdEstado();
        this.Nombre = estadoJPA.getNombre();
    }
    
    public void setIdEstado(int idEstado) 
    {
        this.IdEstado = idEstado;
    }
    public int getIdEstado() 
    {
        return IdEstado;
    }

    public void setNombre(String nombre) 
    {
        this.Nombre = nombre;
    }
    public String getNombre() 
    {
        return Nombre;
    }

    public Pais getPais() {
        return Pais;
    }

    public void setPais(Pais pais) {
        this.Pais = pais;
    }
    
    
}

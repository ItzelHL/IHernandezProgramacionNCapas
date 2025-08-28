package com.digis01.IHernandezProgramacionNCapas.ML;

public class Colonia 
{
    private int IdColonia;
    private String Nombre;
    private String CodigoPostal;
    public Municipio Municipio;
    
    
    public Colonia(){}

    public Colonia(int idColonia, String nombre, String codigoPostal) 
    {
        this.IdColonia = idColonia;
        this.Nombre = nombre;
        this.CodigoPostal = codigoPostal;
    }
    
    public Colonia(com.digis01.IHernandezProgramacionNCapas.JPA.Colonia coloniaJPA)
    {
        this.IdColonia = coloniaJPA.getIdColonia();
        this.Nombre = coloniaJPA.getNombre();
        this.CodigoPostal = coloniaJPA.getCodigoPostal();
    }
    
    public void setIdColonia(int idColonia)
    {
        this.IdColonia = idColonia;
    }
    public int getIdColonia()
    {
        return IdColonia;
    }

    public Municipio getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.Municipio = municipio;
    }
    
    public void setNombre(String nombre)
    {
        this.Nombre = nombre;
    }
    public String getNombre()
    {
        return Nombre;
    }
    
    public void setCodigoPostal(String codigoPostal)
    {
        this.CodigoPostal = codigoPostal;
    }
    public String getCodigoPostal()
    {
        return CodigoPostal;
    }
}

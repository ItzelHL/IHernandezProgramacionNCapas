package com.digis01.IHernandezProgramacionNCapas.JPA;

public class Direccion 
{
    private int IdDireccion;
    
    private String Calle;
    
    private String NumeroExterior;
    
    private String NumeroInterior;
    
    public Colonia Colonia;
    
    public Direccion(){}

    public Direccion(int idDireccion, String calle, String numeroExterior, String numeroInterior, Colonia colonia) 
    {
        this.IdDireccion = idDireccion;
        this.Calle = calle;
        this.NumeroExterior = numeroExterior;
        this.NumeroInterior = numeroInterior;
        this.Colonia = colonia;
    }

    public void setIdDireccion(int idDireccion) 
    {
        this.IdDireccion = idDireccion;
    }
    public int getIdDireccion() 
    {
        return IdDireccion;
    }
    
    public void setCalle(String calle) 
    {
        this.Calle = calle;
    }
    public String getCalle() 
    {
        return Calle;
    }
    
    public void setNumeroExterior(String numeroExterior) 
    {
        this.NumeroExterior = numeroExterior;
    }
    public String getNumeroExterior() 
    {
        return NumeroExterior;
    }

    public void setNumeroInterior(String numeroInterior) 
    {
        this.NumeroInterior = numeroInterior;
    }
    public String getNumeroInterior() 
    {
        return NumeroInterior;
    }

    public void setColonia(Colonia colonia) 
    {
        this.Colonia = colonia;
    }
    public Colonia getColonia() 
    {
        return Colonia;
    }
}

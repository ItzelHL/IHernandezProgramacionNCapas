package com.digis01.IHernandezProgramacionNCapas.ML;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class Direccion {
    private int IdDireccion;
    
    @Pattern(regexp = "^[A-z áéíóúÁÉÍÓÚäëïöüÄËÏÖÜ\\d#&,.-]+", message = "Este campo no perminte carácteres como $%")
    @NotEmpty(message = "Información necesaria")
    private String Calle;
    
    @Pattern(regexp = "^[A-z áéíóúÁÉÍÓÚäëïöüÄËÏÖÜ\\d#][A-z\\d#&-/_]*$", message = "Este campo no perminte carácteres como $%")
    @NotEmpty(message = "Información necesaria")
    private String NumeroInterior;
    
    @Pattern(regexp = "^[A-z áéíóúÁÉÍÓÚäëïöüÄËÏÖÜ\\d#][A-z\\d#&-/_]*$", message = "Este campo no perminte carácteres como $%")
    @NotEmpty(message = "Información necesaria")
    private String NumeroExterior;
    public Colonia Colonia;

    public Direccion(){}
    
    public Direccion( int idDireccion){
        this.IdDireccion = idDireccion;
    }
    
    public Direccion(int idDireccion, String calle, String numeroInterior, String numeroExterior) 
    {
        this.IdDireccion = idDireccion;
        this.Calle = calle;
        this.NumeroInterior = numeroInterior;
        this.NumeroExterior = numeroExterior;
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

    public void setNumeroInterior(String numeroInterior) 
    {
        this.NumeroInterior = numeroInterior;
    }
    public String getNumeroInterior() 
    {
        return NumeroInterior;
    }
    
    public void setNumeroExterior(String numeroExterior) 
    {
        this.NumeroExterior = numeroExterior;
    }
    public String getNumeroExterior() 
    {
        return NumeroExterior;
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

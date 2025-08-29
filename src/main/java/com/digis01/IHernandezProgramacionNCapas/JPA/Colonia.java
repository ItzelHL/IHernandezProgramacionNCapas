package com.digis01.IHernandezProgramacionNCapas.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Colonia 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcolonia")
    private int IdColonia;
    
    @Column(name = "nombre")
    private String Nombre;
    
    @Column(name = "codigopostal")
    private String CodigoPostal;
    
    @ManyToOne
    @JoinColumn(name = "idmunicipio")
    public Municipio Municipio;
    
    
    public Colonia(){}

    public Colonia(int idColonia, String nombre, String codigoPostal) 
    {
        this.IdColonia = idColonia;
        this.Nombre = nombre;
        this.CodigoPostal = codigoPostal;
    }

    public Colonia(int idColonia, String nombre, String codigoPostal, Municipio municipio) 
    {
        this.IdColonia = idColonia;
        this.Nombre = nombre;
        this.CodigoPostal = codigoPostal;
        this.Municipio = municipio;
    }
    
    public Colonia(com.digis01.IHernandezProgramacionNCapas.ML.Colonia coloniaML)
    {
        this.IdColonia = coloniaML.getIdColonia();
        this.Nombre = coloniaML.getNombre();
        this.CodigoPostal = coloniaML.getCodigoPostal();
    }
    
    public void setIdColonia(int idColonia)
    {
        this.IdColonia = idColonia;
    }
    public int getIdColonia()
    {
        return IdColonia;
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
    
    public void setMunicipio(Municipio municipio) 
    {
        this.Municipio = municipio;
    }
    public Municipio getMunicipio() 
    {
        return Municipio;
    }
}

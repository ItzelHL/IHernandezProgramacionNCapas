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
public class Municipio 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmunicipio")
    private int IdMunicipio;
    
    @Column(name = "nombre")
    private String Nombre;
    
    @ManyToOne
    @JoinColumn(name = "idestado")
    public Estado Estado;
    
    public Municipio(){}

    public Municipio(int idMunicipio, String nombre)
    {
        this.IdMunicipio = idMunicipio;
        this.Nombre = nombre;
    }

    public Municipio(int idMunicipio, String nombre, Estado estado) 
    {
        this.IdMunicipio = idMunicipio;
        this.Nombre = nombre;
        this.Estado = estado;
    }
    
    public Municipio(com.digis01.IHernandezProgramacionNCapas.ML.Municipio municipioML)
    {
        this.IdMunicipio = municipioML.getIdMunicipio();
        this.Nombre = municipioML.getNombre();
    }
    
    public void setIdMunicipio(int idMunicipio)
    {
        this.IdMunicipio = idMunicipio;
    }
    public int getIdMunicipio()
    {
        return IdMunicipio;
    }
    
    public void setNombre(String nombre)
    {
        this.Nombre = nombre;
    }
    public String getNombre()
    {
        return Nombre;
    }
    
    public void setEstado(Estado estado) 
    {
        this.Estado = estado;
    }
    public Estado getEstado() 
    {
        return Estado;
    }
}

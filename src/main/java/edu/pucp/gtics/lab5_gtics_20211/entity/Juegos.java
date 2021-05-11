package edu.pucp.gtics.lab5_gtics_20211.entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Entity
@Table(name = "juegos")
public class Juegos {

    @Id
    private int idjuego;

    @Size(min = 3, max = 45, message = "Debe contener entre 3 y 45 caracteres")
    private String nombre;

    @Size(min = 3, max = 200, message = "Debe contener entre 3 y 200 caracteres")
    private String descripcion;

    @NotNull(message = "Coloque un número")
    @DecimalMin(value = "10" , message = "Valor maximo 10")
    @DecimalMax(value = "500" , message = "Valor mínimo 500")
    private double precio;

    private String image;
    @ManyToOne
    @JoinColumn(name = "idplataforma")
    @Valid
    private Plataformas plataforma;

    public int getIdjuego() {
        return idjuego;
    }

    public void setIdjuego(int idjuego) {
        this.idjuego = idjuego;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Plataformas getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataformas plataforma) {
        this.plataforma = plataforma;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

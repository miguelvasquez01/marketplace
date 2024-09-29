package com.marketplace.model;

import java.util.List;
import java.time.LocalDate;

import lombok.Data;

@Data
public class Producto {
    
    private String nombre;
    private int codigo;
    private String imagen;
    private String categoria;
    private double precio;
    private EstadoProducto estado;
    private LocalDate fechaPublicacion;
    private List<Comentario> comentarios;
    private int meGustas;
}

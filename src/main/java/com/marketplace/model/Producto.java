package com.marketplace.model;

import lombok.Data;

@Data
public class Producto {
    
    private String nombre;
    private int codigo;
    private String imagen;
    private String categoria;
    private double precio;
    private EstadoProducto estado;
}

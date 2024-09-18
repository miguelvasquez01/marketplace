package com.marketplace.model;

import lombok.Data;
import java.util.ArrayList;

@Data
public class Vendedor {
    
    private String nombre;
    private String apellidos;
    private String cedula;
    private String direccion;
    private ArrayList<Producto> productos;
}

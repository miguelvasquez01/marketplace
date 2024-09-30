package com.marketplace.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vendedor {

    private String nombre;
    private String apellidos;
    private String cedula;
    private String direccion;
    private ArrayList<Producto> productos;
    private ArrayList<Vendedor> contactos;
    private ArrayList<Resena> reseñas;
    private ArrayList<Vendedor> solicitudesDeContacto;
    private Muro muro;

}

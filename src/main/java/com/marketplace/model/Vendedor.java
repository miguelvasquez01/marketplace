package com.marketplace.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
public class Vendedor {

    private String nombre;
    private String apellidos;
    private String cedula;
    private String direccion;
    private List<Producto> productos;
    private ArrayList<Vendedor> contactos;
    private ArrayList<Resena> reseñas;
    private ArrayList<Vendedor> solicitudesDeContacto;
    private Muro muro;

    public Vendedor() {
        this.productos = new ArrayList<>();
    }
}

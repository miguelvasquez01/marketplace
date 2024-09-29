package com.marketplace.model;

import java.util.List;

import lombok.Data;

@Data
public class Muro {
    
    private String mensaje;
    private List<Comentario> comentarios;
    private int meGustas;
}

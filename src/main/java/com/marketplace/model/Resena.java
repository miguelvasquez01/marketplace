package com.marketplace.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Resena {

    private Vendedor autor;
    private int calificacion;
    private String comentario;
    private LocalDateTime fecha;
}

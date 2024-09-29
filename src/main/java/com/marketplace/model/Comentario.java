package com.marketplace.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comentario {
    
    private Vendedor autor;
    private String mensaje;
    private LocalDateTime fecha;
}

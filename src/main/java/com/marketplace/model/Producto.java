package com.marketplace.model;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    
    //Lo mando como String para serializarlo correctamente en el servidor
    public String getFechaPublicacion() {
        return fechaPublicacion.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    //Lo recibo como String y lo convierto a LocalDate
    public void setFechaPublicacion(String fecha) {
        if (fecha != null && !fecha.isEmpty()) {
            this.fechaPublicacion = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            this.fechaPublicacion = null;
        }
    }
}

package com.edutech.notificaciones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notificacion {
    private Long id;
    private String destinatario;
    private String mensaje;
    private java.time.LocalDateTime fechaCreacion;
    private boolean leido;
}
